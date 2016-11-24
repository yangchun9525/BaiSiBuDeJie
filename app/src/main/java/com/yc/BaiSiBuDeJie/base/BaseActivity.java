package com.yc.BaiSiBuDeJie.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.utils.SharedPreferencesMgr;

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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPreferencesMgr.getInt("theme", 0) == 1) {
            setTheme(R.style.theme_night);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_night));
            }
        } else {
            setTheme(R.style.theme_day);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_day));
            }
        }

    }
}
