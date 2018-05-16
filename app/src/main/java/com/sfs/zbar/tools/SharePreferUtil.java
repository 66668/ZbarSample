package com.sfs.zbar.tools;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sfs.zbar.common.Constant;
import com.sfs.zbar.MyApplication;


/**
 * 
 * @ClassName: SharePreferUtil
 * @Description: 新的SharedPreferences存储类 不需要传contex
 * @version V1.0
 */
public class SharePreferUtil {
	private static int MODE = Context.MODE_PRIVATE;

	private SharePreferUtil() {
	}

	private static SharedPreferences getSharePrefer(String shareName) {
		return MyApplication.getInstance().getSharedPreferences(shareName, MODE);
	}
	
	public static void putBoolean(String key, boolean value) {
		putBoolean(Constant.SHARE_NAME_DEF, key, value);
	}

	public static void putBoolean(String shareName, String key, boolean value) {
		SharedPreferences share = getSharePrefer(shareName);
		Editor editor = share.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(String key, boolean defValue) {
		return getBoolean(Constant.SHARE_NAME_DEF, key, defValue);
	}

	public static boolean getBoolean(String shareName, String key, boolean defValue) {
		SharedPreferences share = getSharePrefer(shareName);
		return share.getBoolean(key, defValue);
	}

	public static void putInt(String key, int value) {
		putInt(Constant.SHARE_NAME_DEF, key, value);
	}

	public static void putInt(String shareName, String key, int value) {
		SharedPreferences share = getSharePrefer(shareName);
		Editor editor = share.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getInt(String key, int defValue) {
		return getInt(Constant.SHARE_NAME_DEF, key, defValue);
	}

	public static int getInt(String shareName, String key, int defValue) {
		SharedPreferences share = getSharePrefer(shareName);
		return share.getInt(key, defValue);
	}

	public static void putFloat(String key, float value) {
		putFloat(Constant.SHARE_NAME_DEF, key, value);
	}

	public static void putFloat(String shareName, String key, float value) {
		SharedPreferences share = getSharePrefer(shareName);
		Editor editor = share.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static float getFloat(String key, float defValue) {
		return getFloat(Constant.SHARE_NAME_DEF, key, defValue);
	}

	public static float getFloat(String shareName, String key, float defValue) {
		SharedPreferences share = getSharePrefer(shareName);
		return share.getFloat(key, defValue);
	}

	public static void putLong(String key, long value) {
		putLong(Constant.SHARE_NAME_DEF, key, value);
	}

	public static void putLong(String shareName, String key, long value) {
		SharedPreferences share = getSharePrefer(shareName);
		Editor editor = share.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static long getLong(String key, long defValue) {
		return getLong(Constant.SHARE_NAME_DEF, key, defValue);
	}

	public static long getLong(String shareName, String key, long defValue) {
		SharedPreferences share = getSharePrefer(shareName);
		return share.getLong(key, defValue);
	}
	

	public static void putString(String key, String value) {
		putString(Constant.SHARE_NAME_DEF, key, value);
	}

	public static void putString(String shareName, String key, String value) {
		SharedPreferences share = getSharePrefer(shareName);
		Editor editor = share.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getString(String key, String defValue) {
		return getString(Constant.SHARE_NAME_DEF, key, defValue);
	}

	public static String getString(String shareName, String key, String defValue) {
		SharedPreferences share = getSharePrefer(shareName);
		return share.getString(key, defValue);
	}

	/**
	 * 
	 * @MethodName:clearAll
	 * @Description: 清除所有Constant.DEF_SHARE_NAME中的数据
	 * @author：LiZhimin
	 * @date：2014-8-15 下午5:46:12 void
	 */
	public static void clearAll() {
		Editor edit = getSharePrefer(Constant.SHARE_NAME_DEF).edit();
		edit.clear().commit();
	}

}
