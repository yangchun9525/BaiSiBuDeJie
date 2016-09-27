package com.yc.BaiSiBuDeJie.module.listview;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseActivity;
import com.yc.BaiSiBuDeJie.base.BaseRelativeLayout;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.manager.RequestManager;
import com.yc.BaiSiBuDeJie.module.error.ErrorPortraitView;
import com.yc.BaiSiBuDeJie.module.listview.adapter.TabLayoutFragmentAdapter;
import com.yc.BaiSiBuDeJie.module.listview.entity.ShowApiEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.listview.fragment.ItemFragment;
import com.yc.BaiSiBuDeJie.module.mvp.activity.BuDeJieMvpActivity;
import com.yc.BaiSiBuDeJie.module.recycleview.MainRecycleViewActivity;
import com.yc.BaiSiBuDeJie.net.IParserListener;
import com.yc.BaiSiBuDeJie.net.IRequestListener;
import com.yc.BaiSiBuDeJie.net.ParserFacade;
import com.yc.BaiSiBuDeJie.utils.ColorUiUtil;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.utils.LogTools;
import com.yc.BaiSiBuDeJie.utils.SharedPreferencesMgr;
import com.yc.BaiSiBuDeJie.utils.TextDisplayUtil;
import com.yc.BaiSiBuDeJie.widget.SlipViewPager;

import java.util.ArrayList;
import java.util.HashMap;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by YangChun on 2016/4/15.
 * http://7xl07p.com1.z0.glb.clouddn.com/image" + i + ".jpg
 */
