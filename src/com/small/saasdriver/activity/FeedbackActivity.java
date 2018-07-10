package com.small.saasdriver.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saascardriver.R;
import com.small.saasdriver.utils.AnFQNumEditText;

public class FeedbackActivity extends BaseActivity {

	private AnFQNumEditText anetDemo;
	private Button commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		initView();
		setListener();

	}

	private void initView() {
		commit = (Button) findViewById(R.id.comit);
		iv_back = (ImageView) findViewById(R.id.iv_back);

		anetDemo = (AnFQNumEditText) findViewById(R.id.anetDemo);
		anetDemo.setEtHint("内容")// 设置提示文字
				.setEtMinHeight(200)// 设置最小高度，单位px
				.setLength(50)// 设置总字数
				.setType(AnFQNumEditText.PERCENTAGE)// TextView显示类型(SINGULAR单数类型)(PERCENTAGE百分比类型)
				.setLineColor("#3F51B5")// 设置横线颜色
				.show();
	}

	private void setListener() {
		commit.setOnClickListener(this);
		iv_back.setOnClickListener(this);

	}

	public void doClick(View v) {

		switch (v.getId()) {

		case R.id.comit:
			anetDemo.setEntmy();
			Toast.makeText(FeedbackActivity.this, "你已经提交", Toast.LENGTH_SHORT).show();

			break;
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}

	}

}
