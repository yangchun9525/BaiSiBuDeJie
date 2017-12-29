package com.yc.baisibudejie.module.mvvm.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.baisibudejie.BR;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseTextView;
import com.yc.baisibudejie.manager.ImageLoadManager;
import com.yc.baisibudejie.module.listview.entity.ContentEntity;
import com.yc.baisibudejie.utils.DimensionUtil;
import com.yc.baisibudejie.utils.TextDisplayUtil;
import com.yc.baisibudejie.utils.ValidatesUtil;

import java.util.ArrayList;

/**
 * Created by yangchun on 2016-12-1.
 */

public class DataBindingAdapter extends BaseQuickAdapter<ContentEntity, DataBindingAdapter.ContentEntityViewHolder> {

    //    private MoviePresenter mPresenter;
    private static Context sContext;

    public DataBindingAdapter(int layoutResId, ArrayList<ContentEntity> contentList, Context context) {
        super(layoutResId, contentList);
        sContext = context;
//        mPresenter = new MoviePresenter();
    }

    @Override
    protected void convert(ContentEntityViewHolder contentEntityViewHolder, ContentEntity contentEntity) {
        ViewDataBinding binding = contentEntityViewHolder.getBinding();
        contentEntityViewHolder.addOnClickListener(R.id.image);
        binding.setVariable(BR.contententity, contentEntity);
        binding.setVariable(BR.imageUrl, contentEntity.image0);
        binding.executePendingBindings();
    }

    @Override
    protected ContentEntityViewHolder createBaseViewHolder(View view) {
        return new ContentEntityViewHolder(view);
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImageView3(ImageView view, String url) {
        if (ValidatesUtil.isEmpty(url)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            if (url.contains("gif")) {
                ImageLoadManager.loadGif(sContext, url, view);
            } else {
                ImageLoadManager.loadImage(sContext, url, view);
            }
        }
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();

        BaseTextView tv = (BaseTextView) view.findViewById(R.id.title);
        ImageView iv = (ImageView) view.findViewById(R.id.image);
        DimensionUtil.setSize(iv, 1000, 1000);
        DimensionUtil.setMargin(iv, 30, 0, 30, 30);
        DimensionUtil.setMargin(tv, 0, 30, 0, 0);
        tv.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_50));
        view.setTag(R.id.DataBinding_support, binding);
        return view;
    }

    public class ContentEntityViewHolder extends BaseViewHolder {

        public ContentEntityViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) getConvertView().getTag(R.id.DataBinding_support);
        }
    }
}
