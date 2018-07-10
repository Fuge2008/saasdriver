package com.small.saasdriver.activity.order;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.example.saascardriver.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.saas.chat.chat.ProfileActivity;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.activity.OrderDataCommitActivity;
import com.small.saasdriver.activity.map.BNavigatorActivity;
import com.small.saasdriver.activity.map.MapUtil;
import com.small.saasdriver.activity.map.RouteGuideActivity;
import com.small.saasdriver.activity.map.TravelTraceQueryActivity;
import com.small.saasdriver.activity.map.TravelTraceUploadActivity;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.LogUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OrderNowDetailActivity extends BaseActivity implements OnGetRoutePlanResultListener {
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
	private TextView tv_input_tip2;
	private TextView tv_input_tip1;
	private EditText et_input1;
	private EditText et_input2;
	private RadioGroup rg_main;
	private Bundle bun;

	private CheckBox cb_route_plan;
	private CheckBox cb_navigation;
	private CheckBox cb_route_again;
	private CheckBox cb_satellite;
	private Button btn_add_point;
	private Button btn_end_travel;

	protected RoutePlanSearch routePlanSearch;// 路线搜索相关
	private BaiduMap baiduMap;
	private MapView mapView;
	private LinearLayout ll_baidumap;
	private LinearLayout ll_show;
	private LinearLayout ll_hiden;

	private LatLng start = new LatLng(22.529535, 113.947441);
	private LatLng end = new LatLng(22.591334, 114.122088);
	private String startAddress = null;// 起点
	private String endAddress = null;// 终点
	private boolean FLAG = true;// 判断是否第一次路线规划
	private boolean INPUT_FORMAT = false;// 判断输入的是经纬度

	private RouteLine route = null;// 路线
	private MyLocationListenner myListener;
	private LocationClient mLocClient;// 定位相关
	private LocationMode mCurrentMode;
	private BitmapDescriptor mCurrentMarker = null;
	public static final String TIME_CHANGED_ACTION = "OrderNowDetailActivity";
	private TextView tv_service_time;// 服务计时时长
	private TextView tv_service_distance;// 服务里程
	private TextView tv_service_cost;// 计费
	private TextView tv_need_time;
	private TextView tv_total_money;
	private TextView tv_my_location;
	private Context mContext = null;

	private String start_location;
	private double start_lat;
	private double start_lng;
	private String end_loacatin;
	private double end_lat;
	private double end_lng;

	private String passager_name;
	private String passager_phone;
	private int use_car_model;
	private int order_id;// 订单id
	private int order_model;

	private int passager_num;
	private String user_car_time;
	private String order_statue;
	private String other_describe;
	private static final int MA_REQUESTCODE = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LatLng defaultLatLng = new LatLng(22.555133, 114.066218);
		mapView = new MapView(this,
				new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(defaultLatLng).zoom(16).build())
						.scaleControlEnabled(true).zoomControlsEnabled(false));
		setContentView(R.layout.activity_travel_now_detail);
		mContext = this;
		initView();
		initData();
		SetListener();
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(start);
		baiduMap.setMapStatus(mapStatusUpdate);

		mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);
		baiduMap.setMapStatus(mapStatusUpdate);
		routePlanSearch = RoutePlanSearch.newInstance();// 初始化路线查询对象
		routePlanSearch.setOnGetRoutePlanResultListener(this);// 设置路线查询结果监听
		routePlanSearchInit();

	}

	private void initView() {// 初始化控件
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_service_time = (TextView) findViewById(R.id.tv_service_time);
		tv_service_distance = (TextView) findViewById(R.id.tv_service_distance);
		tv_service_cost = (TextView) findViewById(R.id.tv_service_cost);
		tv_need_time = (TextView) findViewById(R.id.tv_need_time);
		// my_location = (TextView) findViewById(R.id.my_location);
		tv_my_location = (TextView) findViewById(R.id.tv_my_location);
		tv_total_money = (TextView) findViewById(R.id.tv_total_money);

		cb_route_plan = (CheckBox) findViewById(R.id.cb_route_plan);
		cb_navigation = (CheckBox) findViewById(R.id.cb_navigation);
		cb_route_again = (CheckBox) findViewById(R.id.cb_route_again);
		cb_satellite = (CheckBox) findViewById(R.id.cb_satellite);
		ll_baidumap = (LinearLayout) findViewById(R.id.ll_baidumap);

		ll_show = (LinearLayout) findViewById(R.id.ll_show);
		ll_hiden = (LinearLayout) findViewById(R.id.ll_hiden);

		btn_add_point = (Button) findViewById(R.id.btn_add_point);
		btn_end_travel = (Button) findViewById(R.id.btn_end_travel);

		// tv_passager_name = (TextView) findViewById(R.id.tv_passager_name);
		// tv_passager_phone = (TextView) findViewById(R.id.tv_passager_phone);
		// tv_user_model = (TextView) findViewById(R.id.tv_user_model);
		// tv_order_model = (TextView) findViewById(R.id.tv_order_model);
		// tv_passager_number = (TextView)
		// findViewById(R.id.tv_passager_number);
		// tv_start_address = (TextView) findViewById(R.id.tv_start_address);
		// tv_end_address = (TextView) findViewById(R.id.tv_end_address);
		// tv_passager_company = (TextView)
		// findViewById(R.id.tv_passager_company);
		// tv_user_car_time = (TextView) findViewById(R.id.tv_user_car_time);
		// tv_other_describe = (TextView) findViewById(R.id.tv_other_describe);

		ll_baidumap.addView(mapView);
		baiduMap = mapView.getMap();
		myListener = new MyLocationListenner();

	}

	private void initData() {// 初始化数据
		bun = getIntent().getExtras();
		passager_name = bun.getString("car_user");
		passager_phone = bun.getString("user_phone");
		use_car_model = bun.getInt("use_car_model");
		order_id = bun.getInt("order_id");// 订单id
		order_model = bun.getInt("order_model");
		start_location = bun.getString("start_address");
		end_loacatin = bun.getString("end_address");
		start_lat = bun.getDouble("start_lat");
		start_lng = bun.getDouble("start_lng");
		end_lat = bun.getDouble("end_lat");
		end_lng = bun.getDouble("end_lng");
		Log.i("info", start_lat + start_lng + end_lat + end_lng + "");
		start = new LatLng(start_lat, start_lng);
		end = new LatLng(end_lat, end_lng);
		passager_num = bun.getInt("passager_num");
		user_car_time = bun.getString("user_car_time");
		order_statue = bun.getString("order_statue");
		other_describe = bun.getString("other_describe");

	}

	private void SetListener() {// 监听事件
		iv_back.setOnClickListener(this);
		cb_route_plan.setOnClickListener(this);
		cb_navigation.setOnClickListener(this);
		cb_route_again.setOnClickListener(this);
		cb_satellite.setOnClickListener(this);
		ll_show.setOnClickListener(this);
		ll_hiden.setOnClickListener(this);
		btn_add_point.setOnClickListener(this);
		btn_end_travel.setOnClickListener(this);

	}

	public void routePlanSearchInit() {
		routePlanSearch.drivingSearch(getSearchParams());

	}

	private DrivingRoutePlanOption getSearchParams() {// 乘车路线规划
		DrivingRoutePlanOption params = new DrivingRoutePlanOption();
		// List<PlanNode> nodes = new ArrayList<PlanNode>();
		// nodes.add(PlanNode.withCityNameAndPlaceName("深圳市", "布吉联检站"));//添加点
		// params.passBy(nodes); // 设置途经点
		params.from(PlanNode.withLocation(start)); // 设置起点
		params.to(PlanNode.withLocation(end)); // 设置终点
		return params;
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {// 获取驾车搜索结果的回调方法
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			ToastUtil.showShort(getApplicationContext(), "抱歉，未找到结果");
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {

			return;
		}

		route = result.getRouteLines().get(0);
		DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
		baiduMap.setOnMarkerClickListener(overlay);
		List<DrivingRouteLine> routeLines = result.getRouteLines(); // 获取到所有的搜索路线，最优化的路线会在集合的前面
		overlay.setData(routeLines.get(0)); // 把搜索结果设置到覆盖物
		overlay.addToMap(); // 把搜索结果添加到地图
		overlay.zoomToSpan(); // 把搜索结果在一个屏幕内显示完

		List<DrivingRouteLine> rLines = result.getRouteLines();
		List<DrivingStep> steps = rLines.get(0).getAllStep();
		int distance = rLines.get(0).getDistance(); // 路程
		int time = rLines.get(0).getDuration() / 60; // 耗时
		tv_total_money.setText("● 预计乘车总费用为：" + distance * 5 / 1000 + "元");// 暂定每公里收费5元
		if (FLAG) {

			tv_need_time.setText("● 全程：" + MapUtil.distanceFormatter(distance).toString() + ", 约需："
					+ MapUtil.timeFormatter(time).toString());
		} else {
			tv_need_time.setText("● 还剩：" + MapUtil.distanceFormatter(distance).toString() + ", 仍需："
					+ MapUtil.timeFormatter(time).toString());
		}
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {// 获取换乘（公交、地铁）搜索结果的回调方法
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {// 获取步行搜索结果的回调方法
	}

	private void launchNavigator() {// 启动GPS导航. 前置条件：导航引擎初始化成功
		// 这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
		BaiduNaviManager.getInstance().launchNavigator(this, start.latitude, start.longitude, start_location,
				end.latitude, end.longitude, end_loacatin, NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(OrderNowDetailActivity.this, BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
	}

	private void beginLocation() {// 开始定位
		mCurrentMode = LocationMode.Hight_Accuracy;
		baiduMap.setMyLocationEnabled(false);// 开启定位图层
		mLocClient = new LocationClient(this);// 定位初始化
		baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));// 设置地图缩放级别为15
		mLocClient.registerLocationListener(myListener);
		// 设置定位图层的配置
		// boolean enableDirection = true; // 设置允许显示方向
		// BitmapDescriptor customMarker =
		// BitmapDescriptorFactory.fromResource(R.drawable.location_point); //
		// 自定义定位的图标
		// MyLocationConfiguration config = new
		// MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
		// enableDirection, customMarker);
		// baiduMap.setMyLocationConfigeration(config);
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);// 设置是否返回定位结果 包括地址信息
		option.setNeedDeviceDirect(true);// 设置返回结果是否包含手机机头的方向
		option.setOpenGps(false);// 打开gpss
		option.setCoorType("bd09ll"); // 设置坐标类型 取值有3个： 返回国测局经纬度坐标系：gcj02
		// 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
		option.setScanSpan(1000);// 扫描间隔 单位毫秒
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		mLocClient.setLocOption(option);
		mLocClient.start();

	}

	public class MyLocationListenner implements BDLocationListener {// 定位SDK监听函数

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mapView == null) {// mapview销毁后不在处理新接收的位置
				return;
			}
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					.latitude(location.getLatitude()).longitude(location.getLongitude()).build();// 此处设置开发者获取到的方向信息，顺时针0-360
			baiduMap.setMyLocationData(locData);
			double Latitude = location.getLatitude(); // 获取经度
			double Longitude = location.getLongitude(); // 获取纬度
			String address = location.getAddrStr();
			start = new LatLng(Latitude, Longitude);
			tv_my_location.setText("● " + address.toString());
			LogUtil.i("info", "纬度：" + Latitude + "经度：" + Longitude + "地址：" + address);
		}
	}

	public void inputDialog(final String title) {// 途经点输入对话框
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View dialogView = layoutInflater.inflate(R.layout.dialog_input, null);
		tv_input_tip2 = (TextView) dialogView.findViewById(R.id.tv_input_tip2);// 获取布局中的控件
		tv_input_tip1 = (TextView) dialogView.findViewById(R.id.tv_input_tip1);
		et_input1 = (EditText) dialogView.findViewById(R.id.et_input1);
		et_input2 = (EditText) dialogView.findViewById(R.id.et_input2);
		rg_main = (RadioGroup) dialogView.findViewById(R.id.rg_main);

		rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
				switch (checkedId) {
				case R.id.rb_address_name:
					INPUT_FORMAT = false;
					tv_input_tip1.setText(mContext.getString(R.string.add_point1));
					tv_input_tip2.setText(mContext.getString(R.string.add_point2));
					break;

				case R.id.rb_address_lat_lng:
					INPUT_FORMAT = true;
					tv_input_tip1.setText(mContext.getString(R.string.add_lng));
					tv_input_tip2.setText(mContext.getString(R.string.add_lnt));
					break;

				default:
					break;
				}
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(title);
		builder.setView(dialogView);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(OrderNowDetailActivity.this, TravelTraceUploadActivity.class);
				startActivity(intent);

			}

		});

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
//				Intent intent = new Intent(OrderNowDetailActivity.this, RouteGuideActivity.class);
//				startActivity(intent);
				Intent intent = new Intent(OrderNowDetailActivity.this, TravelTraceQueryActivity.class);
				startActivity(intent);
			}
		});
		builder.show();
	}

	private void sendDataToNet() {
		RequestParams params = new RequestParams();// 封装订单数据包
		params.addBodyParameter("DriverID",
				SharePreferenceUtils.getString(OrderNowDetailActivity.this, "DriverID", ""));
		params.addBodyParameter("Identify",
				SharePreferenceUtils.getString(OrderNowDetailActivity.this, "Identify", ""));
		params.addBodyParameter("OrderID", "");
		params.addBodyParameter("Location", "");
		params.addBodyParameter("LocationLongitude", "");
		params.addBodyParameter("LocationLatitude", "");
		params.addBodyParameter("CurrentMileage", "");
		params.addBodyParameter("StartOrEnd", "2");

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, HttpConstant.ORDER_START_OR_END, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载请求
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { // 请求成功
				String result = responseInfo.result;
				Log.i("info", "网络数据加载成功：" + result);
				Intent intent = new Intent(OrderNowDetailActivity.this, OrderDataCommitActivity.class);
				startActivity(intent);

			}

			@Override
			public void onFailure(HttpException error, String msg) {// 请求失败
				Log.i("info", "请求数据失败原因：" + msg);
				Toast.makeText(OrderNowDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
			}
		});

	}

	public void doClick(View v) {

		switch (v.getId()) {
		case R.id.cb_route_plan:// 轨迹追踪

			if (baiduMap.isTrafficEnabled()) {
				baiduMap.setTrafficEnabled(false);// 开启交通图
			} else {
				baiduMap.setTrafficEnabled(true);
			}
			break;
		case R.id.cb_navigation:// 导航
			baiduMap.clear();
			beginLocation();
			launchNavigator();
			break;
		case R.id.ll_show:// 展开
			ll_show.setVisibility(View.GONE);
			ll_hiden.setVisibility(View.VISIBLE);
			break;
		case R.id.ll_hiden:// 影藏
			ll_hiden.setVisibility(View.GONE);
			ll_show.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_add_point:// 增加途经点
			inputDialog(mContext.getString(R.string.add_point));
			break;
		case R.id.btn_end_travel:// 结束行程
			sendDataToNet();

			break;
		case R.id.cb_route_again:// 重新规划路线
			FLAG = false;
			baiduMap.clear();
			if (cb_route_again.isChecked()) {
				routePlanSearch = RoutePlanSearch.newInstance();
				routePlanSearch.setOnGetRoutePlanResultListener(this);
				routePlanSearchInit();
			} else {

				beginLocation();
				Log.i("info", "开始定位");
			}
			break;
		case R.id.cb_satellite:// 轨迹回放
			if (baiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
				baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);// 卫星地图
			} else if (baiduMap.getMapType() == BaiduMap.MAP_TYPE_SATELLITE) {
				baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 普通地图
			}

			break;
		case R.id.iv_back:
			finish();
			break;

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

}
