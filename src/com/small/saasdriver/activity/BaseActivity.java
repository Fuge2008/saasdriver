package com.small.saasdriver.activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.small.saasdriver.application.MyApplication;
import com.small.saasdriver.receiver.ConnectionChangeReceiver;
import com.small.saasdriver.utils.AbAppManager;
import com.small.saasdriver.view.SystemBarTintManager;

/**
 * 界面基类
 */
public abstract class BaseActivity extends FragmentActivity implements android.view.View.OnClickListener {

	protected ImageView iv_back;// 返回图片
	private ConnectionChangeReceiver mReceiver;// 网络连接状态监听广播

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.title_color);// 通知栏所需颜色
		}
		//AbAppManager.getAbAppManager().addActivity(this);
		registerReceiver();
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);// 注销网络广播监听

		 MyApplication.getInstance().delActivityList(this);
		//AbAppManager.getAbAppManager().finishActivity(this);// 删除Application的界面集合
	}

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		mReceiver = new ConnectionChangeReceiver();
		this.registerReceiver(mReceiver, filter);
	}

	@Override
	public final void onClick(View v) {
		doClick(v);

	}

	public abstract void doClick(View v);

}
