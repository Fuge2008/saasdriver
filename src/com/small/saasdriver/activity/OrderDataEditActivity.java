package com.small.saasdriver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.saascardriver.R;

/**
 * 订单数据上传数据修改界面
 */
public class OrderDataEditActivity extends BaseActivity {
	private EditText et_load_bill;
	private EditText et_parking_bill;
	private EditText et_other_bill;
	private EditText et_end_position;
	private EditText et_other_describe;
	private Button btn_submit;
	private String loadBill;
	private String parkingBill;
	private String otherBill;
	private String endPosition;
	private String otherDescribe;
	public static final String LOAD_BILL = "LOAD_BILL_CODE";
	public static final String PARKING_BILL = "PARKING_BILL_CODE";
	public static final String OTHER_BILL = "OTHER_BILL_CODE";
	public static final String END_POSITION = "END_POSITION_CODE";
	public static final String OTHER_DESCRIBE = "OTHER_DESCRIBE_CODE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_data_edit);
		initView();

		setListener();

	}

	private void initView() {
		et_load_bill = (EditText) findViewById(R.id.et_load_bill);
		et_parking_bill = (EditText) findViewById(R.id.et_parking_bill);
		et_other_bill = (EditText) findViewById(R.id.et_other_bill);
		et_end_position = (EditText) findViewById(R.id.et_end_position);
		et_other_describe = (EditText) findViewById(R.id.et_other_describe);
		btn_submit = (Button) findViewById(R.id.btn_submit);

	}

	private void initData() {
		loadBill = et_load_bill.getText().toString();
		parkingBill = et_parking_bill.getText().toString();
		otherBill = et_other_bill.getText().toString();
		endPosition = et_end_position.getText().toString();
		otherDescribe = et_other_describe.getText().toString();
		Log.i("info", "发送信息：" + loadBill);

	}

	private void setListener() {
		btn_submit.setOnClickListener(this);

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			initData();
			Intent intent = new Intent(OrderDataEditActivity.this, OrderDataCommitActivity.class);
			intent.putExtra(LOAD_BILL, loadBill);
			intent.putExtra(PARKING_BILL, parkingBill);
			intent.putExtra(OTHER_BILL, otherBill);
			intent.putExtra(END_POSITION, endPosition);
			intent.putExtra(OTHER_DESCRIBE, otherDescribe);
			setResult(RESULT_OK, intent);
			finish();// 结束之后会将结果传回
			break;

		}
	}

}
