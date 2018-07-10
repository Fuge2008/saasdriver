package com.small.saasdriver.activity.order;

import com.example.saascardriver.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.StringUtils;
import com.small.saasdriver.utils.ToastUtil;
import com.small.saasdriver.view.dialog.SweetAlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 未来计划详情
 */
public class OrderPlanDetailActivity extends BaseActivity {
	private TextView tv_passager_name;
	private TextView tv_passager_phone;
	private TextView tv_user_model;
	private TextView tv_order_model;
	private TextView tv_passager_number;
	private TextView tv_start_address;
	private TextView tv_end_address;
	private TextView tv_passager_company;
	private TextView tv_user_car_time;
	private TextView tv_other_describe;
	private TextView tv_order_id;
	private Button btn_begin;
	private Bundle bun;
	private int order_id;

	private String order_statue;
	private int s = -1;
	private boolean statue_flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_plan_detail);
		initView();
		initData();
		SetListener();
	}

	private void initView() {// 初始化控件
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		tv_passager_name = (TextView) findViewById(R.id.tv_passager_name);
		tv_passager_phone = (TextView) findViewById(R.id.tv_passager_phone);
		tv_user_model = (TextView) findViewById(R.id.tv_user_model);
		tv_order_model = (TextView) findViewById(R.id.tv_order_model);
		tv_passager_number = (TextView) findViewById(R.id.tv_passager_number);
		tv_start_address = (TextView) findViewById(R.id.tv_start_address);
		tv_end_address = (TextView) findViewById(R.id.tv_end_address);
		tv_passager_company = (TextView) findViewById(R.id.tv_passager_company);
		tv_user_car_time = (TextView) findViewById(R.id.tv_user_car_time);
		tv_other_describe = (TextView) findViewById(R.id.tv_other_describe);
		tv_order_id = (TextView) findViewById(R.id.tv_order_id);

		btn_begin = (Button) findViewById(R.id.btn_begin);
	}

	private void initData() {// 初始化数据

		bun = getIntent().getExtras();
		String passager_name = bun.getString("car_user");
		String passager_phone = bun.getString("user_phone");
		int use_car_model = bun.getInt("use_car_model");
		order_id = bun.getInt("order_id");// 订单id
		int order_model = bun.getInt("order_model");
		String start_address = bun.getString("start_address");
		String end_address = bun.getString("end_address");
		double start_lat = bun.getDouble("start_lat");
		double start_lng = bun.getDouble("start_lng");
		double end_lat = bun.getDouble("end_lat");
		double end_lng = bun.getDouble("end_lng");
		int passager_num = bun.getInt("passager_num");
		String user_car_time = bun.getString("user_car_time");
		order_statue = bun.getString("order_statue");
		// order_statue = "2";
		Log.i("info", "订单状态：" + order_statue);
		String other_describe = bun.getString("other_describe");
		String company_name = bun.getString("company_name");
		tv_order_id.setText(String.valueOf(order_id));
		if (StringUtils.equals(order_statue, "待接单")) {
			btn_begin.setText("确认接单");
		} else if (StringUtils.equals(order_statue, "已接单")) {
			btn_begin.setText("开启订单");
		} else {
			btn_begin.setBackgroundColor(R.color.dimgrey);
		}

		if (StringUtils.isEmpty(String.valueOf(use_car_model))) {

			tv_user_model.setText("未知");
		} else {
			tv_user_model.setText(use_car_model + "");
		}
		if (StringUtils.isEmpty(String.valueOf(order_model))) {
			tv_order_model.setText("未知");// 格式
		} else {
			tv_order_model.setText(order_model + "");// 格式
		}
		if (StringUtils.isEmpty(passager_name)) {
			tv_passager_name.setText("未知");

		} else {
			tv_passager_name.setText(passager_name.toString());
		}
		if (StringUtils.isEmpty(passager_phone)) {
			tv_passager_phone.setText("未知");

		} else {
			tv_passager_phone.setText(passager_phone.toString());

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
			tv_passager_number.setText("未知");

		} else {
			tv_passager_number.setText(String.valueOf(passager_num) + " 人");

		}
		if (StringUtils.isEmpty(user_car_time)) {
			tv_user_car_time.setText("未知");

		} else {
			tv_user_car_time.setText(user_car_time.toString());

		}
		if (StringUtils.isEmpty(other_describe)) {
			tv_other_describe.setText("未知");

		} else {
			tv_other_describe.setText(other_describe.toString());

		}
		if (StringUtils.isEmpty(company_name)) {
			tv_passager_company.setText("未知");
		} else {

			tv_passager_company.setText(company_name.toString());
		}

	}

	private void SetListener() {
		btn_begin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {// 确认订单逻辑
				if (statue_flag && StringUtils.equals(order_statue, "待接单")) {
					RequestParams params1 = new RequestParams();
					params1.addBodyParameter("DriverID",
							SharePreferenceUtils.getString(OrderPlanDetailActivity.this, "DriverID", ""));
					params1.addBodyParameter("Identify",
							SharePreferenceUtils.getString(OrderPlanDetailActivity.this, "Identify", ""));
					params1.addBodyParameter("OrderID", order_id + "");
					confirmOderToServer(HttpConstant.ORDER_START_CONFIRM, params1);
				} else if (statue_flag && StringUtils.equals(order_statue, "已接单")) {
					RequestParams params2 = new RequestParams();
					params2.addBodyParameter("DriverID",
							SharePreferenceUtils.getString(OrderPlanDetailActivity.this, "DriverID", ""));
					params2.addBodyParameter("Identify",
							SharePreferenceUtils.getString(OrderPlanDetailActivity.this, "Identify", ""));
					params2.addBodyParameter("StartOrEnd", "1");
					params2.addBodyParameter("OrderID", order_id + "");
					params2.addBodyParameter("Location", "A8音乐大厦");
					params2.addBodyParameter("LocationLongitude", "22.530166");
					params2.addBodyParameter("LocationLatitude", "113.946698");
					params2.addBodyParameter("CurrentMileage", "22.58");
					confirmOderToServer(HttpConstant.ORDER_START_OR_END, params2);

				} else {
					btn_begin.setBackgroundColor(R.color.dimgrey);
					ToastUtil.showShort(OrderPlanDetailActivity.this, "抱歉！暂时无法操作。");
				}

			}
		});

	}

	protected void confirmOderToServer(final String url, RequestParams params) {// 开启订单
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
				statue_flag = false;
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载请求
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
				String result = responseInfo.result;
				Log.i("info", "网络数据加载成功：" + result);
				String progressShow;
				String successShow;
				statue_flag = true;
				if (StringUtils.equals(url, HttpConstant.ORDER_START_CONFIRM)) {
					btn_begin.setBackgroundColor(R.color.dimgrey);
					progressShow = "确认中...";
					successShow = "已成功接单!";
					showSuccessProgress(progressShow, successShow, HttpConstant.ORDER_START_CONFIRM);

				} else if (StringUtils.equals(url, HttpConstant.ORDER_START_OR_END)) {
					btn_begin.setBackgroundColor(R.color.dimgrey);
					progressShow = "开启中...";
					successShow = "已成功开启!";
					showSuccessProgress(progressShow, successShow, HttpConstant.ORDER_START_OR_END);

				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {// 请求失败
				Log.i("info", "请求数据失败原因：" + msg);
				error.printStackTrace();
			}
		});

	}

	private void showSuccessProgress(String string1, final String string2, final String url) {// 展示进度条对话框

		final SweetAlertDialog pDialog = new SweetAlertDialog(OrderPlanDetailActivity.this,
				SweetAlertDialog.PROGRESS_TYPE).setTitleText(string1);
		pDialog.show();
		pDialog.setCancelable(false);
		new CountDownTimer(800 * 7, 800) {
			public void onTick(long millisUntilFinished) {

				s++;
				switch (s) {
				case 0:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
					break;
				case 1:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
					break;
				case 2:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
					break;
				case 3:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
					break;
				case 4:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
					break;
				case 5:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
					break;
				case 6:
					pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
					break;
				}
			}

			public void onFinish() {
				s = -1;
				pDialog.setTitleText(string2).setConfirmText("确定").changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				if (StringUtils.equals(url, HttpConstant.ORDER_START_OR_END)) {
					Intent intent = new Intent(OrderPlanDetailActivity.this, OrderNowActivity.class);
					startActivity(intent);
					finish();
				} else if (StringUtils.equals(url, HttpConstant.ORDER_START_CONFIRM)) {
					Intent intent = new Intent(OrderPlanDetailActivity.this, OrderPlanActivity.class);
					startActivity(intent);
					finish();
				}
			}

		}.start();

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
