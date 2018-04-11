package com.yc.baisibudejie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yc.baisibudejie.module.listview.MainListViewActivity;

/**
 * Created by chun.yang on 2018/4/11.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainListViewActivity.class);
        //这个必须添加flags
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

