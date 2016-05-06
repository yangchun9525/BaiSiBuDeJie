package com.yc.BaiSiBuDeJie.utils;

import android.util.Log;

/**
 * 日志管理类
 * 在DeBug模式开启，其他模式关闭
 */
public class LogTools {

    private static final String DEFAULT_TAG = "develop";

    /**
     * 是否开启debug
     */
    private static boolean isDebug = true;

    /**
     * 错误
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, getFunctionName() + " msg : " + msg);
        }
    }

    /**
     * 错误
     * @param msg
     */
    public static void e(String msg) {
        if (isDebug) {
            Log.e(DEFAULT_TAG, getFunctionName() + " msg : " + msg);
        }
    }

    /**
     * 信息
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, getFunctionName() + " msg : " + msg);
        }
    }

    /**
     * 信息
     * @param msg
     */
    public static void i(String msg) {
        if (isDebug) {
            Log.i(DEFAULT_TAG, getFunctionName() + " msg : " + msg);;
        }
    }

    /**
     * 警告
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, getFunctionName() + " msg : " + msg);
        }
    }
    /**
     * 警告
     * @param msg
     */
    public static void w(String msg) {
        if (isDebug) {
            Log.w(DEFAULT_TAG, getFunctionName() + " msg : " + msg);
        }
    }

    /**
     * 获取当前方法的文件名，方法名，行号
     * @return
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(LogTools.class.getName())) {
                continue;
            }

            return "[ " + Thread.currentThread().getId() + ": "
                    + st.getFileName() + ": " + st.getMethodName() + "(): " + st.getLineNumber() + " ]";
        }

        return null;
    }
}
