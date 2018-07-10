package com.small.saasdriver.activity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.small.saasdriver.adapter.MessageAdapter;
import com.small.saasdriver.application.MyApplication;

public class MessageCenterActivity extends BaseActivity {
	public static final String Message_Center = MessageCenterActivity.class.getSimpleName();
	private static final String DTAT_LIST = "datalists";

	// public static List<Map<String, Object>> data = new ArrayList<Map<String,
	// Object>>();//在这里new和在onCreate new方法区别。。。。。
	private List<Map<String, Object>> data;
	private MessageAdapter messageCenterAdapter;
	private SharedPreferences sharedPreferences;
	// 消息中心图标

	private ListView mRecyclerView;
	// private ItemTouchHelper itemTouchHelper;

	private TextView tv_empty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_message);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		tv_empty = (TextView) findViewById(R.id.empty);
		iv_back.setOnClickListener(this);

		sharedPreferences = this.getSharedPreferences("test", Context.MODE_PRIVATE);
		mRecyclerView = (ListView) findViewById(R.id.recycleview);

		data = MyApplication.getDataList(DTAT_LIST);

		if (data != null) {
			Collections.sort(data, new sortClass());

			messageCenterAdapter = new MessageAdapter(MessageCenterActivity.this, data);
			messageCenterAdapter.notifyDataSetChanged();
			mRecyclerView.setAdapter(messageCenterAdapter);

		} else {

			tv_empty.setVisibility(View.VISIBLE);

		}

		// SaasDriverApplication.datalits_notify_message=
		// SaasDriverApplication.getInstance().getDatalits_notify_message();
		//
		// if(SaasDriverApplication.datalits_notify_message!=null){
		// Collections.sort(SaasDriverApplication.datalits_notify_message,new
		// sortClass());
		// messageCenterAdapter =new
		// MessageAdapter(MessageCenterActivity.this,SaasDriverApplication.datalits_notify_message);
		// messageCenterAdapter.notifyDataSetChanged();
		// mRecyclerView.setAdapter(messageCenterAdapter);
		// }

		IntentFilter filter = new IntentFilter();
		filter.addAction(MessageCenterActivity.Message_Center);
		registerReceiver(broadcastReceiver, filter);

	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null)
				return;

			if (MessageCenterActivity.Message_Center.equals(intent.getAction())) {
				messageCenterAdapter.notifyDataSetChanged();
			}
		}
	};

	private class sortClass implements Comparator {
		public int compare(Object arg0, Object arg1) {
			// 比较两个Mpa对象之间的时间
			String user0 = (String) MyApplication.getDataList(DTAT_LIST).get(0).get("date");
			String user1 = (String) MyApplication.getDataList(DTAT_LIST).get(1).get("date");
			int flag = user0.compareTo(user1);
			Log.i("flag", flag + "");
			return flag;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}

	}

}
