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

public class FAQActivity extends BaseActivity {
	private TextView tv_faq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
		initView();
		setListener();
		
	}
	private void initView() {
		tv_faq = (TextView) findViewById(R.id.faq_tv);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		new ImmersionUtils(this).setTranslucentStatus(R.color.title_color);
		InputStream inputStream = getResources().openRawResource(R.raw.faq);
		String string = TxtUtils.getString(inputStream);
		tv_faq.setText(string);
	}
	private void setListener() {
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
