package com.yc.baisibudejie.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;
import com.yc.baisibudejie.R;

import java.io.File;
import java.util.Observable;

public class ImageLoadManager {

    public static void loadImage(Context context, String url, ImageView image) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.common_loading)
                .crossFade()
                .into(image);
    }

    public static void loadImage(Context context, int id, ImageView image) {
        Glide.with(context)
                .load(id)
                .centerCrop()
                .placeholder(R.drawable.common_loading)
                .crossFade()
                .into(image);
    }

    public static void loadGif(Context context, String url, ImageView image) {
        Glide.with(context)
                .load(url)
                .asGif()
                .centerCrop()
                .placeholder(R.drawable.common_loading)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }

    public static void loadImg(Context context, String url, LargeImageView image){
        Glide.with(context).load(url).downloadOnly(new Target<File>() {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                image.setImage(R.drawable.common_loading);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                image.setImage(R.drawable.common_loading);
            }

            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                image.setImage(new FileBitmapDecoderFactory(resource));
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {

            }

            @Override
            public void getSize(SizeReadyCallback cb) {
                cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            }

            @Override
            public void setRequest(Request request) {

            }

            @Override
            public Request getRequest() {
                return null;
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onDestroy() {

            }
        });
    }
}
