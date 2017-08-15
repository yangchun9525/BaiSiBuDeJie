package com.yc.baisibudejie.constant;

import android.os.Environment;

import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.utils.FileUtil;

/**
 * Created by yangchun on 2015/5/29.
 * 应用中所有文件路径
 */
public class Dir {

    /**
     * Context.getExternalFilesDir()方法
     *      获取到 SDCard/Android/data/<application package>/files/ 目录，一般放一些长时间保存的数据
     *      对应 设置->应用->应用详情里面的“清除数据”选项
     * Context.getExternalCacheDir()方法
     *      获取到 SDCard/Android/data/<application package>/cache/目录，一般存放临时缓存数据
     *      对应 设置->应用->应用详情里面的“清除缓存”选项
     * Context.getCacheDir()方法
     *      用于获取/data/data/<application package>/cache目录
     * Context.getFilesDir()方法
     *      用于获取/data/data/<application package>/files目录
     */

    /**
     * 获取应用的缓存路径
     * @return
     */
    public static String getDiskCacheDir() {
        String cachePath = null;
        if (FileUtil.hasSDCard()) {
            cachePath = GlobalApp.getInstance().getApplicationContext().getExternalCacheDir().getPath();
        } else {
            cachePath = GlobalApp.getInstance().getApplicationContext().getCacheDir().getPath();
        }
        return cachePath + "/data/";
    }

    /**
     * 获取应用的缓存路径
     * @return
     */
    public static String getCrashDir() {
        String appPath = "BaiSiBuDeJie";
        return  Environment.getExternalStorageDirectory().getPath() + "/" + appPath +"/crash/";
    }
}
