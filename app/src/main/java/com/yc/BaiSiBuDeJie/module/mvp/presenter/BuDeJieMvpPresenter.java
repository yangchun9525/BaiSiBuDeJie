package com.yc.BaiSiBuDeJie.module.mvp.presenter;

import android.graphics.Bitmap;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;
import com.yc.BaiSiBuDeJie.cache.LruCacheManager;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.constant.HttpURL;
import com.yc.BaiSiBuDeJie.module.listview.entity.SingleDataEntity;
import com.yc.BaiSiBuDeJie.module.mvp.model.MvpModel;
import com.yc.BaiSiBuDeJie.net.parser.JsonParser;
import com.yc.BaiSiBuDeJie.utils.LogTools;
import com.yc.BaiSiBuDeJie.utils.SecurityUtil;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yangchun on 2016-9-18.
 */
public class BuDeJieMvpPresenter {
    private Subscription subscription;
    private MvpModel mvpModel;
    public BuDeJieMvpPresenter(MvpModel mvpModel){
        this.mvpModel = mvpModel;
    }
    public void sendRequest(String type,int currentPage){
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
                        String result = LruCacheManager.getStringFromCache(SecurityUtil.getMD5(type));
                        if(result != null){
                            JsonParser jsonParser = new JsonParser("pagebean", SingleDataEntity.class);
                            SingleDataEntity singleDataEntity =  (SingleDataEntity) jsonParser.parser(result);
                            mvpModel.onCompleted(singleDataEntity);
                        }
                    }

                    @Override
                    public void onSuccess(String t) {
                        LruCacheManager.addStringToCache(SecurityUtil.getMD5(type), t);
                    }
                })
                .getResult()
                ;
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
                .subscribe(new Subscriber<SingleDataEntity>(){
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpModel.onError(e);
                    }

                    @Override
                    public void onNext(SingleDataEntity singleDataEntity) {
                        mvpModel.onCompleted(singleDataEntity);
                    }
                });
    }
}
