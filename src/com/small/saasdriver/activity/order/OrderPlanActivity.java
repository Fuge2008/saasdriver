package com.small.saasdriver.activity.order;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.saascardriver.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.adapter.OrderPlanAdapter;
import com.small.saasdriver.entity.order.OrderPlanEntity;
import com.small.saasdriver.entity.order.OrderPlanEntity.Data;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.CacheUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.view.DeleteListViewMainActivity;
import com.small.saasdriver.view.XListView;
import com.small.saasdriver.view.DeleteListViewMainActivity.MessageItem;
import com.small.saasdriver.view.SlideView.OnSlideListener;
import com.small.saasdriver.view.XListView.OnLoadListener;
import com.small.saasdriver.view.XListView.OnRefreshListener;

/**
 * 接收订单列表（含已确认接收、未确认）
 */
public class OrderPlanActivity extends BaseActivity implements OnRefreshListener, OnLoadListener {

	private final String TAG = "OrderPlanActivity";
	private List<Data> plan_data;

	private OrderPlanAdapter plan_adapter;

	private XListView lv_plan;
	private ScrollView scrollview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_plan);
		initView();// 界面初始化

	}

	private void initView() {// 界面初始化
		lv_plan = (XListView) findViewById(R.id.lv_plan);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		// lv_plan.setOnItemClickListener(this);
		initData();
		// 设置适配器
		plan_adapter = new OrderPlanAdapter(OrderPlanActivity.this, R.layout.item_travel_plan, plan_data);
		lv_plan.setAdapter(plan_adapter);
		lv_plan.setOnRefreshListener(this);
		lv_plan.setOnLoadListener(this);
		loadData(XListView.REFRESH);
	}

	private void initData() {// 缓存数据
		String resJson;
		try {
			resJson = CacheUtil.getFileCache(OrderPlanActivity.this, HttpConstant.ORDER_NOW_OR_PLAN);
			if (resJson != null) {
				processData(resJson);
			}
			sendDataToNet();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendDataToNet() {// 封装订单数据包
		RequestParams params = new RequestParams();
		params.addBodyParameter("DriverID", SharePreferenceUtils.getString(OrderPlanActivity.this, "DriverID", ""));
		params.addBodyParameter("Identify", SharePreferenceUtils.getString(OrderPlanActivity.this, "Identify", ""));
		params.addBodyParameter("NowOrFuture", "2");
		getDataFromNet(HttpConstant.ORDER_NOW_OR_PLAN, params);
	}

	private void getDataFromNet(String url, final RequestParams params) {// 请求网络
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

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
					CacheUtil.setFileCache(OrderPlanActivity.this, HttpConstant.ORDER_NOW_OR_PLAN, result);
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
		OrderPlanEntity reData = gson.fromJson(result, OrderPlanEntity.class);
		plan_data = reData.Data;
		Log.i("info", "数据解析成功：" + plan_data);
		showData(plan_data);

	}

	private void showData(final List<Data> plan_data) {// 显示数据

		lv_plan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Data data = plan_data.get(position);
				Bundle bundle = new Bundle();

				bundle.putInt("order_id", data.OrderID);
				bundle.putString("car_user", data.CustomerName); // 用司机替代
				bundle.putString("user_phone", data.CustomerPhoneNum);// 用司机号码
				bundle.putInt("use_car_model", data.UserType);
				bundle.putInt("order_model", data.OrderMode);
				bundle.putString("start_address", data.Origin);
				bundle.putString("end_address", data.Destination);
				bundle.getDouble("start_lat", data.OriginLatitude);
				bundle.getDouble("start_lng", data.OriginLongitude);
				bundle.getDouble("end_lat", data.DestinationLatitude);
				bundle.getDouble("end_lng", data.DestinationLongitude);
				bundle.putInt("passager_num", data.PersonNum);// 乘车人数
				bundle.putString("user_car_time", data.UseVehicleDate);
				bundle.putString("other_describe", data.OtherCommand);
				bundle.putString("company_name", data.CompanyName);
				bundle.putString("order_statue", data.OrderState);

				Intent intent = new Intent(OrderPlanActivity.this, OrderPlanDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}

	private void loadData(final int what) {// 加载数据
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					//initData();
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.what = what;
				msg.obj = plan_data;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			List<Data> result = (List<Data>) msg.obj;
			switch (msg.what) {
			case XListView.REFRESH:// 更新消息
				
				lv_plan.onRefreshComplete();
				plan_data.clear();
				plan_data.addAll(result);
				break;
			case XListView.LOAD:// 加载更多
				lv_plan.onLoadComplete();
				plan_data.addAll(result);
				break;
			case 2:
				lv_plan.onLoadComplete();// 加载完成
				Toast.makeText(OrderPlanActivity.this, "亲，已到底啦！", Toast.LENGTH_SHORT).show();
				break;
				
			}
			lv_plan.setResultSize(result.size());
			plan_adapter.notifyDataSetChanged();
		};
	};

	@Override
	public void onLoad() {// 加载数据
		if (plan_adapter.getCount() < 5) {
			loadData(XListView.LOAD);
		} else {
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = plan_data;
			handler.sendMessage(msg);

		}
	}

	@Override
	public void onRefresh() {// 刷新数据

		loadData(XListView.REFRESH);
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
