package com.yc.baisibudejie;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yc.baisibudejie.base.BaseActivity;
import com.yc.baisibudejie.module.listview.MainListViewActivity;
import com.yc.baisibudejie.receiver.NetWorkBroadcastReceiver;

/**
 * Created by YangChun on 2016/4/21.
 */
public class LoadingActivity extends BaseActivity implements Animation.AnimationListener {
    private ImageView loadingIv;
    private Animation mFadeIn;
    private NetWorkBroadcastReceiver networkBroadcast = new NetWorkBroadcastReceiver();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoadingActivityTheme);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);
        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                LoadingActivity.this.registerReceiver(networkBroadcast, filter);
                // 加载session
            }
        }, 1000);
    }

    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_in);
        mFadeIn.setDuration(5000);
    }

    @Override
    protected void findView() {
        loadingIv = (ImageView) findViewById(R.id.loadingIv);
        initAnim();
        loadingIv.startAnimation(mFadeIn);
    }

    @Override
    protected void setViewSize() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void bindEvent() {
        mFadeIn.setAnimationListener(this);
    }

    @Override
    protected void addActivity() {
        GlobalApp.getInstance().addActivity(this);
    }

    @Override
    protected void removeActivity() {
        GlobalApp.getInstance().removeActivity(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(new Intent(LoadingActivity.this, MainListViewActivity.class));
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkBroadcast);
    }
}
