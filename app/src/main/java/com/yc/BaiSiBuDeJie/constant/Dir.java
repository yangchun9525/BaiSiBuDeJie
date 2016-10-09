package com.yc.BaiSiBuDeJie.constant;

import android.os.Environment;

import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.utils.FileUtil;

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

    private static final String APP_NAME_PATH = "/Innovation/";

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
     * 获取应用在sd卡中的root路径
     * @return
     */
    public static String getAppRootDir() {
        if (FileUtil.hasSDCard()) {
            return Environment.getExternalStoragePublicDirectory(APP_NAME_PATH).getAbsolutePath() + "/";
        } else {
            return APP_NAME_PATH;
        }
    }

    /**
     * 获取应用中存放附件的路径
     * @return
     */
    public static String getAppendixDir() {
        return getAppendixDir(false);
    }

    /**
     * 获取应用中存放附件的路径
     * @param isDownload
     *          由于调用DownloadManager时，系统已经默认添加了getExternalStoragePublicDirectory，所以在
     *          此处不在调用getAppRootDir方法获得存储路径。
     * @return
     */
    public static String getAppendixDir(boolean isDownload) {
        if (isDownload) {
            return APP_NAME_PATH + "appendix/";
        } else {
            return getAppRootDir() + "appendix/";
        }
    }

    /**
     * 获取应用中存放主题的路径
     * @param isDownload
     *          由于调用DownloadManager时，系统已经默认添加了getExternalStoragePublicDirectory，所以在
     *          此处不在调用getAppRootDir方法获得存储路径。
     * @return
     */
    public static String getThemDir(boolean isDownload) {
        if (isDownload) {
            return APP_NAME_PATH + "them/";
        } else {
            return getAppRootDir() + "them/";
        }
    }

    public static String getEmailFileZipDir(boolean isDownload){
        if (isDownload) {
            return APP_NAME_PATH + "email/attachment/";
        } else {
            return getAppRootDir() + "email/attachment/";
        }
    }
}
