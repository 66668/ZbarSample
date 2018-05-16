package com.sfs.zbar.common;

import android.content.Context;
import android.text.TextUtils;

import com.sfs.zbar.entity.Ser1UserInfo;
import com.sfs.zbar.tools.Logg;
import com.sfs.zbar.tools.SettingsHelper;
import com.sfs.zbar.tools.SharePreferUtil;

/**
 * 
 * @ClassName: UserManager
 * @Description: 用户信息管理类
 * @version V1.0
 */
public class UserManager {


	public static String TAG = "UserManager";

	public static Ser1UserInfo userInfo;

	/**
	 * 
	 * @MethodName:getUserInfo
	 * @Description: 获取本地用户信息
	 * @return Ser1UserInfo
	 */
	public static Ser1UserInfo getUserInfo() {
		if (userInfo == null) {
			userInfo = SettingsHelper.getInstance().getUserInfo();
			if (userInfo != null) {
				Logg.v(TAG, userInfo.toString());
			}
		}
		return userInfo;
	}

	/**
	 * 
	 * @MethodName:setUserInfo
	 * @Description: 保存用户信息
	 * @param userInfo
	 *            void
	 */
	public static void setUserInfo(Ser1UserInfo luserInfo) {
		userInfo = luserInfo;
		SettingsHelper.getInstance().setUserInfo(luserInfo);
	}

	/**
	 * 
	 * @MethodName:setUserKey
	 * @Description: 保存服务器返回的用于登录信息加密的key
	 * @param key
	 *            void
	 */
	public static void setUserKey(String key) {
		SharePreferUtil.putString(Constant.USER_MD5_KEY, key);
	}

	/**
	 * 
	 * @MethodName:getUserKey
	 * @Description: 本地获取服务器返回的用于用户登录信息加密的key
	 * @return String
	 */
	public static String getUserKey() {
		return SharePreferUtil.getString(Constant.USER_MD5_KEY, "");
	}

	/**
	 * 
	 * @MethodName:getUserId
	 * @Description: 获取用户id
	 * @return String
	 */
	public static String getUserId() {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo != null) {
			return userInfo.getAid();
		}
		return "";
	}

	public static void setUserId(String id) {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo == null) {
			userInfo = new Ser1UserInfo();
		}
		userInfo.mAid = id;
		setUserInfo(userInfo);
	}

	public static void setLogInData(String pin, String pid, String icon) {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo == null) {
			userInfo = new Ser1UserInfo();
		}
		userInfo.pin = pin;
		userInfo.mAid = pid;
		userInfo.icon = icon;
		setUserInfo(userInfo);
	}

	/**
	 * 
	 * @MethodName:settUserNickname
	 * @Description:设置昵称
	 * @return String
	 */
	public static void settUserNickname(String nickname) {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo == null) {
			userInfo = new Ser1UserInfo();
		}
		userInfo.nickname = nickname;
		setUserInfo(userInfo);
	}

	public static String getUserPin() {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo != null) {
			return userInfo.getPin();
		}
		return "";
	}

	/**
	 * 
	 * @MethodName:setUserHeadImg
	 * @Description: 保存用户头像网络地址(修改资料)
	 * @param path
	 *            void
	 */
	public static void setUserHeadImg(String path) {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo != null) {
			userInfo.setIcon(path);
			setUserInfo(userInfo);
		}
	}

	/**
	 * 
	 * @MethodName:getUserHeadImg
	 * @Description: 取用户头像网络地址
	 * @return String
	 */
	public static String getUserHeadImg() {
		Ser1UserInfo userInfo = getUserInfo();
		if (userInfo != null) {
			return userInfo.getIcon();
		}
		return "";
	}

	/**
	 * 
	 * @MethodName:isLogin
	 * @Description: 是否登录
	 * @return boolean
	 */
	public static boolean isLogin() {
		Ser1UserInfo userInfo = UserManager.getUserInfo();
		if (userInfo != null) {
			return !TextUtils.isEmpty(userInfo.getPin());
		}
		return false;
	}

	/**
	 * 
	 * @param context
	 * @MethodName:doLogout
	 * @Description: 注销账户
	 */
	public static void doLogout(Context context) {
		UserManager.getUserInfo().isLogin = false;
		UserManager.setUserInfo(UserManager.getUserInfo());
		userInfo = null;
	}

}
