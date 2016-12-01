package com.yc.BaiSiBuDeJie.module.mvvm;

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
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;
import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseActivity;
import com.yc.BaiSiBuDeJie.base.BaseRelativeLayout;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.cache.LruCacheManager;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.constant.HttpURL;
import com.yc.BaiSiBuDeJie.module.error.ErrorPortraitView;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.mvvm.adapter.DataBindingAdapter;
import com.yc.BaiSiBuDeJie.net.parser.JsonParser;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.utils.LogTools;
import com.yc.BaiSiBuDeJie.utils.SecurityUtil;
import com.yc.BaiSiBuDeJie.utils.TextDisplayUtil;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by YangChun on 2016/4/19.
 */
public class MvvmRecycleViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,SwipeBackActivityBase, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private DataBindingAdapter mQuickAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ErrorPortraitView errorPortraitVw;
    private Subscription subscription;
    private int currentPage = 1;
    private boolean isLoadMore = false;
    private ArrayList<ContentEntity> contentlist = new ArrayList<>();
    private SwipeBackActivityHelper swipeBackActivityHelper;

    private BaseRelativeLayout mTopRela;
    private View mViewActionBarDivide;
    private BaseTextView mTvLabel;
    private ImageView mIvOpenDrawer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        setContentView(R.layout.activity_main_recyclerview);
        init();
        rxVolleyPost(Const.SHOWAPI_TYPE_IMAGE);
    }

    private void rxVolleyPost(String type) {
        HttpParams params = new HttpParams();
        params.put("showapi_appid", Const.SHOWAPI_APPID);
        params.put("showapi_sign", Const.SHOWAPI_SIGN);
        params.put("type", type);
        params.put("page", currentPage + "");
        Observable<Result> observable = new RxVolley.Builder()
                .url(HttpURL.getCommonRequestURL())
                .params(params)
                .httpMethod(RxVolley.Method.POST)
                .contentType(RxVolley.ContentType.FORM)
                .callback(new HttpCallback() {

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        String res = LruCacheManager.getStringFromCache(SecurityUtil.getMD5(type));
                        if(res != null){
                            JsonParser jsonParser = new JsonParser("pagebean", SingleDataEntity.class);
                            SingleDataEntity singleDataEntity =  (SingleDataEntity) jsonParser.parser(res);
                            errorPortraitVw.setVisibility(View.GONE);
                            currentPage = singleDataEntity.currentPage + 1;
                            if(!isLoadMore) {
                                contentlist = singleDataEntity.contentlist;
                                initAdapter(contentlist);
                                mSwipeRefreshLayout.setRefreshing(false);

                            }else {
                                isLoadMore = false;
                                mQuickAdapter.addData(singleDataEntity.contentlist);
                                mQuickAdapter.loadMoreComplete();
                            }
                        }
                    }

                    @Override
                    public void onSuccess(String t) {
                        LruCacheManager.addStringToCache(SecurityUtil.getMD5(type), t);
                    }
                })
                .getResult();

        subscription = observable
                .filter(new Func1<Result, Boolean>() {
                    @Override
                    public Boolean call(Result result) {
                        return result.data != null;
                    }
                })
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return new String(result.data);
                    }
                })
                .map(new Func1<String, SingleDataEntity>() {
                    @Override
                    public SingleDataEntity call(String s) {
                        LogTools.i("test-call", s);
                        JsonParser jsonParser = new JsonParser("pagebean", SingleDataEntity.class);
                        return (SingleDataEntity) jsonParser.parser(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SingleDataEntity>() {
                    @Override
                    public void call(SingleDataEntity result) {
                        errorPortraitVw.setVisibility(View.GONE);
                        currentPage = result.currentPage + 1;
                        if(!isLoadMore) {
                            contentlist = result.contentlist;
                            initAdapter(contentlist);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }else {
                            isLoadMore = false;
                            mQuickAdapter.addData(result.contentlist);
//                            mQuickAdapter.getData().addAll(result.contentlist);
                            mQuickAdapter.loadMoreComplete();
                        }
                    }
                });
//                .subscribe(new Observer<SingleDataEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        LogTools.i("test","onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogTools.i("test","onError");
//                    }
//
//                    @Override
//                    public void onNext(SingleDataEntity singleDataEntity) {
//                        LogTools.i("test", "======网络请求" + singleDataEntity.allNum);
//                    }
//                });
    }

    private void initAdapter(ArrayList<ContentEntity> contentlist){
        mQuickAdapter = new DataBindingAdapter(R.layout.adapter_recycle_item_data_binding, contentlist,this);
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
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
                        String[] imageUrls = new String[1];
                        imageUrls[0] = contentlist.get(position).image3;
                        KJGalleryActivity.toGallery(MvvmRecycleViewActivity.this, imageUrls);
                        break;
                }
            }
        });
//        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Toast.makeText(MainRecycleViewActivity.this, "" + position, Toast.LENGTH_LONG).show();
//            }
//        });
        mQuickAdapter.setOnLoadMoreListener(MvvmRecycleViewActivity.this);
        mRecyclerView.setAdapter(mQuickAdapter);
    }
    @Override
    protected void findView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        mTvLabel.setText("MVC-RecycleView");
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
    public void onRefresh() {
        currentPage = 1;
        rxVolleyPost(Const.SHOWAPI_TYPE_IMAGE);
    }

    @Override
    public void onLoadMoreRequested() {
        isLoadMore = true;
        rxVolleyPost(Const.SHOWAPI_TYPE_IMAGE);
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
