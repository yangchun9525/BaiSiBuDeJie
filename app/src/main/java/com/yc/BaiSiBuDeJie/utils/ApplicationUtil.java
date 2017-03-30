package com.yc.baisibudejie.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yc.baisibudejie.GlobalApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yangchun on 2015/5/28.
 * android系统相关的工具类
 */
public class ApplicationUtil {

    private static long lastClickTime;
    private static PowerManager.WakeLock wakeLock = null;

    /**
     * 获取应用程序的版本号名称
     * @return
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pm = GlobalApp.getInstance().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(GlobalApp.getInstance().getPackageName(), 0);
            versionName += packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取应用程序的版本号
     * @return
     */
    public static int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pm = GlobalApp.getInstance().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(GlobalApp.getInstance().getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取应用包名
     * @return
     */
    public static String getAppPackageName(){
        String packageName = "";
        try {
            PackageManager pm = GlobalApp.getInstance().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(GlobalApp.getInstance().getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * 关闭软键盘
     * @param activity
     */
    public static void closeSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭软键盘
     * @param activity
     * @param eventX 事件x坐标
     * @param eventY 事件y坐标
     * @param exceptViews 不响应关闭软键盘事件的区域
     */
    public static void closeSoftInput(Activity activity, int eventX, int eventY, View... exceptViews) {
        boolean isClose = true;
        if (exceptViews != null && exceptViews.length > 0) {
            for (View exceptView : exceptViews) {
                Rect exceptRect = new Rect();
                exceptView.getGlobalVisibleRect(exceptRect);
                if (exceptRect.contains(eventX, eventY)) {
                    isClose = false;
                    break;
                }
            }
        }
        if(isClose) {
            closeSoftInput(activity);
        }
    }

    /**
     * 获取当前页面的截图
     * @param activity
     * @param config
     * @return
     */
    public static Bitmap getScreenShot(Activity activity, Bitmap.Config config) {
        View view = activity.getWindow().getDecorView().getRootView();
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        view.draw(new Canvas(bitmap));
        return  bitmap;
    }

    /**
     * 跳转至指定的activityClass
     * @param activity
     */
    public static void startActivityAnim(Activity activity, Class<? extends Activity> class1, int enterAnim, int exitAnim) {
        Intent intent = new Intent(activity, class1);
        activity.startActivity(intent);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 结束activity
     * @param activity
     */
    public static void finishActivityAnim(Activity activity, int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(0, exitAnim);
    }

    /**
     * 是否显示emptyView
     */
    public static void showEmptyView(ArrayList datas, View emptyView) {
        if(datas == null || datas.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }
    /**
     * To avoid muti click.
     * @param interval
     * @return
     */
    public static boolean isFastDoubleClick(long interval) {

        long time = System.currentTimeMillis();
        boolean isFlag = time - lastClickTime < interval;
        if(!isFlag) {
            lastClickTime = time;
        }
        return isFlag;
    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */
    public static void acquireWakeLock(Context context) {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "budejie_lock");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    //释放设备电源锁
    public static void releaseWakeLock() {
        if (null != wakeLock) {
            wakeLock.release();
            wakeLock = null;
        }
    }

//    public static String  getTopAppPackage(Context context) {
//        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
//                .getSystemService("activity");
//        ActivityManager.RunningTaskInfo currentRun = activityManager.getRunningTasks(1).get(0);
//        ComponentName nowApp = currentRun.topActivity;
//        String packname = nowApp.getPackageName();
//        return packname;
//    }

    public static boolean isAppOnForeground(Context context, String mPackageName) {
        ActivityManager mActivityManager  = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            String classname = tasksInfo.get(0).topActivity.getClassName();
            if (mPackageName.equals(classname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断class是否在堆栈中
     * @param context
     * @param mClassName
     * @return
     */
    public static boolean isClassOnForeground(Context context, String mClassName) {
        ActivityManager mActivityManager  = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> tasksInfo = mActivityManager.getRunningTasks(10);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            for (int i = 0;i < tasksInfo.size();i++) {
                String className = tasksInfo.get(i).topActivity.getClassName();
                if (mClassName.equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    //响铃和振动
    public static void playSoundAndVibrator(Context context){
        try {
            NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification nt = new Notification();
            nt.defaults = Notification.DEFAULT_SOUND;
            int soundId = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
            mgr.notify(soundId, nt);
//					  return soundId;

            Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
            long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
            vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取语言版本
     * @param context
     * @return
     */
    public static String getLanguage(Context context){
        return context.getResources().getConfiguration().locale.getLanguage();
    }
}
