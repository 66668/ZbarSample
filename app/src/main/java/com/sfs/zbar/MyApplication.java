package com.sfs.zbar;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.sfs.zbar.common.UserManager;
import com.sfs.zbar.entity.Ser1UserInfo;
import com.sfs.zbar.net.GetDataManager;
import com.sfs.zbar.tools.CommonTools;
import com.sfs.zbar.tools.Toaster;

/**
 * Created by souhitoshiyou on 2018/1/24.
 */

public class MyApplication extends Application {
    public static float density = 2;//默认是2
    public static String netWorkTimeOut = "network timeout!";
    public static String netWorkError = "network error!";
    private static String yonghuToken = null;
    private static MyApplication instance;
    public static DisplayMetrics metrics;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        density = metric.density;

        metrics = getResources().getDisplayMetrics();

        //初始化网络
        GetDataManager.init(instance);

        //初始化弹窗
        Toaster.init(this);
    }
    /**
     * @MethodName:getPin
     * @Description: 获取pin串
     * @return String
     */
    public static String getPin() {
            if (TextUtils.isEmpty(yonghuToken)) {
                Ser1UserInfo userInfo = UserManager.getUserInfo();
                if (userInfo != null) {
                    if (!TextUtils.isEmpty(userInfo.getPin())) {
                        yonghuToken = userInfo.getPin();
                    }
                }
            }
            return yonghuToken + "^^" + CommonTools.getDesPin(yonghuToken);
    }
    public static MyApplication getInstance() {
        return instance;
    }
}
