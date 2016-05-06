package com.yc.BaiSiBuDeJie.module.error;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yc.BaiSiBuDeJie.R;
import com.yc.BaiSiBuDeJie.base.BaseButton;
import com.yc.BaiSiBuDeJie.base.BaseListView;
import com.yc.BaiSiBuDeJie.base.BaseTextView;
import com.yc.BaiSiBuDeJie.utils.DimensionUtil;
import com.yc.BaiSiBuDeJie.widget.CircularProgress;

/**
 * 竖屏错误页面
 */
public class ErrorPortraitView extends FrameLayout implements View.OnClickListener {

    //ui
    private View mRootView;

    private RelativeLayout mLoadingRl, mNodataRl, mErrorRl;
    private CircularProgress mLoadingProgress;
    private ImageView mNoDataIv, mErrorIv;
    private View mLineVw;
    private BaseTextView mErrorTv;
    private BaseButton mRetryBtn;
    private BaseListView mReasonLv;

    //data
    private String[] reasons;

    // other
    private ErrorReasonAdapter mReasonAdapter;
    private Context mContext;

    public ErrorPortraitView(Context context) {
        this(context, null);
    }

    public ErrorPortraitView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorPortraitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        findView();
        setViewSize();
        initValue();
        bindEvent();
    }

    private void findView() {

        mRootView = LayoutInflater.from(mContext).inflate(R.layout.request_common_layout, this);

        mLoadingRl = (RelativeLayout) mRootView.findViewById(R.id.commonLoadingLayout);
        mLoadingProgress = (CircularProgress) mRootView.findViewById(R.id.commonProgress);

        mNodataRl = (RelativeLayout) mRootView.findViewById(R.id.commonNoDataLayout);
        mNoDataIv = (ImageView) mRootView.findViewById(R.id.commonNoDataIv);

        mErrorRl = (RelativeLayout) mRootView.findViewById(R.id.commonErrorLayout);
        mErrorIv = (ImageView) mRootView.findViewById(R.id.commonErrorIv);
        mLineVw = mRootView.findViewById(R.id.commonErrorLine);
        mErrorTv = (BaseTextView) mRootView.findViewById(R.id.commonErrorTv);
        mReasonLv = (BaseListView) mRootView.findViewById(R.id.commonErrorReasonLv);
        mRetryBtn = (BaseButton) mRootView.findViewById(R.id.commonRetryBtn);
    }

    private void setViewSize() {
        DimensionUtil.setSizeHorizontal(mLoadingProgress, 200, 200);

        DimensionUtil.setMargin(mNoDataIv, 0, 400, 0, 0);

        DimensionUtil.setMargin(mErrorIv, 0, 200, 0, 0);
        DimensionUtil.setMargin(mLineVw, 116, 50, 116, 38);
        DimensionUtil.setSize(mErrorTv, 150, 0);
        DimensionUtil.setMargin(mErrorTv, 150, 0, 0, 0);
        DimensionUtil.setMargin(mReasonLv, 38, 0, 0, 0);

        DimensionUtil.setSize(mRetryBtn, 318, 110);
        DimensionUtil.setMargin(mRetryBtn, 0, 96, 0, 0);
    }

    private void initValue() {
        reasons = getResources().getStringArray(R.array.common_error_reason);

        mReasonAdapter = new ErrorReasonAdapter(mContext, reasons, true);
        mReasonLv.setAdapter(mReasonAdapter);
    }

    private void bindEvent() {
        mRetryBtn.setOnClickListener(this);
    }

    public void isLoading() {
        mRetryBtn.setClickable(false);
        mLoadingRl.bringToFront();
        //请求重新布局,重新调用：onMeasure，onLayout，onDraw
        mLoadingRl.requestLayout();
        //刷新视图，相当于调用View.onDraw()方法
        mLoadingRl.invalidate();
    }

    public void isNoData() {
        mRetryBtn.setClickable(false);
        mNodataRl.bringToFront();
        //请求重新布局,重新调用：onMeasure，onLayout，onDraw
        mNodataRl.requestLayout();
        //刷新视图，相当于调用View.onDraw()方法
        mNodataRl.invalidate();
    }

    public void isError() {
        mRetryBtn.setClickable(true);
        mErrorRl.bringToFront();
        //请求重新布局,重新调用：onMeasure，onLayout，onDraw
        mErrorRl.requestLayout();
        //刷新视图，相当于调用View.onDraw()方法
        mErrorRl.invalidate();
    }

//    /**
//     * 设置背景色
//     * @param color
//     */
//    public void setBgColor(int color){
//        mLoadingRl.setBackgroundColor(color);
//        mNodataRl.setBackgroundColor(color);
//    }

    /**
     * 设置点击事件
     * @param listener
     */
    public void setOnClickListener(OnClickListener listener){
        mRetryBtn.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commonRetryBtn:
                isLoading();
        }
    }
}
