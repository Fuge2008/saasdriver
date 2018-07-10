package com.small.saasdriver.activity.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.saascardriver.R;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.fragment.FragmentHistoryRecord;
import com.small.saasdriver.fragment.FragmentTodayRecord;

public class OrderRecordActivity extends BaseActivity implements OnCheckedChangeListener {

	private final String TAG = "OrderRecordActivity";
	private RadioButton rb_today, rb_history;
	private RadioGroup rg_record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_history);
		initView();// 界面初始化
	}

	private void initView() {// 界面初始化
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		rb_today = (RadioButton) findViewById(R.id.rb_today);
		rb_history = (RadioButton) findViewById(R.id.rb_history);
		rg_record = (RadioGroup) findViewById(R.id.rg_record);
//		rb_today.setOnClickListener(this);
//		rb_history.setOnClickListener(this);
		rg_record.setOnCheckedChangeListener(this);
		rb_history.setChecked(true);

	}

	private void changeFragment(Fragment targetFragment) {
		getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		if (rb_history.getId() == checkedId) {
			changeFragment(new FragmentHistoryRecord());
		} else if (rb_today.getId() == checkedId) {
			changeFragment(new FragmentTodayRecord());
		}
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}
	}

}
