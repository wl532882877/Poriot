package com.pay.poriot.util;

import android.content.pm.PackageManager;

import com.pay.poriot.base.BaseApplication;

public class PackageUtil {
    private static final String TAG = "PackageUtil";

    /**
     * 获取应用程序的包名
     *
     * @return 应用程序的包名
     */
    public static String getPackageName() {
        return BaseApplication.getInstance().getPackageName();
    }

    /**
     * 获取应用程序的版本号
     *
     * @return 版本号
     */
    public static int getVersionCode() {
        int verCode = 0;
        try {
            verCode = BaseApplication.getInstance().getPackageManager().getPackageInfo(
                    BaseApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException exception) {
            EvtLog.d(TAG, exception.toString());
        }
        return verCode;
    }

    /**
     * 获取应用程序的外部版本号
     *
     * @return 外部版本号
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            versionName = BaseApplication.getInstance().getPackageManager().getPackageInfo(
                    BaseApplication.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException exception) {
            EvtLog.d(TAG, exception.toString());
        }
        return versionName;
    }

}
