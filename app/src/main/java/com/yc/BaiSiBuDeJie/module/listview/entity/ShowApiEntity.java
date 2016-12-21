package com.yc.BaiSiBuDeJie.module.listview.entity;

import java.io.Serializable;

/**
 * Created by YangChun on 2016/4/15.
 */
public class ShowApiEntity implements Serializable{
    public int showapi_res_code;
    public String showapi_res_error;
    public BuDeJieEntity showapi_res_body;

    @Override
    public String toString() {
        return "ShowApiEntity{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
