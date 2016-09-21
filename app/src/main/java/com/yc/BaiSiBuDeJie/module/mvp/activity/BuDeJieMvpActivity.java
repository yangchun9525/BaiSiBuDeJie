package com.yc.BaiSiBuDeJie.module.mvp.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseActivity;
import com.yc.BaiSiBuDeJie.base.BaseButton;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.module.error.ErrorPortraitView;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.mvp.adapter.MvpRecycleViewAdapter;
import com.yc.BaiSiBuDeJie.module.mvp.model.MvpModel;
import com.yc.BaiSiBuDeJie.module.mvp.presenter.BuDeJieMvpPresenter;
import com.yc.BaiSiBuDeJie.module.recycleview.adapter.RecycleViewAdapter;
import com.yc.BaiSiBuDeJie.utils.LogTools;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by yangchun on 2016-9-18.
 */
public class BuDeJieMvpActivity extends BaseActivity implements MvpModel, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SwipeBackActivityBase {
    private int currentPage = 1;
    private BuDeJieMvpPresenter mBuDeJieMvpPresenter;
    private RecycleViewAdapter mMvpRecycleViewAdapter;
    private RecyclerView mRvMvpList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoadMore = false;
    private SwipeBackActivityHelper swipeBackActivityHelper;
    private ErrorPortraitView errorPortraitVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        setContentView(R.layout.activity_budejiemvp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.to_mvp_activity);
        mBuDeJieMvpPresenter = new BuDeJieMvpPresenter(this);
        init();
        mBuDeJieMvpPresenter.sendRequest(Const.SHOWAPI_TYPE_IMAGE,currentPage);
    }

    private void initAdapter(ArrayList<ContentEntity> contentlist){
        mMvpRecycleViewAdapter = new RecycleViewAdapter(BuDeJieMvpActivity.this, R.layout.item_main_recycle, contentlist);
        mMvpRecycleViewAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mMvpRecycleViewAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(BuDeJieMvpActivity.this, "" + position, Toast.LENGTH_LONG).show();
            }
        });
        mMvpRecycleViewAdapter.setOnLoadMoreListener(Const.PAGESIZE, BuDeJieMvpActivity.this);
        mRvMvpList.setAdapter(mMvpRecycleViewAdapter);
    }

    @Override
    protected void findView() {
        mRvMvpList = (RecyclerView) findViewById(R.id.rv_mvp_list);
        mRvMvpList.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        errorPortraitVw = (ErrorPortraitView) findViewById(R.id.errorPortraitVw);
        errorPortraitVw.isLoading();
    }

    @Override
    protected void setViewSize() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void bindEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
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
    public void onCompleted(SingleDataEntity singleDataEntity) {
        LogTools.i("test-currentPage",currentPage+"");
        currentPage = singleDataEntity.currentPage + 1;
        errorPortraitVw.setVisibility(View.GONE);
        if(isLoadMore) {
            isLoadMore = false;
            mMvpRecycleViewAdapter.setData(singleDataEntity.contentlist);
            mMvpRecycleViewAdapter.isNextLoad(true);
        }else{
            initAdapter(singleDataEntity.contentlist);
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onError(Throwable e) {
        LogTools.i("test-onError",e.toString());
        errorPortraitVw.isError();
    }

    @Override
    public void onLoadMoreRequested() {
        isLoadMore = true;
        mBuDeJieMvpPresenter.sendRequest(Const.SHOWAPI_TYPE_IMAGE,currentPage);
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        currentPage = 1;
        mBuDeJieMvpPresenter.sendRequest(Const.SHOWAPI_TYPE_IMAGE,currentPage);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackActivityHelper.onPostCreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
           onBackPressed();
        }
        return true;
    }
}
