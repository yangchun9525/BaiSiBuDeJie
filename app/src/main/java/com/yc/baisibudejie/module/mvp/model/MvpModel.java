package com.yc.baisibudejie.module.mvp.model;

import com.yc.baisibudejie.module.listview.entity.SingleDataEntity;

/**
 * Created by yangchun on 2016-9-19.
 */
public interface MvpModel {
    void onCompleted(SingleDataEntity singleDataEntity);
    void onError(Throwable e);
}
