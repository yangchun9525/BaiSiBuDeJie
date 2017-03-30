package com.yc.baisibudejie.module.listview;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.OnekeyShare;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseActivity;
import com.yc.baisibudejie.base.BaseButton;
import com.yc.baisibudejie.base.BaseRelativeLayout;
import com.yc.baisibudejie.base.BaseTextView;
import com.yc.baisibudejie.cache.LruCacheManager;
import com.yc.baisibudejie.constant.Const;
import com.yc.baisibudejie.manager.RequestManager;
import com.yc.baisibudejie.module.error.ErrorPortraitView;
import com.yc.baisibudejie.module.listview.adapter.TabLayoutFragmentAdapter;
import com.yc.baisibudejie.module.listview.entity.MessageEntity;
import com.yc.baisibudejie.module.listview.entity.SingleDataEntity;
import com.yc.baisibudejie.module.listview.fragment.ItemFragment;
import com.yc.baisibudejie.module.mvp.activity.BuDeJieMvpActivity;
import com.yc.baisibudejie.module.mvvm.MvvmRecycleViewActivity;
import com.yc.baisibudejie.module.recycleview.MainRecycleViewActivity;
import com.yc.baisibudejie.net.IParserListener;
import com.yc.baisibudejie.net.IRequestListener;
import com.yc.baisibudejie.net.ParserFacade;
import com.yc.baisibudejie.utils.ColorUiUtil;
import com.yc.baisibudejie.utils.DimensionUtil;
import com.yc.baisibudejie.utils.FileUtil;
import com.yc.baisibudejie.utils.LogTools;
import com.yc.baisibudejie.utils.SecurityUtil;
import com.yc.baisibudejie.utils.SharedPreferencesMgr;
import com.yc.baisibudejie.utils.TextDisplayUtil;
import com.yc.baisibudejie.utils.ValidatesUtil;
import com.yc.baisibudejie.widget.SlipViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.backPress;

/**
 * Created by YangChun on 2016/4/15.
 * http://7xl07p.com1.z0.glb.clouddn.com/image" + i + ".jpg
 */
public class MainListViewActivity extends BaseActivity implements IRequestListener, IParserListener, View.OnClickListener, OnDisableViewPagerSlipListener, ViewPager.OnPageChangeListener,OnPermissionCallback {
    private String[] mTitles = new String[3];
    private TabLayout mTabLayout;
    private SlipViewPager mViewPager;
    private TabLayoutFragmentAdapter mFragmentAdapter;
    private ErrorPortraitView errorPortraitVw;
    private BaseTextView mTvToMvcRv,mTvToMvpRv,mTvToMvvmRv,mTvChangeSkin,mTvLabel;
    private ImageView mIvOpenDrawer;
    private BaseRelativeLayout mTopRela,mLeftDrawerLayout,mRootLinear;
    private View mViewActionBarDivide;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private DrawerLayout mMainDrawerLayout;
    private long exitTime = 0; ////记录第一次点击的时间
    private ItemFragment textFragment = null;
    private ItemFragment imageFragment = null;
    private ItemFragment videoFragment = null;
    private BaseButton mRetryButton;

