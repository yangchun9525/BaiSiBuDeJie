package com.yc.baisibudejie.module.listview.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YangChun on 2016/4/15.
 */
public class SingleDataEntity implements Serializable{
    //所有的num
    public int allNum;
    //所有的page
    public int allPages;
    // 当前page
    public int currentPage;
    //具体结果
    public ArrayList<ContentEntity> contentlist;
}
