package com.small.saasdriver.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saascardriver.R;

public class ClassRoomAcitvity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_classroom);
		initView();
		setLiseter();
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
	}

	private void setLiseter() {
		iv_back.setOnClickListener(this);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}
	}

}
