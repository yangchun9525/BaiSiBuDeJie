package com.yc.BaiSiBuDeJie.module.recycleview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;
import com.yc.BaiSiBuDeJie.GlobalApp;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseActivity;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.constant.HttpURL;
import com.yc.BaiSiBuDeJie.module.error.ErrorPortraitView;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.recycleview.adapter.RecycleViewAdapter;
import com.yc.BaiSiBuDeJie.net.parser.JsonParser;
import com.yc.BaiSiBuDeJie.utils.LogTools;

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
public class MainRecycleViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,SwipeBackActivityBase {
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mQuickAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ErrorPortraitView errorPortraitVw;
    private Subscription subscription;
    private int currentPage = 1;
    private boolean isLoadMore = false;
    private ArrayList<ContentEntity> contentlist = new ArrayList<>();
    private SwipeBackActivityHelper swipeBackActivityHelper;

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
                            mQuickAdapter.setData(result.contentlist);
//                            mQuickAdapter.getData().addAll(result.contentlist);
                            mQuickAdapter.isNextLoad(true);
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
        mQuickAdapter = new RecycleViewAdapter(MainRecycleViewActivity.this, R.layout.item_main_recycle, contentlist);
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainRecycleViewActivity.this, "" + position, Toast.LENGTH_LONG).show();
            }
        });
        mQuickAdapter.setOnLoadMoreListener(Const.PAGESIZE, MainRecycleViewActivity.this);
        mRecyclerView.setAdapter(mQuickAdapter);
    }
    @Override
    protected void findView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}
