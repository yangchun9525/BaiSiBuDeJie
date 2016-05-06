package com.yc.BaiSiBuDeJie.base;

import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void findView();

    protected abstract void setViewSize();

    protected abstract void initValue();

    protected abstract void bindEvent();

    protected abstract void addActivity();

    protected abstract void removeActivity();

    protected void init() {
        findView();
        setViewSize();
        initValue();
        bindEvent();
        addActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeActivity();
    }

    @Override
    public void finish() {
        super.finish();
        removeActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
