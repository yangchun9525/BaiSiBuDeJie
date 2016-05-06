package com.yc.BaiSiBuDeJie.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.yc.BaiSiBuDeJie.GlobalApp;

public class BaseButton extends Button {

    public BaseButton(Context context) {
        super(context);
        this.setTransformationMethod(null);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTransformationMethod(null);
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTransformationMethod(null);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(GlobalApp.TYPE_FACE);
    }
}
