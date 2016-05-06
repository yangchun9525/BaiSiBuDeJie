package com.yc.BaiSiBuDeJie.module.listview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kymjs.gallery.KJGalleryActivity;
import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.manager.ImageLoadManager;
import com.yc.BaiSiBuDeJie.module.listview.entity.ContentEntity;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.utils.TextDisplayUtil;
import com.yc.BaiSiBuDeJie.utils.ValidatesUtil;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by YangChun on 2016/4/15.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentEntity> datas;

    public ListViewAdapter(Context mContext, ArrayList<ContentEntity> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public void setData(ArrayList<ContentEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_listview, viewGroup, false);
            holder = getHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (!ValidatesUtil.isNull(datas.get(i))) {
            if(datas.get(i).type.equals("10")) {
                holder.ivImageView.setVisibility(View.VISIBLE);
                holder.jcVideoPlayer.setVisibility(View.GONE);
                if(datas.get(i).image3.contains("gif")) {
                    ImageLoadManager.loadGif(mContext, datas.get(i).image3, holder.ivImageView);
                }else {
                    ImageLoadManager.loadImage(mContext, datas.get(i).image3, holder.ivImageView);
                }
            }else if(datas.get(i).type.equals("29")){
                holder.ivImageView.setVisibility(View.GONE);
                holder.jcVideoPlayer.setVisibility(View.GONE);
            }else if(datas.get(i).type.equals("41")){
                holder.ivImageView.setVisibility(View.GONE);
                holder.jcVideoPlayer.setVisibility(View.VISIBLE);
                holder.jcVideoPlayer.setUp(datas.get(i).video_uri, "");
            }else {
                holder.ivImageView.setVisibility(View.VISIBLE);
                holder.ivImageView.setImageResource(R.drawable.common_loading);
            }
            holder.tvTitle.setText(datas.get(i).text);

            if(datas.get(i).type.equals("10")) {
                holder.ivImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] imageUrls = new String[1];
                        imageUrls[0] = datas.get(i).image3;
                        KJGalleryActivity.toGallery(mContext, imageUrls);
                    }
                });
            }else if(datas.get(i).type.equals("41")){
                holder.ivImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] imageUrls = new String[1];
                        imageUrls[0] = datas.get(i).image3;
                        KJGalleryActivity.toGallery(mContext, imageUrls);
                    }
                });
            }
        }
        return view;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = new ViewHolder();

        holder.tvTitle = (BaseTextView) convertView.findViewById(R.id.title);
        holder.tvContent = (BaseTextView) convertView.findViewById(R.id.content);
        holder.ivImageView = (ImageView) convertView.findViewById(R.id.image);
        holder.jcVideoPlayer = (JCVideoPlayer) convertView.findViewById(R.id.videocontroller1);

        DimensionUtil.setPadding(convertView, 30, 30, 30, 30);
        DimensionUtil.setMargin(holder.tvContent, 0, 30, 0, 0);
        DimensionUtil.setSize(holder.ivImageView, 1000, 1000);

        holder.tvTitle.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_50));
        holder.tvContent.setTextSize(TextDisplayUtil.fixSpValue(R.dimen.text_size_common_txt_48));
        return holder;
    }

    private static class ViewHolder {
        public BaseTextView tvTitle;
        public BaseTextView tvContent;
        public ImageView ivImageView;
        public JCVideoPlayer jcVideoPlayer;
    }
}
