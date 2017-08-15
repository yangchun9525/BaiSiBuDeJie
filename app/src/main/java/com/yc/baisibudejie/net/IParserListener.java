package com.yc.baisibudejie.net;

import com.yc.baisibudejie.module.listview.entity.MessageEntity;

/**
 * 解析字符串请求回调接口
 */
public interface IParserListener {


    /**
     * 返回成功结果处理事件
     * @param requestCode    请求参数，标识请求类
     * @param result          返回正确结果，解析后返回的解析结果
     */
    public void onParserSuccess(String requestCode, Object result);

    /**
     * 返回失败结果处理事件
     * @param requestCode    请求参数，标识请求类
     * @param messageEntity  服务端返回信息
     */
    public void onParserError(String requestCode, MessageEntity messageEntity);
}
