package com.yc.BaiSiBuDeJie.module.listview.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseFragment;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.manager.RequestManager;
import com.yc.BaiSiBuDeJie.module.listview.adapter.ListViewAdapter;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.MessageEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.ShowApiEntity;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.net.IParserListener;
import com.yc.BaiSiBuDeJie.net.IRequestListener;
import com.yc.BaiSiBuDeJie.net.ParserFacade;
import com.yc.BaiSiBuDeJie.utils.ValidatesUtil;
import com.yc.BaiSiBuDeJie.widget.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YangChun on 2016/4/18.
 */
public class ItemFragment  extends BaseFragment implements IRequestListener, IParserListener,PullToRefreshListView.OnLoadMoreListener, AdapterView.OnItemClickListener, PullToRefreshListView.OnRefreshListener {
    private ArrayList<ContentEntity> imageContentList;
    private ArrayList<ContentEntity> textContentList;
    private ArrayList<ContentEntity> videoContentList;

    public PullToRefreshListView mListView;
    private ListViewAdapter lvAdapter;
    private Bundle bundle;
    private String type;
    private int count, currentPage = 1;
    private ArrayList<ContentEntity> contentlist = new ArrayList<>();
    private boolean isLoadMore = false;
    public static ItemFragment newInstance(SingleDataEntity entities,String flag,Context context){
        ItemFragment instance = new ItemFragment();
        Bundle args = new Bundle();

        args.putString("type", flag);
        if(flag.equals(Const.SHOWAPI_TYPE_TEXT)){
            args.putSerializable("text_entities", entities);
        }else if(flag.equals(Const.SHOWAPI_TYPE_VIDEO)){
            args.putSerializable("video_entities", entities);
        }else if(flag.equals(Const.SHOWAPI_TYPE_IMAGE)){
            args.putSerializable("image_entities", entities);
        }


        instance.setArguments(args);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item, parent, false);

        bundle = getArguments();
        type = bundle.getString("type");
        if(!ValidatesUtil.isNull(bundle.getSerializable("image_entities"))) {
            imageContentList = ((SingleDataEntity) bundle.getSerializable("image_entities")).contentlist;
        }
        if(!ValidatesUtil.isNull(bundle.getSerializable("text_entities"))) {
            textContentList = ((SingleDataEntity) bundle.getSerializable("text_entities")).contentlist;
        }
        if(!ValidatesUtil.isNull(bundle.getSerializable("video_entities"))) {
            videoContentList = ((SingleDataEntity) bundle.getSerializable("video_entities")).contentlist;
        }
        init();
        if(!ValidatesUtil.isNull(imageContentList)){
            lvAdapter = new ListViewAdapter(getActivity(), imageContentList);
        }else if(!ValidatesUtil.isNull(textContentList)){
            lvAdapter = new ListViewAdapter(getActivity(), textContentList);
        }else if(!ValidatesUtil.isNull(videoContentList)){
            lvAdapter = new ListViewAdapter(getActivity(), videoContentList);
        }
        mListView.setAdapter(lvAdapter);
        return mRootView;
    }

    @Override
    protected void findView() {
        mListView = (PullToRefreshListView) mRootView.findViewById(R.id.lv);
//        contentlist = new ArrayList<>();
//        lvAdapter = new ListViewAdapter(getActivity(), contentlist);
        mListView.setCanLoadMore(true);
        mListView.setCanRefresh(true);
    }

    @Override
    protected void setViewSize() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    protected void bindEvent() {
        mListView.setOnItemClickListener(this);
        mListView.setOnRefreshListener(this);
        mListView.setOnLoadListener(this);
    }

    private void sendRequest(String type,int currentPage) {
        HashMap<String, String> params = new HashMap<>();
        params.put("showapi_appid", Const.SHOWAPI_APPID);
        params.put("showapi_sign", Const.SHOWAPI_SIGN);
        params.put("type", type);
        params.put("page", currentPage + "");
        RequestManager.getInstance().deliverCommonRequest(type, params, this, null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ContentEntity entity = (ContentEntity) lvAdapter.getItem((int)l);
        Uri uri = Uri.parse(entity.weixin_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onRequestSuccess(String requestCode, String result) {
        ParserFacade parser = new ParserFacade(requestCode, this);
        parser.start(result, "pagebean", SingleDataEntity.class);
    }

    @Override
    public void onRequestError(String requestCode, VolleyError volleyError) {

    }

    @Override
    public void onParserSuccess(String requestCode, Object result) {
        SingleDataEntity res = (SingleDataEntity) result;
        count = res.allNum;
        currentPage = res.currentPage + 1;
        if (currentPage == 1) {
            contentlist = res.contentlist;
            lvAdapter.setData(contentlist);
            mListView.onRefreshComplete();
        } else if (!isLoadMore) {
            contentlist = res.contentlist;
            lvAdapter.setData(contentlist);
            mListView.onRefreshComplete();
        } else {
            isLoadMore = false;
            contentlist.addAll(res.contentlist);
            lvAdapter.setData(contentlist);
            mListView.onLoadMoreComplete();
        }
    }

    @Override
    public void onParserError(String requestCode, MessageEntity messageEntity) {

    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        sendRequest(type,currentPage);
    }

    @Override
    public void onRefresh() {
        sendRequest(type, 1);
    }

    public void refreshListViewUI(){
        mListView.refreshUI();
    }
}
