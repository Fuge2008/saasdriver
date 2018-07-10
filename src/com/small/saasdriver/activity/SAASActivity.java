package com.small.saasdriver.activity;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.small.saasdriver.utils.ImmersionUtils;
import com.small.saasdriver.utils.TxtUtils;

public class SAASActivity extends BaseActivity {
	private TextView tv_saas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_saas);
		new ImmersionUtils(this).setTranslucentStatus(R.color.title_color);
		tv_saas = (TextView) findViewById(R.id.saas);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		InputStream inputStream = getResources().openRawResource(R.raw.saas);
		String string = TxtUtils.getString(inputStream);
		tv_saas.setText(string);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}

	}

}
