package com.yc.baisibudejie.module.recycleview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseTextView;
import com.yc.baisibudejie.manager.ImageLoadManager;
import com.yc.baisibudejie.module.listview.entity.ContentEntity;
import com.yc.baisibudejie.utils.DimensionUtil;
import com.yc.baisibudejie.utils.TextDisplayUtil;

import java.util.ArrayList;

/**
 * Created by YangChun on 2016/4/19.
 */
public class RecycleViewAdapter extends BaseQuickAdapter<ContentEntity, BaseViewHolder> {
    public RecycleViewAdapter(Context context,int layout_id,ArrayList<ContentEntity> contentList) {
        super(layout_id, contentList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentEntity item) {
        ImageView iv = helper.getView(R.id.image);
        iv.setVisibility(View.VISIBLE);
        BaseTextView tv = helper.getView(R.id.title);
        DimensionUtil.setSize(iv, 1000, 1000);
        DimensionUtil.setMargin(iv, 30, 0, 30, 30);
        DimensionUtil.setMargin(tv, 0, 30, 0, 0);
        tv.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_50));
        helper.setText(R.id.title, item.text)
                .addOnClickListener(R.id.image);
        if(item.image3.contains("gif")){
            ImageLoadManager.loadGif(mContext,item.image3,helper.getView(R.id.image));
        }else {
            ImageLoadManager.loadImage(mContext,item.image3,helper.getView(R.id.image));
        }
    }
}

