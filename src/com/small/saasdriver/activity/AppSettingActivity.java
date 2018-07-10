package com.small.saasdriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saascardriver.R;
import com.small.saasdriver.utils.ImmersionUtils;

public class AppSettingActivity extends BaseActivity {
	private TextView software_Upgrade, password_Modify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_setting);
		initView();
		setListener();

	}

	private void initView() {
		software_Upgrade = (TextView) findViewById(R.id.software_upgrade);
		password_Modify = (TextView) findViewById(R.id.password_modify);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		new ImmersionUtils(this).setTranslucentStatus(R.color.title_color);
	}

	private void setListener() {
		software_Upgrade.setOnClickListener(this);
		password_Modify.setOnClickListener(this);
		iv_back.setOnClickListener(this);

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.software_upgrade:
			Toast.makeText(this, "已是最新版", Toast.LENGTH_SHORT).show();
			break;
		case R.id.password_modify:
			Intent intent = new Intent(AppSettingActivity.this, ModifyPasswardActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_back:
			finish();
			break;

		}

	}

}
