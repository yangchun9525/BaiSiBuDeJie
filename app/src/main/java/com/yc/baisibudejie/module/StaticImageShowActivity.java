package com.yc.baisibudejie.module;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.DowningListener;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.toolbox.Loger;
import com.shizhefei.view.largeimage.LargeImageView;
import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseActivity;
import com.yc.baisibudejie.base.BaseTextView;
import com.yc.baisibudejie.manager.ImageLoadManager;

import java.io.File;

/**
 * Created by Administrator on 2017/7/12.
 */
public class StaticImageShowActivity extends BaseActivity implements View.OnClickListener {
    private String mImageUrl;
    private LargeImageView mLivImage;
    private BaseTextView mTvDownload;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_image_show);
        mImageUrl = getIntent().getStringExtra("url");
        init();
    }

    @Override
    protected void findView() {
        mLivImage = (LargeImageView) findViewById(R.id.livImage);
        mTvDownload = (BaseTextView) findViewById(R.id.tvDownload);
    }

    @Override
    protected void setViewSize() {
    }

    @Override
    protected void initValue() {
        ImageLoadManager.loadImg(this, mImageUrl, mLivImage);
    }

    @Override
    protected void bindEvent() {
        mLivImage.setOnClickListener(this);
        mTvDownload.setOnClickListener(this);
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
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.livImage:
                onBackPressed();
                break;
            case R.id.tvDownload:
                download();
                break;
        }
    }

    public void download() {
        String url = mImageUrl;

        String appPath = "BaiSiBuDeJie";

        File file = Environment.getExternalStoragePublicDirectory(appPath);
        file.mkdirs();
        String name = url.substring(url.lastIndexOf("/") + 1);
        RxVolley.download(file.getAbsolutePath() + "/" + name,
                url, new ProgressListener() {
                    @Override
                    public void onProgress(long transferredBytes, long totalSize) {
                        Loger.debug(transferredBytes + "======" + totalSize);
                    }
                }, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        Toast.makeText(StaticImageShowActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                        Loger.debug("====success" + t);
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        Toast.makeText(StaticImageShowActivity.this, "下载失败" + strMsg, Toast.LENGTH_SHORT).show();
                        Loger.debug(errorNo + "====failure" + strMsg);
                    }
                }, new DowningListener() {
                    @Override
                    public void onDowning() {
                        Toast.makeText(StaticImageShowActivity.this, "下载中", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
