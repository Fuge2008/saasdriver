package com.small.saasdriver.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.utils.SharedPreferencesUtil;

public class OrderStatisticsActivity extends BaseActivity {

	private LinearLayout ll_today_statistics;
	private LinearLayout ll_total_statistics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_statistics);
		initView();
		setLiseter();
	}

	private void setLiseter() {
		iv_back.setOnClickListener(this);
		ll_today_statistics.setOnClickListener(this);
		ll_total_statistics.setOnClickListener(this);
	}

	private void initView() {
		ll_today_statistics = (LinearLayout) findViewById(R.id.ll_today_statistics);
		ll_total_statistics = (LinearLayout) findViewById(R.id.ll_total_statistics);
		iv_back = (ImageView) findViewById(R.id.iv_back);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.ll_today_statistics:
			Intent intent_today = new Intent(OrderStatisticsActivity.this, OrderTodayStatisticsDetail.class);
			startActivity(intent_today);
			break;
		case R.id.ll_total_statistics:
			Intent intent_total = new Intent(OrderStatisticsActivity.this, OrderTotalStatisticsDetail.class);
			startActivity(intent_total);
			break;
		case R.id.img_back_arrow:
			finish();
			break;
		case R.id.iv_back:
			finish();
			break;
		default:
			break;
		}

	}
}
