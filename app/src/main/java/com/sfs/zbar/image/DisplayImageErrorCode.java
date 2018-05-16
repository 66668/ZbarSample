package com.sfs.zbar.image;

/**
 * Project:ImageLoaderLib <br>
 * Class:DisPlayImageErrorCode <br>
 * Description:图片加载器错误代码<br>
 * Created by 杨禹恒 on 2014年9月22日. <br>
 * Version:1.0.0 <br>
 * Update:2014年9月22日 <br>
 */
public class DisplayImageErrorCode {
	/**
	 * 控件类型错误
	 */
	public static final int DISPLAY_ERROR_WIDGET_TYPE=1001;
	
	/**
	 * 控件为空
	 */
	public static final int DISPLAY_ERROR_WIDGET_NULL=1002;
	/**
	 * 网络连接错误，检查网络是否可用
	 */
	public static final int DISPLAY_ERROR_NET_CONNECTION=2001;
	/**
	 * 网络URL格式错误，检查URL有效性
	 */
	public static final int DISPLAY_ERROR_NET_URL_FORMAT=2002;
	/**
	 * 网络超时
	 */
	public static final int DISPLAY_ERROR_NET_TIMEOUT=2003;
	/**
	 * 网络文件未找到
	 */
	public static final int DISPLAY_ERROR_IMAGE_NOT_FOUND=3004;
}
