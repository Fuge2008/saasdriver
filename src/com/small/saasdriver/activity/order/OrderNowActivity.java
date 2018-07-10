/**
 * 
 */
package com.small.saasdriver.activity.order;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.util.verify.BNKeyVerifyListener;
import com.example.saascardriver.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.activity.MainActivity;
import com.small.saasdriver.adapter.OrderNowAdapter;
import com.small.saasdriver.adapter.OrderNowAdapter;
import com.small.saasdriver.application.MyApplication;
import com.small.saasdriver.entity.LoginData;
import com.small.saasdriver.entity.order.OrderNowEntity;
import com.small.saasdriver.entity.order.OrderNowEntity;
import com.small.saasdriver.entity.order.OrderNowEntity.Data;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.CacheUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.SharedPreferencesUtil;

/**
 * 正在进行的订单
 */
public class OrderNowActivity extends BaseActivity {

	private final String TAG = "OrderNowActivity";
	private List<Data> now_data;

	private ListView lv_now;
	private OrderNowAdapter now_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_underway);
		initView();// 界面初始化
		initData();// 先访问缓存，再加载数据
	}

	private void initView() {// 界面初始化

		lv_now = (ListView) findViewById(R.id.lv_now);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);

	}

	private void initData() { // 缓存数据
		String resJson;
		try {
			resJson = CacheUtil.getFileCache(OrderNowActivity.this, HttpConstant.ORDER_NOW_OR_PLAN);
			if (resJson != null) {
				processData(resJson);
			}
			sendDataToNet();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendDataToNet() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("DriverID", SharePreferenceUtils.getString(OrderNowActivity.this, "DriverID", ""));
		params.addBodyParameter("Identify", SharePreferenceUtils.getString(OrderNowActivity.this, "Identify", ""));
		params.addBodyParameter("NowOrFuture", "1");
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, HttpConstant.ORDER_NOW_OR_PLAN, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载请求

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
				String result = responseInfo.result;
				Log.i("info", "网络数据加载成功：" + result);
				try {
					CacheUtil.setFileCache(OrderNowActivity.this, HttpConstant.ORDER_NOW_OR_PLAN, result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {// 请求失败
				Log.i(TAG, "请求数据失败原因：" + msg);
				error.printStackTrace();
			}
		});

	}

	protected void processData(String result) {// 解析数据
		Gson gson = new Gson();
		OrderNowEntity reData = gson.fromJson(result, OrderNowEntity.class);
		now_data = reData.Data;
		Log.i("info", "数据解析成功：" + now_data);
		showData(now_data);

	}

	private void showData(final List<Data> now_data) {// 显示数据

		now_adapter = new OrderNowAdapter(OrderNowActivity.this, R.layout.item_travel_plan, now_data);
		lv_now.setAdapter(now_adapter);// 设置适配器

		lv_now.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Data data = now_data.get(position);
				Bundle bundle = new Bundle();
				bundle.putInt("order_id", data.OrderID);
				bundle.putString("car_user", data.CustomerName);
				bundle.putString("user_phone", data.CustomerPhoneNum);
				bundle.putInt("use_car_model", data.UserType);
				bundle.putInt("order_model", data.OrderMode);
				bundle.putString("start_address", data.Origin);
				bundle.putString("end_address", data.Destination);
				bundle.putDouble("start_lat", data.OriginLatitude);
				bundle.putDouble("start_lng", data.OriginLongitude);
				bundle.putDouble("end_lat", data.DestinationLatitude);
				bundle.putDouble("end_lng", data.DestinationLongitude);
				bundle.putInt("passager_num", data.PersonNum);
				bundle.putString("user_car_time", data.UseVehicleDate);
				bundle.putString("order_statue", data.OrderState);
				bundle.putString("other_describe", data.OtherCommand);

				Intent intent = new Intent(OrderNowActivity.this, OrderNowDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
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
