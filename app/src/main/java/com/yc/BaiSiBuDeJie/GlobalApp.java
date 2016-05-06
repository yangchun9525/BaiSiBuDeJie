package com.yc.BaiSiBuDeJie;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;

import com.yc.BaiSiBuDeJie.net.MyVolley;
import com.yc.BaiSiBuDeJie.utils.DebugUtil;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WangWenLong on 2016/3/9.
 */
public class GlobalApp extends Application {
    private static GlobalApp sGlobalApp;
    // 字体
    public static Typeface TYPE_FACE;
    // 网络类型
    public static String netType;
    // 是否显示网络错误(true：当前网络正常；false：当前网络异常)
    public static boolean isShowNetworkError = true;
    // 当前启动activity的list，退出时finish所有activity
    private List<Activity> mActivityList = new LinkedList<Activity>();

    public static GlobalApp getInstance() {
        return sGlobalApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sGlobalApp = this;

        // 开启严格模式
        DebugUtil.enableStrictMode();

        initApp();

    }

    private void initApp() {
        DimensionUtil.init();
        MyVolley.init(getApplicationContext());
    }

    //添加activity
    public void addActivity(Activity activity){
        mActivityList.add(activity);
    }

    //移除activity
    public void removeActivity(Activity activity){
        mActivityList.remove(activity);
    }

    /**
     * 关闭所有的activity
     */
    private void finishAllActivity(){
        try {
            for (int size = mActivityList.size(); size > 0; size--) {
                Activity activity = mActivityList.get(size - 1);
                activity.finish();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 重新初始化
     */
    public void prepareLogin() {
        // 关闭所有的activity
        finishAllActivity();
    }

    /**
     * 退出应用
     */
    public void exit() {
        // 重新初始化
        prepareLogin();
        /**
         * 如果调用 Process.kill 或者 System.exit 之类的方法杀死进程，请务必在此之前调用此方法，用来保存统计数据
         */
        System.exit(0);
    }
}
