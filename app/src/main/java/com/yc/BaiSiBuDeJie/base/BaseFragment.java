package com.yc.BaiSiBuDeJie.base;

import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    protected abstract void findView();
    protected abstract void setViewSize();
    protected abstract void initValue();
    protected abstract void bindEvent();

    protected void init() {
        findView();
        setViewSize();
        initValue();
        bindEvent();
    }
}
