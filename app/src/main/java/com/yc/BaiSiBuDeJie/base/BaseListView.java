package com.yc.BaiSiBuDeJie.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class BaseListView extends ListView {
    // 解决scrollView 与 ListView产生冲突bug
    private boolean mHasScrollBar;

    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOverScrollMode(int mode) {
        // 消除ListView滑动到顶部和底部时出现的阴影
        super.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public void setHasScrollBar(boolean hasScrollBar) {
        this.mHasScrollBar = hasScrollBar;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mHasScrollBar) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
