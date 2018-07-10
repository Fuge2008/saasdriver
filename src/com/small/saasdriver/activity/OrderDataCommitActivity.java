package com.small.saasdriver.activity;

import java.util.List;

import com.example.saascardriver.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.small.saasdriver.activity.order.OrderNowActivity;
import com.small.saasdriver.activity.order.OrderPlanActivity;
import com.small.saasdriver.activity.order.OrderPlanDetailActivity;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.StringUtils;
import com.small.saasdriver.view.dialog.SweetAlertDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 订单数据提交界面
 */
public class OrderDataCommitActivity extends BaseActivity {
	private TextView tv_cost;
	private TextView tv_road_bill;
	private TextView tv_parking_bill;
	private TextView tv_other_bill;
	private TextView tv_passager_number;
	private TextView tv_kilometer;
	private TextView tv_start_time;
	private TextView tv_end_time;
	private TextView tv_end_position;

	private String totalBill = "22.00";
	private String roadBill = "22.00";
	private String parkingBill = "22.00";
	private String otherBill = "22.00";
	private String passagerNumber = "22.00";
	private String kilometer = "22.00";
	private String startTime = "22.00";
	private String endTime = "22.00";
	private String endPosition = "22.00";

	private Button btn_edit;
	private Button btn_submit;
	private String loadBillReceiver;
	private String parkingBillReceiver;
	private String otherBillReceiver;
	private String endPositionReceiver;
	private String otherDescribeReceiver;
	public static final int REQUSET = 1;
	private int s = -1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {// 接收返回数据
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {// requestCode标示请求的标示 resultCode表示有数据
		case REQUSET:
			String moneyZero = "0.00";
			if (requestCode == OrderDataCommitActivity.REQUSET && resultCode == RESULT_OK) {
				loadBillReceiver = data.getStringExtra(OrderDataEditActivity.LOAD_BILL);
				parkingBillReceiver = data.getStringExtra(OrderDataEditActivity.PARKING_BILL);
				otherBillReceiver = data.getStringExtra(OrderDataEditActivity.OTHER_BILL);
				endPositionReceiver = data.getStringExtra(OrderDataEditActivity.END_POSITION);
				otherDescribeReceiver = data.getStringExtra(OrderDataEditActivity.OTHER_DESCRIBE);

			}
			if (StringUtils.isNotEquals(loadBillReceiver, moneyZero)) {
				tv_road_bill.setText("¥ " + loadBillReceiver);
			}
			if (StringUtils.isNotEquals(parkingBillReceiver, moneyZero)) {
				tv_parking_bill.setText("¥ " + parkingBillReceiver);
			}
			if (StringUtils.isNotEquals(otherBillReceiver, moneyZero)) {
				tv_other_bill.setText("¥ " + otherBillReceiver);
			}
			if (StringUtils.isNotEquals(endPosition, endPositionReceiver)) {
				tv_end_position.setText(endPositionReceiver);
			}

			break;

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_data_commit);
		initView();
		initData();
		setListener();

	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_cost = (TextView) findViewById(R.id.tv_cost);
		tv_road_bill = (TextView) findViewById(R.id.tv_road_bill);
		tv_parking_bill = (TextView) findViewById(R.id.tv_parking_bill);
		tv_other_bill = (TextView) findViewById(R.id.tv_other_bill);
		tv_passager_number = (TextView) findViewById(R.id.tv_passager_number);
		tv_kilometer = (TextView) findViewById(R.id.tv_kilometer);
		tv_start_time = (TextView) findViewById(R.id.tv_start_time);
		tv_end_time = (TextView) findViewById(R.id.tv_end_time);
		tv_end_position = (TextView) findViewById(R.id.tv_end_position);
		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_submit = (Button) findViewById(R.id.btn_submit);

	}

	private void initData() {
		tv_cost.setText(totalBill);
		tv_road_bill.setText("¥" + roadBill);
		tv_parking_bill.setText("¥" + parkingBill);
		tv_other_bill.setText("¥" + otherBill);
		tv_passager_number.setText(passagerNumber + "人");
		tv_kilometer.setText(kilometer);
		tv_start_time.setText(startTime);
		tv_end_time.setText(endTime);
		tv_end_position.setText(endPosition);

	}

	private void setListener() {
		btn_edit.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		iv_back.setOnClickListener(this);

	}

	private void sendDataToServer() {// 提交订单数据到服务器
		RequestParams params = new RequestParams();
		params.addBodyParameter("Identify",
				SharePreferenceUtils.getString(OrderDataCommitActivity.this, "Identify", ""));
		params.addBodyParameter("OrderID",
				SharePreferenceUtils.getString(OrderDataCommitActivity.this, "Identify", ""));
		params.addBodyParameter("OilCharge", "");
		params.addBodyParameter("TollCharge", "");
		params.addBodyParameter("OtherCharge", "");
		params.addBodyParameter("ParkingCharge", "");
		params.addBodyParameter("OtherChargeDescription", "");
		params.addBodyParameter("OilMass", "");
		params.addBodyParameter("Oil", "");
		params.addBodyParameter("ParkingCategory", "");
		params.addBodyParameter("TollCategory", "");
		params.addBodyParameter("WaitingTime", "");
		params.addBodyParameter("TicketCount", "");
		params.addBodyParameter("StoppingPlace", "");
		params.addBodyParameter("WayPlace", "");
		params.addBodyParameter("Overtime", "");
		params.addBodyParameter("StartOrEnd", "2");

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, HttpConstant.UPLOAD_ORDER_DATA, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载中
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
				String result = responseInfo.result;
				// Intent intent = new Intent(OrderDataCommitActivity.this,
				// OrderDataCommitActivity.class);
				// startActivity(intent);
				showSuccessProgress();
			}

			@Override
			public void onFailure(HttpException error, String msg) {// 请求失败
				Toast.makeText(OrderDataCommitActivity.this, msg, Toast.LENGTH_SHORT).show();
				showFailDialog();
				error.printStackTrace();
			}
		});

	}

	private void showFailDialog() {
		new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("提交失败").setContentText("服务器正忙!").show();

	}

	// 展示进度条对话框
	private void showSuccessProgress() {

		final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
				.setTitleText("提交中...");
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
				pDialog.setTitleText("提交数据成功！").setConfirmText("确定").changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
			}
		}.start();

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit:
			Intent intent = new Intent(OrderDataCommitActivity.this, OrderDataEditActivity.class);
			startActivityForResult(intent, REQUSET);
			break;
		case R.id.btn_submit:
			sendDataToServer();
			break;
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}

	}

}
