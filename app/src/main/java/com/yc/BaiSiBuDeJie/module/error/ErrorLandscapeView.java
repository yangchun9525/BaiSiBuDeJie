package com.yc.baisibudejie.module.error;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yc.baisibudejie.R;
import com.yc.baisibudejie.base.BaseButton;
import com.yc.baisibudejie.base.BaseListView;
import com.yc.baisibudejie.base.BaseTextView;
import com.yc.baisibudejie.utils.DimensionUtil;
import com.yc.baisibudejie.widget.CircularProgress;


/**
 * 横屏错误页面
 */
public class ErrorLandscapeView extends FrameLayout implements View.OnClickListener {

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

    public ErrorLandscapeView(Context context) {
        this(context, null);
    }

    public ErrorLandscapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorLandscapeView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mErrorIv.setImageResource(R.drawable.error_landscape);
        mLineVw = mRootView.findViewById(R.id.commonErrorLine);
        mErrorTv = (BaseTextView) mRootView.findViewById(R.id.commonErrorTv);
        mReasonLv = (BaseListView) mRootView.findViewById(R.id.commonErrorReasonLv);
        mRetryBtn = (BaseButton) mRootView.findViewById(R.id.commonRetryBtn);
    }

    private void setViewSize() {
        DimensionUtil.setSizeVertical(mLoadingProgress, 150, 150);

        DimensionUtil.setMarginChange(mNoDataIv, 0, 200, 0, 0);

        DimensionUtil.setMarginChange(mErrorIv, 0, 46, 0, 0);
        DimensionUtil.setMarginChange(mLineVw, 480, 30, 480, 30);
        DimensionUtil.setSizeChange(mErrorTv, 150, 0);
        DimensionUtil.setMarginChange(mErrorTv, 570, 0, 0, 0);
        DimensionUtil.setMarginChange(mReasonLv, 38, 0, 0, 0);

        DimensionUtil.setSizeChange(mRetryBtn, 318, 110);
        DimensionUtil.setMarginChange(mRetryBtn, 0, 48, 0, 0);
    }

    private void initValue() {
        reasons = getResources().getStringArray(R.array.common_error_reason);

        mReasonAdapter = new ErrorReasonAdapter(mContext, reasons, false);
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
        mLoadingRl.requestLayout();
        //刷新视图，相当于调用View.onDraw()方法
        mLoadingRl.invalidate();
    }

    public void isError() {
        mRetryBtn.setClickable(true);
        mErrorRl.bringToFront();
        //请求重新布局,重新调用：onMeasure，onLayout，onDraw
        mLoadingRl.requestLayout();
        //刷新视图，相当于调用View.onDraw()方法
        mLoadingRl.invalidate();
    }

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
