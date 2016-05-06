package com.yc.BaiSiBuDeJie.net;

import android.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yc.BaiSiBuDeJie.utils.LogTools;
import com.yc.BaiSiBuDeJie.utils.ValidatesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写StringRequest
 */
public class MyRequest extends StringRequest {
    public static final HashMap<String, String> mHeaders;
    public static HashMap<String, String> mParams;

    static {
        mHeaders = new HashMap<>();
        mHeaders.put("x-client-identifier", "android");
    }

    /**
     * 更新http head
     * @param key
     * @param value
     */
    public static void updateHeaders(String key, String value) {
        mHeaders.put(key, value);
    }

    private MyRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    public static MyRequest createPostStringRequest(String requestCode, String url,
                                                    HashMap<String, String> params, IRequestListener requestListener) {
        if(!ValidatesUtil.isEmpty(params)) {
            mParams = params;
        }
        return createPostStringRequest(requestCode, url, params, requestListener, null);
    }

    /**
     * 发送post方式的string request
     * @param requestCode        请求代码
     * @param url                 请求路径
     * @param params              请求参数
     * @param requestListener   请求回调
     * @param fragmentManager
     * @return
     */
    public static MyRequest createPostStringRequest(String requestCode, String url,
                                                    HashMap<String, String> params, IRequestListener requestListener, FragmentManager fragmentManager) {
        if(!ValidatesUtil.isEmpty(params)) {
            mParams = params;
        }
        return createStringRequest(Method.POST, requestCode, url, requestListener, fragmentManager);
    }

    public static MyRequest createGetStringRequest(String requestCode, String url,
                                                   HashMap<String, String> params, IRequestListener requestListener) {
        return createGetStringRequest(requestCode, url, params, requestListener, null);
    }

    /**
     * 发送get方式的string request
     * @param requestCode        请求代码
     * @param url                 请求路径
     * @param params              请求参数
     * @param requestListener   请求回调
     * @param fragmentManager
     * @return
     */
    public static MyRequest createGetStringRequest(String requestCode, String url,
                                                   HashMap<String, String> params, IRequestListener requestListener, FragmentManager fragmentManager) {
        StringBuffer urlBuff = new StringBuffer(url);
        if (!ValidatesUtil.isEmpty(params)) {
            mParams = params;
            urlBuff.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuff.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url = urlBuff.toString();
            url = url.substring(0, url.length() - 1);
        }
        return createStringRequest(Method.GET, requestCode, url, requestListener, fragmentManager);
    }

    /**
     * 发送string request
     * @param method              请求方式post/get
     * @param requestCode        请求代码
     * @param url                 请求路径
     * @param requestListener   请求回调
     * @return
     */
    private static MyRequest createStringRequest(int method, final String requestCode,
                                                 String url, final IRequestListener requestListener, final FragmentManager fragmentManager) {

        Response.Listener<String> successListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                // 由于请求成功后，需要做json串解析，所以不在此处hide dialog
                LogTools.i("requestCode = " + requestCode + " : " + result);
                requestListener.onRequestSuccess(requestCode, result);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // 隐藏progress dialog
                requestListener.onRequestError(requestCode, volleyError);
            }
        };
        // 创建request
        MyRequest myRequest = new MyRequest(method, url, successListener, errorListener);
        return myRequest;
    }
}