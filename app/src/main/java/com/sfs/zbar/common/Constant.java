package com.sfs.zbar.common;


import android.content.Intent;

/**
 * @version V1.0
 * @ClassName: Constant
 * @Description: 公用常量类
 * @author：LiZhimin
 * @date：2014-7-5 下午1:58:14
 */
public class Constant {
    /**
     * 默认sharePrefer名字
     */
    public final static String SHARE_NAME_DEF = "guoao";

    public static final int REQUEST_LOAD_CAMERA = 101;// 打开相机
    public static final int REQUEST_LOAD_GALLERY = 102;// 加载图库
    public static final int REQUEST_CROP_IMAGE = 103;// 裁剪图片

    //传递参数
    public static final String PARA_KEY = "para_key";
    public static final String PARA_KEY2 = "para_key2";
    public static final String PARA_KEY3 = "para_key3";
    public static final String PARA_KEY4 = "para_key4";
    public static final String PARA_KEY5 = "para_key5";
    public static final String PARA_KEY6 = "para_key6";

    public static final String DATE_DIVIDER = "-";
    public static final String MINUTE_DIVIDER = "-";
    public static final String IMAGE_DIVIDER = ",";

    /**
     * des加密的key
     */
    public static final String DES_KEY = "FH040803";


    //网络请求结果
    public static final int NET_SUCCESS = 1;
    public static final int NET_FAIL = 0;
    public static final int Net_NOT_PERFECT = 2;
    public static final int Net_UNREGISTERED = 3;

    /**
     * 后台用于md5加密的key
     */
    public static final String USER_MD5_KEY = "md5_key";

    /**
     * 开启二维头Intent
     */
    // new add for scan
    // 打开二维头
    public static final Intent
            intent_openScan = new Intent(
            "android.intent.action.SCANNER_BUTTON_DOWN", null);
    // 关闭二维头
    public static final Intent intent_closeScan = new Intent(
            "android.intent.action.SCANNER_OFF", null);

}
