package com.sfs.zbar.tools;

import com.sfs.zbar.MyApplication;
import com.sfs.zbar.entity.Ser1UserInfo;
import com.sfs.zbar.tools.aes.SeirableUtil;

import java.io.File;


public class SettingsHelper {
	private static final String USER_INFO_FILE = "ypUserInfo.dat";

	private static SettingsHelper settingsHelper = null;

	private SettingsHelper() {
	}

	public static SettingsHelper getInstance() {
		if (settingsHelper == null) {
			settingsHelper = new SettingsHelper();
		}
		return settingsHelper;
	}

	public Ser1UserInfo getUserInfo() {
		Ser1UserInfo userInfo = null;// 用户信息
		try {
			userInfo = (Ser1UserInfo) SeirableUtil.loadSeriableObjectFromFile(CommonTools.getUUID(),
					USER_INFO_FILE, MyApplication.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	/**
	 * 
	 * @MethodName:setUserInfo
	 * @Description: 用户信息
	 * @param userInfo_
	 *            void
	 */
	public void setUserInfo(Ser1UserInfo userInfo_) {
		if (userInfo_ == null) {
			File f = MyApplication.getInstance().getFileStreamPath(USER_INFO_FILE);
			if (f.exists()) {
				f.delete();
			}
		} else {
			try {
				SeirableUtil.saveSeriableObjectIntoFile(userInfo_, CommonTools.getUUID(), USER_INFO_FILE,
						MyApplication.getInstance());
			} catch (Exception e) {

			}
		}
	}

}