    private PermissionHelper permissionHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listview);
        init();
        permissionHelper = PermissionHelper.getInstance(this);
        permissionHelper
                .setForceAccepting(true) // default is false. its here so you know that it exists.
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        mViewActionBarDivide = findViewById(R.id.view_action_bar_divide);
        mRootLinear = (BaseRelativeLayout) findViewById(R.id.rootLinear);
        mLeftDrawerLayout = (BaseRelativeLayout) findViewById(R.id.leftDrawerLayout);
        mTvLabel = (BaseTextView) findViewById(R.id.tvLabel);
        mTopRela = (BaseRelativeLayout) findViewById(R.id.topRela);
        mTabLayout = (TabLayout) findViewById(R.id.mainTabLay);
        mViewPager = (SlipViewPager) findViewById(R.id.mainViewPager);
        mViewPager.setScrollble(true);

        errorPortraitVw = (ErrorPortraitView) findViewById(R.id.errorPortraitVw);
        errorPortraitVw.isLoading();
        mRetryButton = (BaseButton) findViewById(R.id.commonRetryBtn);
        mRetryButton.setOnClickListener(this);

        mTvToMvcRv = (BaseTextView) findViewById(R.id.tvToMvcRv);
        mTvToMvpRv = (BaseTextView) findViewById(R.id.tvToMvpRv);
        mTvToMvvmRv = (BaseTextView) findViewById(R.id.tvToMvvmRv);
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
        DimensionUtil.setMargin(mViewActionBarDivide, 0, 149, 0, 0);
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
        mTvToMvvmRv.setOnClickListener(this);
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
        LogTools.i("isFullScreen",JCVideoPlayerStandard.isFullScreen+"");
        if(!ValidatesUtil.isEmpty(JCVideoPlayerStandard.isFullScreen) && JCVideoPlayerStandard.isFullScreen.equals("true")){
            backPress();
            JCVideoPlayerStandard.isFullScreen = "false";
        }else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(mTabLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                JCVideoPlayerStandard.isFullScreen = null;
                GlobalApp.getInstance().exit();
//                finish();
            }
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
        String appPath = "BaiSiBuDeJie";

        File file = Environment.getExternalStoragePublicDirectory(appPath);
        file.mkdirs();
        File resultFile = FileUtil.createFile(file.getAbsolutePath() + "/result.txt");
        FileUtil.writeToFile(resultFile, result,false);

        LruCacheManager.addStringToCache(SecurityUtil.getMD5(requestCode), result);
        LogTools.i("test-result", result);
        ParserFacade parser = new ParserFacade(requestCode, this);
        parser.start(result, "pagebean", SingleDataEntity.class);
    }

    @Override
    public void onRequestError(String requestCode, VolleyError volleyError) {
        String result = LruCacheManager.getStringFromCache(SecurityUtil.getMD5(requestCode));
        if (!ValidatesUtil.isEmpty(result)) {
            ParserFacade parser = new ParserFacade(requestCode, this);
            parser.start(result, "pagebean", SingleDataEntity.class);
        }else {
//            mIdeaListLv.setVisibility(View.GONE);
            errorPortraitVw.isError();
        }
    }

    @Override
    public void onParserSuccess(String requestCode, Object result) {
        SingleDataEntity res = (SingleDataEntity) result;

        if (requestCode.equals(Const.SHOWAPI_TYPE_IMAGE)) {
            imageFragment = ItemFragment.newInstance(res, Const.SHOWAPI_TYPE_IMAGE,this);
            mFragments.add(imageFragment);
            sendRequest(Const.SHOWAPI_TYPE_VIDEO);
        } else if (requestCode.equals(Const.SHOWAPI_TYPE_VIDEO)) {
            videoFragment = ItemFragment.newInstance(res, Const.SHOWAPI_TYPE_VIDEO,this);
            mFragments.add(videoFragment);
            sendRequest(Const.SHOWAPI_TYPE_TEXT);
        } else if (requestCode.equals(Const.SHOWAPI_TYPE_TEXT)) {
            textFragment = ItemFragment.newInstance(res, Const.SHOWAPI_TYPE_TEXT,this);
            mFragments.add(textFragment);
        }
        if (mFragments.size() == 3) {
            errorPortraitVw.setVisibility(View.GONE);
            setupViewPager(mFragments);
        }
    }

    @Override
    public void onParserError(String requestCode, MessageEntity messageEntity) {
        LogTools.i("test-onParserError", messageEntity.showapi_res_error);
        errorPortraitVw.setVisibility(View.VISIBLE);
        errorPortraitVw.isError();
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
            case R.id.tvToMvvmRv:
                mMainDrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainListViewActivity.this, MvvmRecycleViewActivity.class));
                break;
            case R.id.commonRetryBtn:
                sendRequest(Const.SHOWAPI_TYPE_IMAGE);
                break;
            case R.id.tvToChangeSkin:
                errorPortraitVw.setVisibility(View.VISIBLE);
                if(SharedPreferencesMgr.getInt("theme", 0) == 1) {
                    SharedPreferencesMgr.setInt("theme", 0);
                    setTheme(R.style.theme_day);
                } else {
                    SharedPreferencesMgr.setInt("theme", 1);
                    setTheme(R.style.theme_night);
                }
                mMainDrawerLayout.closeDrawer(GravityCompat.START);
                changeSkin();
                refreshStatusBar();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(imageFragment != null && imageFragment.mListView != null) {
                            imageFragment.refreshListViewUI();
                        }
                        if(videoFragment != null && videoFragment.mListView != null) {
                            videoFragment.refreshListViewUI();
                        }
                        if(textFragment != null && textFragment.mListView != null) {
                            textFragment.refreshListViewUI();
                        }
                        errorPortraitVw.setVisibility(View.GONE);
                    }
                }, 1000);
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
    public void changeSkin(){
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
        LogTools.i("yc-onPageScrolled", position+"");
    }

    @Override
    public void onPageSelected(int position) {
        JCVideoPlayer.releaseAllVideos();
        LogTools.i("yc-onPageSelected", position+"");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        LogTools.i("yc-onPageScrollStateChanged", state+"");
//        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//            //正在滑动   pager处于正在拖拽中
//            LogTools.i("测试代码", "onPageScrollStateChanged=======正在滑动" + "SCROLL_STATE_DRAGGING");
//        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
//            //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
//            LogTools.i("测试代码", "onPageScrollStateChanged=======自动沉降" + "SCROLL_STATE_SETTLING");
//        } else if (state == ViewPager.SCROLL_STATE_IDLE) {
//            //空闲状态  pager处于空闲状态
//            LogTools.i("测试代码", "onPageScrollStateChanged=======空闲状态" + "SCROLL_STATE_IDLE");
////            JCVideoPlayer.releaseAllVideos();
//        }
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {

    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {

    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {

    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {

    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {

    }

    @Override
    public void onNoPermissionNeeded() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
