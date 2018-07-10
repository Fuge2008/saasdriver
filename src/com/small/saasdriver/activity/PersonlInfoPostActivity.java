package com.small.saasdriver.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.small.saasdriver.utils.SharedPreferencesUtil;

public class PersonlInfoPostActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_driver_complete_information);
		initView();
		setLiseter();
	}

	private void setLiseter() {
		iv_back.setOnClickListener(this);
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}
	}
}
