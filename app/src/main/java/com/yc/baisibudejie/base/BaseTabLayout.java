package com.yc.baisibudejie.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yc.baisibudejie.utils.ViewAttributeUtil;

/**
 * Created by yangchun on 2016-9-27.
 */

public class BaseTabLayout extends TabLayout implements ColorUiInterface{
    private int attr_background = -1;
    public BaseTabLayout(Context context) {
        super(context);
    }

    public BaseTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public BaseTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if(attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}
