package com.yc.BaiSiBuDeJie.module.mvp.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;

import java.util.List;

/**
 * Created by yangchun on 2016-9-19.
 */
public class MvpRecycleViewAdapter extends BaseQuickAdapter<ContentEntity> {
    private Context mContext;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public MvpRecycleViewAdapter(Context context, int layoutResId, List<ContentEntity> data) {
        super(context, layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentEntity item) {
//        ItemMvpRecycleviewBinding binding = DataBindingUtil.bind(helper.convertView);
//        ImageView iv = (ImageView) helper.getView().findViewById(R.id.image);
//        iv.setVisibility(View.VISIBLE);
//        BaseTextView tv = (BaseTextView) helper.getView().findViewById(R.id.title);
//        helper.setOnClickListener(R.id.image, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String[] imageUrls = new String[1];
//                imageUrls[0] = item.image0;
//                KJGalleryActivity.toGallery(mContext, imageUrls);
//            }
//        });
//        DimensionUtil.setPadding(helper.getView(), 30, 30, 30, 30);
//        DimensionUtil.setSize(iv, 1000, 1000);
//        DimensionUtil.setMargin(iv, 30, 0, 30, 30);
//        DimensionUtil.setMargin(tv, 0, 30, 0, 0);
//        tv.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_50));
//        helper.setText(R.id.title, item.text)
//                .setImageUrl(R.id.image, item.image0);
    }
}
