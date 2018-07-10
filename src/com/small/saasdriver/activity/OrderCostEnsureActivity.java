package com.small.saasdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.saascardriver.R;

public class OrderCostEnsureActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_cost_ensure);

	}

	public void doClick(View v) {
		Intent intent = new Intent(OrderCostEnsureActivity.this, OrderDataCommitActivity.class);
		startActivity(intent);
		finish();

	}

}
