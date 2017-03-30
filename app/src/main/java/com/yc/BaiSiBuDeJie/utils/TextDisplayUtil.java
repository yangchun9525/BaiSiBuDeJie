package com.yc.baisibudejie.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.yc.baisibudejie.GlobalApp;

/**
 * 字体大小适配工具类
 *
 *   dp(dip): device independent pixels(设备独立像素). 不同设备有不同的显示效果,这个和设备硬件有关，一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
 *   dp也就是dip，这个和sp基本类似。如果设置表示长度、高度等属性时可以使用dp 或sp。但如果设置字体，需要使用sp。dp是与密度无关，sp除了与密度无关外，还与scale无关。如果屏幕密度为160，这时dp和sp和px是一 样的。1dp=1sp=1px，但如果使用px作单位，如果屏幕大小不变（假设还是3.2寸），而屏幕密度变成了320。那么原来TextView的宽度 设成160px，在密度为320的3.2寸屏幕里看要比在密度为160的3.2寸屏幕上看短了一半。但如果设置成160dp或160sp的话。系统会自动 将width属性值设置成320px的。也就是160 * 320 / 160。其中320 / 160可称为密度比例因子。也就是说，如果使用dp和sp，系统会根据屏幕密度的变化自动进行转换。
 *   px: pixels(像素). 不同设备显示效果相同，一般我们HVGA代表320x480像素，这个用的比较多。
 *   pt: point，是一个标准的长度单位，1pt＝1/72英寸，用于印刷业，非常简单易用；
 *   sp: scaled pixels(放大像素). 主要用于字体显示best for textsize。
 */
public class TextDisplayUtil {
    /**
     * 调整字体大小适应屏幕分辨率
     * @param dimenId
     * @return
     */
    public static float fixSpValue(int dimenId) {
        Resources resources = GlobalApp.getInstance().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float spValue = resources.getDimension(dimenId);
        return  (int) (spValue * displayMetrics.widthPixels / (1080.0f * displayMetrics.density) + 0.5f);
    }

    /**
     * 调整横屏字体大小适应屏幕分辨率
     * @param dimenId
     * @return
     */
    public static float fixLandScapeSpValue(int dimenId) {
        Resources resources = GlobalApp.getInstance().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float spValue = resources.getDimension(dimenId);
        return  (int) (spValue * displayMetrics.widthPixels / (1920.0f * displayMetrics.density) + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = GlobalApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = GlobalApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
