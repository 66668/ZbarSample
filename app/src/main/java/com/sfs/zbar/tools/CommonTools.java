package com.sfs.zbar.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;


import com.sfs.zbar.MyApplication;
import com.sfs.zbar.base.LoadingAct;
import com.sfs.zbar.common.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTools {

	private static final String ENCODING = "UTF-8";

	private static String UUID;
	private static int defaultDialogWidth;

	/**
	 * @Description: 设置加载进度条的状态
	 * @param context
	 * @param visible
	 *            true-显示 false 隐藏
	 * @return void
	 */
	public static final void setLoadingVisible(Context context, boolean visible) {
		if (context == null) {
			return;
		}
		if (context instanceof LoadingAct) {
			LoadingAct act = (LoadingAct) context;
			if (act != null && !act.isFinishing()) {
				act.setLoadingVisible(visible, 0.5f);
			}
		}
	}
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis()).substring(0, 13);
	}

	public static String getDesPin(String token) {
		String timeStamp = getTimeStamp();
		System.out.println("timeStamp=" + timeStamp);
		try {
			String desPin = DESUtils
					.encode(Constant.DES_KEY, token + timeStamp);
			System.out.println("desPin=" + desPin);
			return desPin;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public static int string2int(String str) {
		int i = 0;
		try {
			i = Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
		return i;
	}

	public static String getUUID() {
		if (TextUtils.isEmpty(UUID)) {
			final TelephonyManager tm = (TelephonyManager) MyApplication
					.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
			final String tmDevice, tmSerial, androidId;
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = ""
					+ android.provider.Settings.Secure
					.getString(MyApplication.getInstance()
									.getContentResolver(),
							android.provider.Settings.Secure.ANDROID_ID);
			java.util.UUID deviceUuid = new UUID(androidId.hashCode(),
					((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
			UUID = deviceUuid.toString();
		}
		return UUID;
	}
}
