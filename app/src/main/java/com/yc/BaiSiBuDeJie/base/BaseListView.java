package com.yc.BaiSiBuDeJie.base;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.yc.BaiSiBuDeJie.utils.ViewAttributeUtil;

public class BaseListView extends ListView implements ColorUiInterface{
    private int attr_background = -1;
    private int attr_divider = -1;
    private int divider_height = 0;
    // 解决scrollView 与 ListView产生冲突bug
    private boolean mHasScrollBar;

    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_divider = ViewAttributeUtil.getDividerAttribute(attrs);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_divider = ViewAttributeUtil.getDividerAttribute(attrs);
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

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        this.divider_height = getDividerHeight();
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        int res_divider = this.attr_divider;
        if(res_divider > 0) {
            TypedArray ta = themeId.obtainStyledAttributes(new int[]{res_divider});
            Drawable drawable = ta.getDrawable(0);
            ((ListView)getView()).setDivider(drawable);
            ta.recycle();
        }
        setDividerHeight(divider_height);
    }
}
