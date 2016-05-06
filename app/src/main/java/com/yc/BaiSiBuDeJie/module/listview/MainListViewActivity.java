package com.yc.BaiSiBuDeJie.module.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.VolleyError;
import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseActivity;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.manager.RequestManager;
import com.yc.BaiSiBuDeJie.module.error.ErrorPortraitView;
import com.yc.BaiSiBuDeJie.module.listview.adapter.TabLayoutFragmentAdapter;
import com.yc.BaiSiBuDeJie.module.listview.entity.ShowApiEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.listview.fragment.ItemFragment;
import com.yc.BaiSiBuDeJie.module.recycleview.MainRecycleViewActivity;
import com.yc.BaiSiBuDeJie.net.IParserListener;
import com.yc.BaiSiBuDeJie.net.IRequestListener;
import com.yc.BaiSiBuDeJie.net.ParserFacade;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.utils.LogTools;
import com.yc.BaiSiBuDeJie.widget.SlipViewPager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YangChun on 2016/4/15.
 * http://7xl07p.com1.z0.glb.clouddn.com/image" + i + ".jpg
 */
public class MainListViewActivity extends BaseActivity implements IRequestListener, IParserListener, View.OnClickListener, OnDisableViewPagerSlipListener {
    private String[] mTitles = new String[3];
    private TabLayout mTabLayout;
    private SlipViewPager mViewPager;
    private TabLayoutFragmentAdapter mFragmentAdapter;
    private ErrorPortraitView errorPortraitVw;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private FloatingActionButton mToVolleyRecycleTvFAB;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private long exitTime = 0; ////记录第一次点击的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        mTabLayout = (TabLayout) findViewById(R.id.mainTabLay);
        mViewPager = (SlipViewPager) findViewById(R.id.mainViewPager);
        mViewPager.setScrollble(true);

        errorPortraitVw = (ErrorPortraitView) findViewById(R.id.errorPortraitVw);
        errorPortraitVw.isLoading();

        mToVolleyRecycleTvFAB = (FloatingActionButton) findViewById(R.id.fab);

        //标题
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void setViewSize() {
        if(Integer.parseInt(android.os.Build.VERSION.SDK) > 20) {
            DimensionUtil.setMargin(mTabLayout, 0, 50, 0, 0);
        }
    }

    @Override
    protected void initValue() {
        mTitles[0] = "图片";
        mTitles[1] = "视频";
        mTitles[2] = "段子";
    }

    @Override
    protected void bindEvent() {
        mToVolleyRecycleTvFAB.setOnClickListener(this);
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
            Snackbar.make(mToVolleyRecycleTvFAB, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
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
            mToVolleyRecycleTvFAB.setVisibility(View.VISIBLE);
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
            case R.id.fab:
                startActivity(new Intent(MainListViewActivity.this, MainRecycleViewActivity.class));
                break;
        }
    }
}
