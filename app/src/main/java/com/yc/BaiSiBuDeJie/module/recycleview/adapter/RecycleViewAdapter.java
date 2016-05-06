package com.yc.BaiSiBuDeJie.module.recycleview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kymjs.gallery.KJGalleryActivity;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.utils.TextDisplayUtil;

import java.util.ArrayList;

/**
 * Created by YangChun on 2016/4/19.
 */
public class RecycleViewAdapter extends BaseQuickAdapter<ContentEntity> {
    private Context mContext;
    public RecycleViewAdapter(Context context,int layout_id,ArrayList<ContentEntity> contentList) {
        super(context, layout_id, contentList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentEntity item) {
        ImageView iv = (ImageView) helper.getView().findViewById(R.id.image);
        iv.setVisibility(View.VISIBLE);
        BaseTextView tv = (BaseTextView) helper.getView().findViewById(R.id.title);
        helper.setOnClickListener(R.id.image, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] imageUrls = new String[1];
                imageUrls[0] = item.image0;
                KJGalleryActivity.toGallery(mContext, imageUrls);
            }
        });
        DimensionUtil.setPadding(helper.getView(), 30, 30, 30, 30);
        DimensionUtil.setSize(iv, 1000, 1000);
        DimensionUtil.setMargin(iv, 30, 0, 30, 30);
        DimensionUtil.setMargin(tv, 0, 30, 0, 0);
        tv.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_50));
        helper.setText(R.id.title, item.text)
                .setImageUrl(R.id.image, item.image0);

    }
}

