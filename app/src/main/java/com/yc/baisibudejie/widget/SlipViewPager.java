package com.yc.baisibudejie.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ZhuBibo on 2015/6/24.
 * 可控制滑动开关viewpager
 */
public class SlipViewPager extends ViewPager {

    // 默认为可滑动
    private boolean isScrollble = true;

    public SlipViewPager(Context context) {
        super(context);
    }

    public SlipViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        if (isScrollble) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScrollble)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    public boolean isScrollble() {
        return isScrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.isScrollble = scrollble;
    }
}
