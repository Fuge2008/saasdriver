package com.small.saasdriver.fragment;

import java.util.List;

import com.example.saascardriver.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.small.saasdriver.activity.order.OrderRecordDetailActivity;
import com.small.saasdriver.adapter.OrderRecordAdapter;
import com.small.saasdriver.entity.order.OrderRecordEntity;
import com.small.saasdriver.entity.order.OrderRecordEntity.Data;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.CacheUtil;
import com.small.saasdriver.utils.LogUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentHistoryRecord extends BaseFragment {
	private List<Data> record_data;
	private ListView lv_record;
	private OrderRecordAdapter record_adapter;

	@Override
	protected View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_histroy_record, null);
		lv_record = (ListView) view.findViewById(R.id.lv_history_record);
		String resJson;
		try {
			resJson = CacheUtil.getFileCache(mActivity, HttpConstant.ORDER_DATA_RECORD);
			if (resJson != null) {
				processData(resJson);
			}
			sendDataToNet();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	/**
	 * 封装订单数据包
	 */
	private void sendDataToNet() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("DriverID", SharePreferenceUtils.getString(mActivity, "DriverID", ""));
		params.addBodyParameter("Identify", SharePreferenceUtils.getString(mActivity, "Identify", ""));
		params.addBodyParameter("DayOrAll", "2");
		params.addBodyParameter("PageIndex", "1");
		params.addBodyParameter("PageSize", "2");
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, HttpConstant.ORDER_DATA_RECORD, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载请求

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
				String result = responseInfo.result;
				try {
					CacheUtil.setFileCache(mActivity, HttpConstant.ORDER_DATA_RECORD, result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) { // 请求失败
				LogUtil.i("info", "请求数据失败原因：" + msg);
				error.printStackTrace();
			}
		});

	}

	protected void processData(String result) {// 解析数据
		Gson gson = new Gson();
		OrderRecordEntity reData = gson.fromJson(result, OrderRecordEntity.class);
		record_data = reData.Data;
		showData(record_data);

	}

	private void showData(final List<Data> record_data) {// 显示数据

		record_adapter = new OrderRecordAdapter(mActivity, R.layout.item_travel_records, record_data);
		lv_record.setAdapter(record_adapter);// 设置适配器

		lv_record.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Data data = record_data.get(position);
				Bundle bundle = new Bundle();
				bundle.putString("car_user", data.CustomerName);
				bundle.putString("user_phone", data.CustomerPhoneNum);
				bundle.putInt("order_id", data.OrderID);
				bundle.putString("start_address", data.Origin);
				bundle.putString("end_address", data.Destination);
				bundle.putInt("passager_num", data.PersonNum);
				bundle.putString("user_car_time", data.StartTime);
				bundle.putDouble("travel_diatance", data.Kilometres);
				bundle.putString("end_time", data.EndTime);
				bundle.putString("oil_cost", "¥ " + data.OilCharge);
				bundle.putString("parking_cost", "¥ " + data.ParkingCharge);
				bundle.putString("other_cost", "¥ " + data.OtherCharge);
				bundle.putString("discount", data.ActualDiscount + "折");
				bundle.putString("total_cost", "¥ " + data.ActualCost);

				Intent intent = new Intent(mActivity, OrderRecordDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}

	public void doClick(View v) {

	}

}
