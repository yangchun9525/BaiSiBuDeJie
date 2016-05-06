package com.yc.BaiSiBuDeJie.module.error;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;

/**
 * 错误原因adapter
 */
public class ErrorReasonAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mReasons;
    private boolean isPortrait;

    public ErrorReasonAdapter(Context context, String[] reasons, boolean isPortrait) {
        this.mContext = context;
        this.mReasons = reasons;
        this.isPortrait = isPortrait;
    }

    @Override
    public int getCount() {
        return mReasons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_item_request_common_reason, parent, false);
            holder = getHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mReasonTv.setText(mReasons[position]);
        return convertView;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.mFlagVw = (BaseTextView) convertView.findViewById(R.id.commonErrorItemFlag);
        holder.mReasonTv = (BaseTextView) convertView.findViewById(R.id.commonErrorItemTv);

        if(isPortrait) {
            DimensionUtil.setSize(convertView, 0, 80);
            DimensionUtil.setMargin(holder.mReasonTv, 10, 0, 0, 0);
        } else {
            DimensionUtil.setSizeChange(convertView, 0, 60);
            DimensionUtil.setMarginChange(holder.mReasonTv, 10, 0, 0, 0);
        }
        return holder;
    }

    private static class ViewHolder {
        BaseTextView mFlagVw;
        BaseTextView mReasonTv;
    }
}
