package com.yc.baisibudejie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 14340 on 2017/9/21.
 */

public class TestActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.OnSubscribe observable = new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("das");
                        subscriber.onCompleted();
                    }
                };
                Observer subscriber = new Observer<String>() {
                    @Override
                    public void onNext(String text) {
                        Toast.makeText(TestActivity.this, "onSuccess" + text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(TestActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                };
                test(observable, subscriber);
            }
        });
    }

    public void test(Observable.OnSubscribe observable,Observer subscriber) {
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
}
