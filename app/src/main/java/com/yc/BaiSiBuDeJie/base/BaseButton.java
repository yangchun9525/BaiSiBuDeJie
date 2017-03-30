package com.yc.baisibudejie.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.utils.ViewAttributeUtil;

public class BaseButton extends Button implements ColorUiInterface{

    private int attr_background = -1;
    private int attr_textAppreance = -1;

    public BaseButton(Context context) {
        super(context);
        this.setTransformationMethod(null);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
        this.setTransformationMethod(null);
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
        this.setTransformationMethod(null);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(GlobalApp.TYPE_FACE);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        ViewAttributeUtil.applyTextAppearance(this, themeId, attr_textAppreance);
    }
}
