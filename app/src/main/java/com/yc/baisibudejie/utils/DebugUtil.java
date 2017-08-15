package com.yc.baisibudejie.utils;

import android.os.Build;
import android.os.StrictMode;

/**
 * 启用严格模式工具类
 * 对于开发模式使用严格模式
 */
public class DebugUtil {

    public static void enableStrictMode() {
        if(DebugUtil.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
            StrictMode.VmPolicy.Builder vmPlicyBuilder =
                    new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

            if(DebugUtil.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
            }

            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPlicyBuilder.build());
        }
    }

    private static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    private static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    private static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    private static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}