public class MainListViewActivity extends BaseActivity implements IRequestListener, IParserListener, View.OnClickListener, OnDisableViewPagerSlipListener, ViewPager.OnPageChangeListener {
    private String[] mTitles = new String[3];
    private TabLayout mTabLayout;
    private SlipViewPager mViewPager;
    private TabLayoutFragmentAdapter mFragmentAdapter;
    private ErrorPortraitView errorPortraitVw;
    private BaseTextView mTvToMvcRv,mTvToMvpRv,mTvChangeSkin,mTvLabel;
    private ImageView mIvOpenDrawer;
    private BaseRelativeLayout mTopRela,mLeftDrawerLayout,mRootLinear;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private DrawerLayout mMainDrawerLayout;
    private long exitTime = 0; ////记录第一次点击的时间

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listview);
        init();
        sendRequest(Const.SHOWAPI_TYPE_IMAGE);
    }

    private void sendRequest(String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("showapi_appid", Const.SHOWAPI_APPID);
        params.put("showapi_sign", Const.SHOWAPI_SIGN);
        params.put("type", type);
        RequestManager.getInstance().deliverCommonRequest(type, params, this, null);
    }

    @Override
    protected void findView() {
        mRootLinear = (BaseRelativeLayout) findViewById(R.id.rootLinear);
        mLeftDrawerLayout = (BaseRelativeLayout) findViewById(R.id.leftDrawerLayout);
        mTvLabel = (BaseTextView) findViewById(R.id.tvLabel);
        mTopRela = (BaseRelativeLayout) findViewById(R.id.topRela);
        mTabLayout = (TabLayout) findViewById(R.id.mainTabLay);
        mViewPager = (SlipViewPager) findViewById(R.id.mainViewPager);
        mViewPager.setScrollble(true);

        errorPortraitVw = (ErrorPortraitView) findViewById(R.id.errorPortraitVw);
        errorPortraitVw.isLoading();

        mTvToMvcRv = (BaseTextView) findViewById(R.id.tvToMvcRv);
        mTvToMvpRv = (BaseTextView) findViewById(R.id.tvToMvpRv);
        mTvChangeSkin = (BaseTextView) findViewById(R.id.tvToChangeSkin);
        mIvOpenDrawer = (ImageView) findViewById(R.id.ivOpenDraw);

        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.mainDrawer);
        mMainDrawerLayout.setScrimColor(getResources().getColor(R.color.transparent_background));
        mMainDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void setViewSize() {
        DimensionUtil.setSize(mTopRela, 0, 150);
        DimensionUtil.setSize(mIvOpenDrawer, 120, 120);

        DimensionUtil.setMargin(mIvOpenDrawer, 35, 15, 0, 0);
        DimensionUtil.setMargin(mTvLabel, 30, 35, 0, 0);
        DimensionUtil.setMargin(mRootLinear, 0, 150, 0, 0);
        mTvLabel.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_60));
    }

    @Override
    protected void initValue() {
        mTitles[0] = "图片";
        mTitles[1] = "视频";
        mTitles[2] = "段子";
    }

    @Override
    protected void bindEvent() {
        mTvToMvcRv.setOnClickListener(this);
        mTvToMvpRv.setOnClickListener(this);
        mTvChangeSkin.setOnClickListener(this);
        mIvOpenDrawer.setOnClickListener(this);
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
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Snackbar.make(mTabLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    /**
     * 将viewpager事件 绑定在tablayout
     */
    private void setupViewPager(ArrayList<Fragment> mFragments) {
        mFragmentAdapter = new TabLayoutFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setScrollble(true);
        mViewPager.setOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
    }

    @Override
    public void onDisableViewPagerSlip() {
        mViewPager.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onRequestSuccess(String requestCode, String result) {
        LogTools.i("test-result", result);
        ParserFacade parser = new ParserFacade(requestCode, this);
        parser.start(result, "pagebean", SingleDataEntity.class);
    }

    @Override
    public void onRequestError(String requestCode, VolleyError volleyError) {
    }

    @Override
    public void onParserSuccess(String requestCode, Object result) {
        SingleDataEntity res = (SingleDataEntity) result;

        ItemFragment textFragment = null;
        ItemFragment imageFragment = null;
        ItemFragment videoFragment = null;

        if (requestCode.equals(Const.SHOWAPI_TYPE_IMAGE)) {
            imageFragment = ItemFragment.newInstance(res, Const.SHOWAPI_TYPE_IMAGE);
            mFragments.add(imageFragment);
            sendRequest(Const.SHOWAPI_TYPE_VIDEO);
        } else if (requestCode.equals(Const.SHOWAPI_TYPE_VIDEO)) {
            videoFragment = ItemFragment.newInstance(res, Const.SHOWAPI_TYPE_VIDEO);
            mFragments.add(videoFragment);
            sendRequest(Const.SHOWAPI_TYPE_TEXT);
        } else if (requestCode.equals(Const.SHOWAPI_TYPE_TEXT)) {
            textFragment = ItemFragment.newInstance(res, Const.SHOWAPI_TYPE_TEXT);
            mFragments.add(textFragment);
        }
        if (mFragments.size() == 3) {
            errorPortraitVw.setVisibility(View.GONE);
            setupViewPager(mFragments);
        }
    }

    @Override
    public void onParserError(String requestCode, ShowApiEntity messageEntity) {
        LogTools.i("test-onParserError", messageEntity.showapi_res_error);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvToMvcRv:
                mMainDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainListViewActivity.this, MainRecycleViewActivity.class));
                break;
            case R.id.tvToMvpRv:
                mMainDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainListViewActivity.this, BuDeJieMvpActivity.class));
                break;
            case R.id.tvToChangeSkin:
                if(SharedPreferencesMgr.getInt("theme", 0) == 1) {
                    SharedPreferencesMgr.setInt("theme", 0);
                    setTheme(R.style.theme_1);
                } else {
                    SharedPreferencesMgr.setInt("theme", 1);
                    setTheme(R.style.theme_2);
                }
                mMainDrawerLayout.closeDrawer(GravityCompat.START);
                final View rootView = getWindow().getDecorView();
                if(Build.VERSION.SDK_INT >= 14) {
                    rootView.setDrawingCacheEnabled(true);
                    rootView.buildDrawingCache(true);
                    final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                    rootView.setDrawingCacheEnabled(false);
                    if (null != localBitmap && rootView instanceof ViewGroup) {
                        final View localView2 = new View(getApplicationContext());
                        localView2.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        ((ViewGroup) rootView).addView(localView2, params);
                        localView2.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                ColorUiUtil.changeTheme(rootView, getTheme());
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ((ViewGroup) rootView).removeView(localView2);
                                localBitmap.recycle();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
                    }
                } else {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                }
                refreshStatusBar();
                break;
            case R.id.ivOpenDraw:
                if (mMainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mMainDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mMainDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    /**
     * 刷新 StatusBar
     */
    private void refreshStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mMainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mMainDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mMainDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            //正在滑动   pager处于正在拖拽中
            LogTools.i("测试代码", "onPageScrollStateChanged=======正在滑动" + "SCROLL_STATE_DRAGGING");
        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
            //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
            LogTools.i("测试代码", "onPageScrollStateChanged=======自动沉降" + "SCROLL_STATE_SETTLING");
        } else if (state == ViewPager.SCROLL_STATE_IDLE) {
            //空闲状态  pager处于空闲状态
            LogTools.i("测试代码", "onPageScrollStateChanged=======空闲状态" + "SCROLL_STATE_IDLE");
            JCVideoPlayer.releaseAllVideos();
        }
    }
}
