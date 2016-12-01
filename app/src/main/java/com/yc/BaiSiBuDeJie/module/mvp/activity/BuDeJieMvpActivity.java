package com.yc.BaiSiBuDeJie.module.mvp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kymjs.gallery.KJGalleryActivity;
import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseActivity;
import com.yc.BaiSiBuDeJie.base.BaseRelativeLayout;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.module.error.ErrorPortraitView;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.mvp.model.MvpModel;
import com.yc.BaiSiBuDeJie.module.mvp.presenter.BuDeJieMvpPresenter;
import com.yc.BaiSiBuDeJie.module.recycleview.adapter.RecycleViewAdapter;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.utils.LogTools;
import com.yc.BaiSiBuDeJie.utils.TextDisplayUtil;
import com.yc.BaiSiBuDeJie.utils.ToastUtil;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by yangchun on 2016-9-18.
 */
public class BuDeJieMvpActivity extends BaseActivity implements MvpModel, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, SwipeBackActivityBase, View.OnClickListener {
    private int currentPage = 1;
    private BuDeJieMvpPresenter mBuDeJieMvpPresenter;
    private RecycleViewAdapter mMvpRecycleViewAdapter;
    private RecyclerView mRvMvpList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoadMore = false;
    private SwipeBackActivityHelper swipeBackActivityHelper;
    private ErrorPortraitView errorPortraitVw;

    private BaseRelativeLayout mTopRela;
    private View mViewActionBarDivide;
    private BaseTextView mTvLabel;
    private ImageView mIvOpenDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        setContentView(R.layout.activity_budejiemvp);
        mBuDeJieMvpPresenter = new BuDeJieMvpPresenter(this);
        init();
        mBuDeJieMvpPresenter.sendRequest(Const.SHOWAPI_TYPE_IMAGE,currentPage);
    }

    private void initAdapter(ArrayList<ContentEntity> contentlist){
        mMvpRecycleViewAdapter = new RecycleViewAdapter(BuDeJieMvpActivity.this, R.layout.item_main_recycle, contentlist);
        mMvpRecycleViewAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRvMvpList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Uri uri = Uri.parse(contentlist.get(position).weixin_url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.image:
                        ToastUtil.showLongToast("111111111111111");
                        String[] imageUrls = new String[1];
                        imageUrls[0] = contentlist.get(position).image3;
                        KJGalleryActivity.toGallery(BuDeJieMvpActivity.this, imageUrls);
                        break;
                }
            }
        });
//        mMvpRecycleViewAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(BuDeJieMvpActivity.this, "" + position, Toast.LENGTH_LONG).show();
//            }
//        });
        mMvpRecycleViewAdapter.setOnLoadMoreListener(BuDeJieMvpActivity.this);
        mRvMvpList.setAdapter(mMvpRecycleViewAdapter);
    }

    @Override
    protected void findView() {
        mRvMvpList = (RecyclerView) findViewById(R.id.rv_mvp_list);
        mRvMvpList.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        errorPortraitVw = (ErrorPortraitView) findViewById(R.id.errorPortraitVw);
        errorPortraitVw.isLoading();

        mTopRela = (BaseRelativeLayout) findViewById(R.id.topRela);
        mViewActionBarDivide = findViewById(R.id.view_action_bar_divide);
        mTvLabel = (BaseTextView) findViewById(R.id.tvLabel);
        mIvOpenDrawer = (ImageView) findViewById(R.id.ivOpenDraw);
    }

    @Override
    protected void setViewSize() {
        DimensionUtil.setSize(mTopRela, 0, 150);
        DimensionUtil.setSize(mIvOpenDrawer, 120, 120);

        DimensionUtil.setMargin(mIvOpenDrawer, 35, 15, 0, 0);
        DimensionUtil.setMargin(mViewActionBarDivide, 0, 149, 0, 0);
        DimensionUtil.setMargin(mTvLabel, 30, 35, 0, 0);
        mTvLabel.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_60));
    }

    @Override
    protected void initValue() {
        mTvLabel.setText("MVP-RecycleView");
    }

    @Override
    protected void bindEvent() {
        mIvOpenDrawer.setOnClickListener(this);
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
            mMvpRecycleViewAdapter.addData(singleDataEntity.contentlist);
            mMvpRecycleViewAdapter.loadMoreComplete();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.ivOpenDraw:
                onBackPressed();
                break;
        }
    }
}
