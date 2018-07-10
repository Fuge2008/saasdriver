package com.small.saasdriver.activity.map;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.example.saascardriver.R;
import com.example.saascardriver.R.id;
import com.example.saascardriver.R.layout;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.adapter.SearchAddressAdapter;
import com.small.saasdriver.utils.StringUtils;
import com.small.saasdriver.view.ClearEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * GPS导航Demo
 */
public class RouteGuideActivity extends BaseActivity
		implements OnGetGeoCoderResultListener, OnGetPoiSearchResultListener {
	private Button btn_subimit1, btn_route;
	private LinearLayout ll_point_location1;
	private ClearEditText ce_search_point1;
	private TextView tv_show_point1, tv_show_point2;
	private ListView lv_search_point;
	private int FLAG_INPUT = 1;

	private BNaviPoint mStartPoint;
	private BNaviPoint mEndPoint;
	private List<BNaviPoint> mViaPoints;
	private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private PoiSearch mPoiSearch = null;
	private String cityName = "深圳市";
	private List<PoiInfo> searchAddresses;
	private SearchAddressAdapter searchAddressAdapter = null;
	private String start_name, end_name, point1_name, point2_name;
	private LatLng point1_LatLng, point2_LatLng, point3_LatLng;
	private LatLng start_LatLng = new LatLng(113.945151, 22.52831);
	private LatLng end_LatLng = new LatLng(114.113078, 22.549321);

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_route_guide);
		initView();
		initData();
		setListener();

	}

	private void initView() {
		btn_subimit1 = (Button) findViewById(R.id.btn_subimit1);
		btn_route = (Button) findViewById(R.id.btn_route);
		ce_search_point1 = (ClearEditText) findViewById(R.id.ce_search_point1);
		ll_point_location1 = (LinearLayout) findViewById(R.id.ll_point_location1);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_show_point1 = (TextView) findViewById(R.id.tv_show_point1);
		tv_show_point2 = (TextView) findViewById(R.id.tv_show_point2);
		// title.setText("规划路线");
		lv_search_point = (ListView) findViewById(R.id.lv_search_point);
		searchAddresses = new ArrayList<PoiInfo>();
		mStartPoint = new BNaviPoint(113.945151, 22.52831, "A8音乐大厦", BNaviPoint.CoordinateType.GCJ02);
		mEndPoint = new BNaviPoint(114.113078, 22.549321, "京基100大厦", BNaviPoint.CoordinateType.GCJ02);
		mViaPoints = new ArrayList<BNaviPoint>();

		mSearch = GeoCoder.newInstance();// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSearch.setOnGetGeoCodeResultListener(this);
	}

	private void initData() {
		ce_search_point1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				lv_search_point.setVisibility(View.VISIBLE);
				mPoiSearch.searchInCity(
						(new PoiCitySearchOption()).city(cityName).keyword(s.toString()).pageNum(0).pageCapacity(20));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		searchAddressAdapter = new SearchAddressAdapter(this, R.layout.item_search_address, searchAddresses);
		lv_search_point.setAdapter(searchAddressAdapter);
		lv_search_point.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				PoiInfo poiInfo = searchAddresses.get(position);
				Bundle bundle = new Bundle();
				bundle.putString("Ing", poiInfo.location.longitude + "");
				bundle.putString("Iat", poiInfo.location.latitude + "");
				bundle.putString("Address", poiInfo.name);
				bundle.putString("DetailedAddress", poiInfo.address);
				ce_search_point1.setText(poiInfo.name);
				if (StringUtils.isEmpty(tv_show_point1.getText().toString())) {
					FLAG_INPUT = 1;
					point1_name = poiInfo.name.toString();
					point1_LatLng = new LatLng(poiInfo.location.longitude, poiInfo.location.latitude);
				} else if (StringUtils.isNotEmpty(tv_show_point1, true)) {
					FLAG_INPUT = 2;
					point2_name = poiInfo.name.toString();
					point2_LatLng = new LatLng(poiInfo.location.longitude, poiInfo.location.latitude);

				}
			}
		});
	}

	private void setListener() {
		btn_subimit1.setOnClickListener(this);
		btn_route.setOnClickListener(this);
		iv_back.setOnClickListener(this);
	}

	private void addViaPoint() {
		// 默认坐标系为GCJ
		if (point1_LatLng != null) {

			BNaviPoint viaPoint1 = new BNaviPoint(point1_LatLng.longitude, point1_LatLng.latitude, point1_name);
			mViaPoints.add(viaPoint1);
		}
		if (point2_LatLng != null) {
			BNaviPoint viaPoint2 = new BNaviPoint(point2_LatLng.longitude, point2_LatLng.latitude, point2_name);
			mViaPoints.add(viaPoint2);
		}

	}

	/**
	 * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。 前置条件：导航引擎初始化成功
	 */
	private void launchNavigator2() {
		// 这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标

		BNaviPoint startPoint = new BNaviPoint(start_LatLng.longitude, start_LatLng.latitude, "南山",
				BNaviPoint.CoordinateType.BD09_MC);
		BNaviPoint endPoint = new BNaviPoint(end_LatLng.longitude, end_LatLng.latitude, "罗湖",
				BNaviPoint.CoordinateType.BD09_MC);
		BaiduNaviManager.getInstance().launchNavigator(RouteGuideActivity.this, startPoint, // 起点（可指定坐标系）
				endPoint, // 终点（可指定坐标系）
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(RouteGuideActivity.this, BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
	}

	/**
	 * 增加一个或多个途经点，启动GPS导航. 前置条件：导航引擎初始化成功
	 */
	private void launchNavigatorViaPoints() {
		List<BNaviPoint> points = new ArrayList<BNaviPoint>();
		points.add(mStartPoint);
		points.addAll(mViaPoints);
		points.add(mEndPoint);
		BaiduNaviManager.getInstance().launchNavigator(this, points, // 路线点列表
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(RouteGuideActivity.this, BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.btn_subimit1:
			if (StringUtils.isNotEmpty(ce_search_point1.getText(), true)) {
				if (FLAG_INPUT == 1) {
					tv_show_point1.setText("途经点1：" + ce_search_point1.getText().toString());
					ce_search_point1.setText("");
					lv_search_point.setVisibility(View.GONE);
				} else if (FLAG_INPUT == 2) {
					tv_show_point2.setText("途经点2：" + ce_search_point1.getText().toString());
					ce_search_point1.setText("");
					lv_search_point.setVisibility(View.GONE);
				}
			}
			break;

		case R.id.btn_route:
			addViaPoint();
			if (mViaPoints.size() == 0) {
				launchNavigator2();
			} else {
				launchNavigatorViaPoints();
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
		mSearch.destroy();
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {

	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			List<PoiInfo> list = result.getAllPoi();
			if (list != null && list.size() > 0) {
				searchAddresses.clear();
				searchAddresses.addAll(list);
				searchAddressAdapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {

	}

}