package com.small.saasdriver.activity.order;

import com.example.saascardriver.R;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.utils.SharedPreferencesUtil;
import com.small.saasdriver.utils.StringUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderRecordDetailActivity extends BaseActivity {
	protected static final String TAG = OrderRecordDetailActivity.class.getSimpleName();
	private TextView tv_car_user;
	private TextView tv_user_phone;
	private TextView tv_order_id;
	private TextView tv_start_address;
	private TextView tv_end_address;
	private TextView tv_passager_num;
	private TextView tv_user_car_time;
	private TextView tv_end_time;
	private TextView tv_travel_diatance;
	private TextView tv_oil_cost;
	private TextView tv_parking_cost;
	private TextView tv_other_cost;
	private TextView tv_discount;
	private TextView tv_total_cost;
	private Button btn_delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oder_record_detail);

		initViews();
		initData();
		setListener();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		Bundle bun = getIntent().getExtras();
		String car_user = bun.getString("car_user");
		String user_phone = bun.getString("user_phone");
		int order_id = bun.getInt("order_id");
		String start_address = bun.getString("start_address");
		String end_address = bun.getString("end_address");
		int passager_num = bun.getInt("passager_num");
		String user_car_time = bun.getString("user_car_time");
		String end_time = bun.getString("end_time");
		String travel_diatance = bun.getString("travel_diatance");
		String oil_cost = bun.getString("oil_cost");
		String parking_cost = bun.getString("parking_cost");
		String other_cost = bun.getString("other_cost");
		String discount = bun.getString("discount");
		String total_cost = bun.getString("total_cost");
		if (StringUtils.isEmpty(car_user)) {

			tv_car_user.setText("未知");
		} else {
			tv_car_user.setText(car_user.toString());

		}
		if (StringUtils.isEmpty(user_phone)) {
			tv_user_phone.setText("未知");
		} else {
			tv_user_phone.setText(user_phone.toString());

		}
		if (StringUtils.isEmpty(String.valueOf(order_id))) {
			tv_order_id.setText("未知");
		} else {
			tv_order_id.setText("" + order_id);

		}
		if (StringUtils.isEmpty(start_address)) {
			tv_start_address.setText("未知");
		} else {
			tv_start_address.setText(start_address.toString());

		}
		if (StringUtils.isEmpty(end_address)) {
			tv_end_address.setText("未知");
		} else {
			tv_end_address.setText(end_address.toString());

		}
		if (StringUtils.isEmpty(String.valueOf(passager_num))) {
			tv_passager_num.setText("未知");
		} else {
			tv_passager_num.setText(passager_num + " 人");

		}
		if (StringUtils.isEmpty(user_car_time)) {
			tv_user_car_time.setText("未知");
		} else {
			tv_user_car_time.setText(user_car_time.toString());

		}
		if (StringUtils.isEmpty(end_time)) {
			tv_end_time.setText("未知");
		} else {
			tv_end_time.setText(end_time.toString());

		}
		if (StringUtils.isEmpty(travel_diatance)) {
			tv_travel_diatance.setText("未知");
		} else {
			tv_travel_diatance.setText(travel_diatance.toString());

		}
		if (StringUtils.isEmpty(oil_cost)) {
			tv_oil_cost.setText("未知");
		} else {
			tv_oil_cost.setText(oil_cost.toString());

		}
		if (StringUtils.isEmpty(parking_cost)) {
			tv_parking_cost.setText("未知");
		} else {
			tv_parking_cost.setText(parking_cost.toString());

		}
		if (StringUtils.isEmpty(other_cost)) {
			tv_other_cost.setText("未知");
		} else {
			tv_other_cost.setText(other_cost.toString());

		}
		if (StringUtils.isEmpty(discount)) {
			tv_discount.setText("未知");
		} else {
			tv_discount.setText(discount.toString());

		}
		if (StringUtils.isEmpty(total_cost)) {
			tv_total_cost.setText("未知");
		} else {
			tv_total_cost.setText(total_cost.toString());
		}

	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		tv_car_user = (TextView) findViewById(R.id.tv_car_user);
		tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
		tv_order_id = (TextView) findViewById(R.id.tv_order_id);
		tv_start_address = (TextView) findViewById(R.id.tv_start_address);
		tv_end_address = (TextView) findViewById(R.id.tv_end_address);
		tv_passager_num = (TextView) findViewById(R.id.tv_passager_num);
		tv_user_car_time = (TextView) findViewById(R.id.tv_start_time);
		tv_end_time = (TextView) findViewById(R.id.tv_end_time);
		tv_travel_diatance = (TextView) findViewById(R.id.tv_travel_diatance);
		tv_oil_cost = (TextView) findViewById(R.id.tv_oil_cost);
		tv_parking_cost = (TextView) findViewById(R.id.tv_parking_cost);
		tv_other_cost = (TextView) findViewById(R.id.tv_other_cost);
		tv_discount = (TextView) findViewById(R.id.tv_discount);
		tv_total_cost = (TextView) findViewById(R.id.tv_total_cost);
		btn_delete = (Button) findViewById(R.id.btn_delete);
	}

	private void setListener() {
		btn_delete.setOnClickListener(this);

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
