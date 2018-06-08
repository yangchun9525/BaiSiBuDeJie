package com.yc.baisibudejie.net;

import android.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.yc.baisibudejie.utils.LogTools;
import com.yc.baisibudejie.utils.ValidatesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 重写StringRequest
 */
public class MyJsonRequest extends JsonObjectRequest {
    public static final HashMap<String, String> mHeaders;
    public static HashMap<String, String> mParams;

    static {
        mHeaders = new HashMap<>();
        mHeaders.put("x-client-identifier", "android");
    }

//    @Override
//    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//        String str = null;
//        try {
//            str = new String(response.data, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return Response.success(str,
//                HttpHeaderParser.parseCacheHeaders(response));
//    }


//    @Override
//    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//        String str = null;
////        try {
////            str = new String(response.data, "utf-8");
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
//        return Response.success(str,
//                HttpHeaderParser.parseCacheHeaders(response));
//    }

    /**
     * 更新http head
     *
     * @param key
     * @param value
     */
    public static void updateHeaders(String key, String value) {
        mHeaders.put(key, value);
    }

    private MyJsonRequest(String url, JSONObject request, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, request, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    /**
     * 发送post方式的string request
     *
     * @param requestCode     请求代码
     * @param url             请求路径
     * @param params          请求参数
     * @param requestListener 请求回调
     * @param fragmentManager
     * @return
     */
    public static MyJsonRequest createPostStringRequest(String requestCode, String url,
                                                        HashMap<String, String> params, IRequestListener requestListener, FragmentManager fragmentManager) {
//        if (!ValidatesUtil.isEmpty(params)) {
//            mParams = params;
//        }
        return createStringRequest(Method.POST, requestCode, url, requestListener, fragmentManager);
    }

    public static MyJsonRequest createGetStringRequest(String requestCode, String url,
                                                       HashMap<String, String> params, IRequestListener requestListener) {
        return createGetStringRequest(requestCode, url, params, requestListener, null);
    }

    /**
     * 发送get方式的string request
     *
     * @param requestCode     请求代码
     * @param url             请求路径
     * @param params          请求参数
     * @param requestListener 请求回调
     * @param fragmentManager
     * @return
     */
    public static MyJsonRequest createGetStringRequest(String requestCode, String url,
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
     *
     * @param method          请求方式post/get
     * @param requestCode     请求代码
     * @param url             请求路径
     * @param requestListener 请求回调
     * @return
     */
    private static MyJsonRequest createStringRequest(int method, final String requestCode,
                                                     String url, final IRequestListener requestListener, final FragmentManager fragmentManager) {

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject result) {
                // 由于请求成功后，需要做json串解析，所以不在此处hide dialog
                LogTools.i("yc-requestCode = " + requestCode + " : " + result);
                requestListener.onRequestSuccess(requestCode, result.toString());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogTools.i("yc-volleyError = " + volleyError.networkResponse + "," + volleyError);
                // 隐藏progress dialog
                requestListener.onRequestError(requestCode, volleyError);
            }
        };

        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("UserName", "Z001002003");
//            jsonObject.put("Password", "1q2w3e4r");
            jsonObject.put("name", "name");
            jsonObject.put("age", "123");
//            jsonObject.put("Mac", mac);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 创建request
        MyJsonRequest myRequest = new MyJsonRequest(url, jsonObject, successListener, errorListener);
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(180 * 1000, 10, 1.0f);
        myRequest.setRetryPolicy(retryPolicy);
        return myRequest;
    }
}