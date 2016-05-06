package com.yc.BaiSiBuDeJie.manager;

import android.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.yc.BaiSiBuDeJie.constant.Const;
import com.yc.BaiSiBuDeJie.constant.HttpURL;
import com.yc.BaiSiBuDeJie.net.IRequestListener;
import com.yc.BaiSiBuDeJie.net.MyRequest;
import com.yc.BaiSiBuDeJie.net.MyVolley;
import com.yc.BaiSiBuDeJie.utils.LogTools;

import java.util.HashMap;

/**
 * Created by ZhuBibo on 2015/5/29.
 * 应用中所有request的管理类
 */
public class RequestManager {

    public static int TIMEOUT_SHORT = 10 * 1000;
    public static int TIMEOUT_MIDDLE = 50 * 1000;
    public static int TIMEOUT_LONG = 180 * 1000;

    private static RequestManager mRequestManager;
    private RequestQueue mRequestQueue;
    public static String REQUEST_TAG = "requestTag";

    private RequestManager() {
        mRequestQueue = MyVolley.getRequestQueue();
    }

    public static RequestManager getInstance() {
        if (mRequestManager == null) {
            mRequestManager = new RequestManager();
        }
        return mRequestManager;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    ///////////////////////////////////////////////////////////////////////
    //
    //  common 通用请求处理
    //
    ///////////////////////////////////////////////////////////////////////
    /**
     * 通用请求处理
     * @param requestCode         请求代码
     * @param requestURL          请求url
     * @param params              请求参数
     * @param timeoutMs           超时时长
     * @param requestListener     请求回调
     * @param fragmentManager
     */
    public void deliverCommonRequest(String requestCode, String requestURL,
                                     HashMap<String, String> params, int timeoutMs,
                                     IRequestListener requestListener, FragmentManager fragmentManager) {

        StringRequest request = MyRequest.createPostStringRequest(requestCode, requestURL, params, requestListener, fragmentManager);

        //设置不要缓存请求(解决中断正在执行的网络请求)
        request.setShouldCache(false);
        REQUEST_TAG = requestCode;
        request.setTag(REQUEST_TAG);
        Const.isCancelRequest = true;
        LogTools.i(REQUEST_TAG);

        // Volley中没有指定的方法来设置请求超时时间，可以设置RetryPolicy 来变通实现。
        // 10s超时
        // 重试次数1
        // 1.0f为超时因子，控制下次重试时的超时时间
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutMs, 1, 1.0f);
        request.setRetryPolicy(retryPolicy);
        mRequestQueue.add(request);
    }

    /**
     * 通用请求处理
     * @param requestCode         请求代码
     * @param requestURL          请求url
     * @param params              请求参数
     * @param requestListener     请求回调
     * @param fragmentManager
     */
    public void deliverCommonRequest(String requestCode, String requestURL,
                                     HashMap<String, String> params,
                                     IRequestListener requestListener, FragmentManager fragmentManager) {
        deliverCommonRequest(requestCode, requestURL, params, TIMEOUT_SHORT, requestListener, fragmentManager);
    }

    /**
     * 通用请求处理
     * @param requestCode         请求代码
     * @param params              请求参数
     * @param timeoutMs           超时时长
     * @param requestListener     请求回调
     * @param fragmentManager
     */
    public void deliverCommonRequest(String requestCode,
                                     HashMap<String, String> params, int timeoutMs,
                                     IRequestListener requestListener, FragmentManager fragmentManager) {
        deliverCommonRequest(requestCode, HttpURL.getCommonRequestURL(), params, timeoutMs, requestListener, fragmentManager);
    }

    /**
     * 通用请求处理
     * @param requestCode         请求代码
     * @param params              请求参数
     * @param requestListener     请求回调
     * @param fragmentManager
     */
    public void deliverCommonRequest(String requestCode,
                                     HashMap<String, String> params,
                                     IRequestListener requestListener, FragmentManager fragmentManager) {
        deliverCommonRequest(requestCode, HttpURL.getCommonRequestURL(), params, requestListener, fragmentManager);
    }

    /**
     * 测试get 请求
     * @param requestCode        请求代码
     * @param params              请求参数
     * @param requestListener   请求回调
     * @return
     */
    public void deliverGetRequest(String requestCode, String url, HashMap<String, String> params, IRequestListener requestListener, FragmentManager fragmentManager) {
        StringRequest request = MyRequest.createGetStringRequest(requestCode, url, params, requestListener, fragmentManager);
        mRequestQueue.add(request);
    }
}
