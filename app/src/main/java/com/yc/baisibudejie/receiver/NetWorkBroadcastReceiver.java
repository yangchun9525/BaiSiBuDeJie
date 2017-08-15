package com.yc.baisibudejie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yc.baisibudejie.GlobalApp;
import com.yc.baisibudejie.R;
import com.yc.baisibudejie.constant.Const;
import com.yc.baisibudejie.utils.ToastUtil;

/**
 * 网络状态监听
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {
	private ConnectivityManager mConnectivityManager;
	private NetworkInfo mNetworkInfo;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            try {
                mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if(mNetworkInfo != null && mNetworkInfo.isAvailable()){
                    GlobalApp.isShowNetworkError = true;
                    String typeName = mNetworkInfo.getTypeName().toLowerCase(); // WIFI/MOBILE
                    if (Const.NET_TYPE_WIFI.equalsIgnoreCase(typeName)) {
                        GlobalApp.netType = Const.NET_TYPE_WIFI;
                    } else {

                        typeName = mNetworkInfo.getExtraInfo().toLowerCase();// 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap

                        if(Const.NET_TYPE_3GNET.equals(typeName) || Const.NET_TYPE_3GWAP.equals(typeName)){
                            GlobalApp.netType = Const.NET_TYPE_3G;
                        }else {
                            GlobalApp.netType = Const.NET_TYPE_2G;
                        }
                    }
                }else {
                    if (GlobalApp.isShowNetworkError) {
                        ToastUtil.showShortToast(GlobalApp.getInstance().getString(R.string.error_network_service));
                    }
                    GlobalApp.isShowNetworkError = false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
	}
}
