package com.yc.baisibudejie.module.listview;

/**
 * 禁用viewpager的滑动事件，由于viewpager与listview、recylerview
 * 的滑动事件冲突，所以需要再次额外处理
 */

public interface OnDisableViewPagerSlipListener {
    void onDisableViewPagerSlip();
}