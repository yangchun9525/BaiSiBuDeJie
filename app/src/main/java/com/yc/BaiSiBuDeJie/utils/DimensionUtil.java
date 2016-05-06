package com.yc.BaiSiBuDeJie.utils;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.yc.BaiSiBuDeJie.GlobalApp;

/**
 * 屏幕适配工具类
 */
public class DimensionUtil {

    public static final float DEF_WIDTH = 1080.0f;
    public static final float DEF_HEIGHT = 1920.0f;

    private static DimensionUtil mDimensionUtil = null;

    public static float sWidth;        // 屏幕宽
    public static float sHeight;       // 屏幕高
    public static float sDensity;      // 屏幕密度

    public static float xScale;        // X方向的缩放比例
    public static float yScale;        // Y方向的缩放比例

    public static void init() {
        if(mDimensionUtil == null) {
            mDimensionUtil = new DimensionUtil();
        }
    }

    private DimensionUtil() {
        initValues(DEF_WIDTH, DEF_HEIGHT);
    }

    private void initValues(float ipWidth, float ipHeight) {
        DisplayMetrics dm = GlobalApp.getInstance().getResources().getDisplayMetrics();
        sWidth = dm.widthPixels;
        sHeight = dm.heightPixels;
        sDensity = dm.density;
        xScale = sWidth / ipWidth;
        yScale = sHeight / ipHeight;
    }

    /**
     * 获取屏幕高
     * @return
     */
    public static float getWidth() {
        return sWidth;
    }

    /**
     * 获取屏幕宽
     * @return
     */
    public static float getHeight() {
        return sHeight;
    }

    /**
     * 获取系统菜单的高度
     * @return
     */
    public static int getSystemMenuHeight() {
        if(sHeight > 600) {
            return 50;
        }
        return 42;
    }

    /**
     * 将dp转换为px
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        return (int) (dpValue * sDensity + 0.5f);
    }

    /**
     * 将px转换为dip
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / sDensity + 0.5f);
    }

    /**
     * 按水平方向的比例获取转换后的值
     * @param width
     * @return
     */
    public static int getTranslateWidth(int width) {
        if(width == 0) {
            return 0;
        }
        return Math.round(xScale * width);
    }

    /**
     * 按垂直方向的比例获取转换后的值
     * @param height
     * @return
     */
    public static int getTranslateHeight(int height) {
        if(height == 0) {
            return 0;
        }
        return Math.round(yScale * height);
    }

    /**
     * 按原始尺寸设置view大小
     * @param view
     * @param width
     * @param height
     */
    public static void setSizeOriginal(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(width > 0) {
            layoutParams.width = width;
        }
        if(height > 0) {
            layoutParams.height = height;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按各自方向的比例设置view大小
     * @param view
     * @param width
     * @param height
     */
    public static void setSize(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(width > 0) {
            layoutParams.width = getTranslateWidth(width);
        }
        if(height > 0) {
            layoutParams.height = getTranslateHeight(height);
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按交换后的的比例设置view大小
     * @param view
     * @param width
     * @param height
     */
    public static void setSizeChange(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(width > 0) {
            layoutParams.width = getTranslateHeight(width);
        }
        if(height > 0) {
            layoutParams.height = getTranslateWidth(height);
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按水平方向的比例设置view大小
     * @param view
     * @param width
     * @param height
     */
    public static void setSizeHorizontal(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(width > 0) {
            layoutParams.width = getTranslateWidth(width);
        }
        if(height > 0) {
            layoutParams.height = getTranslateWidth(height);
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按垂直方向的比例设置view大小
     * @param view
     * @param width
     * @param height
     */
    public static void setSizeVertical(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(width > 0) {
            layoutParams.width = getTranslateHeight(width);
        }
        if(height > 0) {
            layoutParams.height = getTranslateHeight(height);
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按原始尺寸设置view边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMarginOriginal(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        layoutParams.rightMargin = right;
        layoutParams.bottomMargin = bottom;
    }

    /**
     * 按各自方向比例设置view边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = getTranslateWidth(left);
        layoutParams.topMargin = getTranslateHeight(top);
        layoutParams.rightMargin = getTranslateWidth(right);
        layoutParams.bottomMargin = getTranslateHeight(bottom);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按交换后的方向比例设置view边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMarginChange(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = getTranslateHeight(left);
        layoutParams.topMargin = getTranslateWidth(top);
        layoutParams.rightMargin = getTranslateHeight(right);
        layoutParams.bottomMargin = getTranslateWidth(bottom);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按水平方向比例设置view边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMarginHorizontal(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = getTranslateWidth(left);
        layoutParams.topMargin = getTranslateWidth(top);
        layoutParams.rightMargin = getTranslateWidth(right);
        layoutParams.bottomMargin = getTranslateWidth(bottom);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按垂直方向比例设置view边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setMarginVertical(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = getTranslateHeight(left);
        layoutParams.topMargin = getTranslateHeight(top);
        layoutParams.rightMargin = getTranslateHeight(right);
        layoutParams.bottomMargin = getTranslateHeight(bottom);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 按各自方向比例设置view内边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(getTranslateWidth(left), getTranslateHeight(top), getTranslateWidth(right), getTranslateHeight(bottom));
    }

    /**
     * 按水平方向比例设置view内边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPaddingHorizontal(View view, int left, int top, int right, int bottom) {
        view.setPadding(getTranslateWidth(left), getTranslateWidth(top), getTranslateWidth(right), getTranslateWidth(bottom));
    }

    /**
     * 按垂直方向比例设置view内边距
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setPaddingVertical(View view, int left, int top, int right, int bottom) {
        view.setPadding(getTranslateHeight(left), getTranslateHeight(top), getTranslateHeight(right), getTranslateHeight(bottom));
    }

    /**
     * 使长宽按水平方向缩放的view居中显示
     * @param view
     * @param width
     */
    public static void reCenterHWidth(View view, int width) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int distance = getTranslateWidth(width) - getTranslateHeight(width);
        layoutParams.leftMargin += distance / 2;
        layoutParams.rightMargin += distance / 2;
    }

    /**
     * 使长宽按垂直方向缩放的view居中显示
     * @param view
     * @param width
     */
    public static void reCenterVWidth(View view, int width) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int distance = getTranslateHeight(width) - getTranslateWidth(width);
        layoutParams.leftMargin += distance / 2;
        layoutParams.rightMargin += distance / 2;
    }
 }
