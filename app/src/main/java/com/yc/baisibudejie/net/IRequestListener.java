package com.yc.baisibudejie.net;

import com.android.volley.VolleyError;

/**
 * volley请求回调接口
 */
public interface IRequestListener {

    /**
     * 请求成功处理事件
     * @param requestCode    请求参数，标识请求类
     * @param result          服务器返回字符串结果
     */
    public void onRequestSuccess(String requestCode, String result);

    /**
     * 请求失败处理事件
     * @param requestCode    请求参数，标识请求类
     * @param volleyError    对请求失败错误的封装
     */
    public void onRequestError(String requestCode, VolleyError volleyError);

}
