package com.yc.baisibudejie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yc.baisibudejie.constant.Const;
import com.yc.baisibudejie.constant.HttpURL;
import com.yc.baisibudejie.manager.RequestManager;
import com.yc.baisibudejie.net.IRequestListener;
import com.yc.baisibudejie.utils.LogTools;

import java.util.HashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 14340 on 2017/9/21.
 */

public class TestActivity extends AppCompatActivity implements IRequestListener {
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", "lisi");
                params.put("age", "598");
                RequestManager.getInstance().doNetWork("test", HttpURL.getCommonRequestURL(), params,3000, TestActivity.this, null);
//                RequestManager.getInstance().deliverCommonRequest("test", params, TestActivity.this, null);
//                Observable.OnSubscribe observable = new Observable.OnSubscribe<String>() {
//                    @Override
//                    public void call(Subscriber<? super String> subscriber) {
//                        subscriber.onNext("das");
//                        subscriber.onCompleted();
//                    }
//                };
//                Observer subscriber = new Observer<String>() {
//                    @Override
//                    public void onNext(String text) {
//                        Toast.makeText(TestActivity.this, "onSuccess" + text, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(TestActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                test(observable, subscriber);
            }
        });
    }

    public void test(Observable.OnSubscribe observable, Observer subscriber) {
        /*
        new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("das");
                subscriber.onCompleted();
            }
        }
         */
        Observable.create(observable).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    @Override
    public void onRequestSuccess(String requestCode, String result) {
        LogTools.i("yc-resulr", result);
    }

    @Override
    public void onRequestError(String requestCode, VolleyError volleyError) {

    }
}
