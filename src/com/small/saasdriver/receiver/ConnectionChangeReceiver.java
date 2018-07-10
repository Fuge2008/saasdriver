package com.small.saasdriver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.small.saasdriver.utils.ToastUtil;

public class ConnectionChangeReceiver extends BroadcastReceiver {
	
		@Override
		public void onReceive(final Context context, Intent intent) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifiNetInfo= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (!mobNetInfo.isConnected()&&!wifiNetInfo.isConnected()) {
				
				ToastUtil.showShort(context, "网络不给力,请检查网络连接");
				
			}
		}
	}