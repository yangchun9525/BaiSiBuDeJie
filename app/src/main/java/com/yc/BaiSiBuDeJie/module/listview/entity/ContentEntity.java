package com.yc.baisibudejie.module.listview.entity;

import java.io.Serializable;

/**
 * Created by YangChun on 2016/4/15.
 */
public class ContentEntity implements Serializable{
    //创建时间
    public String create_time;
    //不喜欢的数量
    public String hate;
    //图片高度
    public String height;
    //id
    public String id;
    //0号图，数字越大，尺寸越大
    public String image0;
    public String image1;
    public String image2;
    public String image3;
    //是否是gif 0表示不是，1表示是
    public String is_gif;
    // 	点赞的数量
    public String love;
    // 	段子正文
    public String text;
    // 	type=10 图片 type=29 段子 type=31 声音 type=41 视频
    public String type;
    // 	视频时长
    public String videotime;
    //视频url
    public String video_uri;
    //声音文件大小
    public String voicelength;
    //声音时长
    public String voicetime;
    // 	声音url
    public String voiceuri;
    // 	微信链接地址
    public String weixin_url;
    //图片宽度
    public String width;
}
