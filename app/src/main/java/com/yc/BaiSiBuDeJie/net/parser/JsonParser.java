package com.yc.baisibudejie.net.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.yc.baisibudejie.module.listview.entity.MessageEntity;
import com.yc.baisibudejie.utils.JsonUtil;
import com.yc.baisibudejie.utils.LogTools;
import com.yc.baisibudejie.utils.ValidatesUtil;

import java.lang.reflect.Type;

/**
 * 默认处理error解析，要重写parserRight方法
 */
public class JsonParser {

    public static final String RESULT_DESC_LABEL = "showapi_res_error";
    public static final String RESULT_LABEL = "showapi_res_body";

    protected Gson mGson;
    private String detcet;
    private Class classOfT;
    private Type typeOfT;

    public JsonParser() {
    }

    public JsonParser(String detcet, Class classOfT) {
        mGson = new GsonBuilder().serializeNulls().create();
        this.detcet = detcet;
        this.classOfT = classOfT;
    }

    public JsonParser(String detcet, Type typeOfT) {
        mGson = new GsonBuilder().serializeNulls().create();
        this.detcet = detcet;
        this.typeOfT = typeOfT;
    }

    /**
     * 解析json字符串，如果有error信息，则解析error
     *
     * @param jsonStr
     * @return
     */
    public Object parser(String jsonStr) {
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        // 如果业务逻辑失败，则返回messageEntity
        String descStr = JsonUtil.detectElement(jsonStr, RESULT_DESC_LABEL);
        LogTools.i("yc-test",descStr);
        String error = "{\"showapi_res_error\":\"1111111111\"}";
//        MessageEntity messageEntity = new MessageEntity();
//        if (!ValidatesUtil.isEmpty(descStr)) {
//            ShowApiEntity showApiEntity = parserObject(descStr, ShowApiEntity.class);
//            messageEntity.showapi_res_error = showApiEntity.showapi_res_error;
////            LogTools.i("yc-code", messageEntity.showapi_res_code+"." + messageEntity);
////            if (messageEntity.showapi_res_code != Const.RESPONSE_SUCCESS) {
////                return descStr;
////            }
//        }
        // 如果业务逻辑成功
        // 首先解析result对应的字符串
        String resultStr = JsonUtil.detectElement(jsonStr, RESULT_LABEL);
        // 如果result对应的字符串不为空
        if (!ValidatesUtil.isEmpty(resultStr)) {
            // 如果业务detcet不为空，解析业务detcet对应的字符串
            if (!ValidatesUtil.isEmpty(detcet)) {
                resultStr = JsonUtil.detectElement(resultStr, detcet);
            }
            // 解析的业务数据
            if (classOfT != null) {
                return parserObject(resultStr, classOfT);
            } else {
                return parserObject(resultStr, typeOfT);
            }
        } else {
//            return JsonUtil.detectElement(error, RESULT_DESC_LABEL);
            return parserObject(error, MessageEntity.class);
        }
    }

    public <T> T parserObject(String jsonStr, Class<T> classOfT) throws JsonSyntaxException {
        return mGson.fromJson(jsonStr, classOfT);
    }

    public <T> T parserObject(String jsonStr, Type typeOfT) throws JsonSyntaxException {
        return mGson.fromJson(jsonStr, typeOfT);
    }
}
