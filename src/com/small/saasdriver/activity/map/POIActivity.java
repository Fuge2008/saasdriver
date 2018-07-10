package com.small.saasdriver.activity.map;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.baidu.navisdk.util.verify.BNKeyVerifyListener;
import com.example.saascardriver.R;
import com.small.saasdriver.activity.BaseActivity;

public class POIActivity extends BaseActivity implements OnGetPoiSearchResultListener {

	private static final String TAG = POIActivity.class.getSimpleName();
	public LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private PoiSearch mPoiSearch = null;
	private LatLng start_Latlng;
	private Double start_latitude;
	private Double start_longitude;
	private String type, start_address;
	private EditText input;
	private Button btn_search;
	private String mark = "";
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	private List<PoiInfo> pois;
	private InfoWindow mInfoWindow;
	private PoiInfo poi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poi);
		initView();
		setListener();
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		input = (EditText) findViewById(R.id.et_input);
		btn_search = (Button) findViewById(R.id.btn_search);

	}

	private void setListener() {
		iv_back.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);// 设置是否返回定位结果 包括地址信息
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		Log.d(TAG, "locClient is INIT");
		if (mLocClient != null && mLocClient.isStarted())
			mLocClient.requestLocation();
		else
			Log.d(TAG, "locClient is null or not started");

	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100)
					.latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			start_longitude = location.getLongitude();
			start_latitude = location.getLatitude();
			start_Latlng = new LatLng(start_latitude, start_longitude);
			start_address = location.getAddrStr();

			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mLocClient.stop();
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		mPoiSearch.destroy();
		super.onDestroy();
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			type = input.getText().toString();
			mark = type;
			mPoiSearch.searchNearby(
					new PoiNearbySearchOption().keyword(mark).pageCapacity(10).radius(5000).location(start_Latlng));

			break;
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onGetPoiDetailResult(final PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(POIActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		} else {
			try {
				//
				// Toast.makeText(POIActivity.this, result.getName() + ": " +
				// result.getAddress(), Toast.LENGTH_SHORT)
				// .show();
				mBaiduMap.hideInfoWindow();
				View view = View.inflate(POIActivity.this, R.layout.popupinfo_search, null);
				TextView tv_diatance = (TextView) view.findViewById(R.id.tv_diatance);
				TextView tv_search_name = (TextView) view.findViewById(R.id.tv_search_name);
				TextView tv_search_address = (TextView) view.findViewById(R.id.tv_search_address);

				tv_search_name.setText(result.getName());
				tv_search_address.setText(result.getAddress());
				tv_diatance.setText("距  离：" + (int) DistanceUtil.getDistance(result.getLocation(), start_Latlng) + "米");
				Log.i("info", "参数" + result.getName() + result.getAddress() + result.getLocation().longitude
						+ result.getLocation().latitude);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(result.getLocation());
				mBaiduMap.animateMapStatus(u, 300);
				Point p = mBaiduMap.getProjection().toScreenLocation(result.getLocation());
				p.y -= 47;
				LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
				// 为弹出的InfoWindow添加点击事件
				mInfoWindow = new InfoWindow(view, llInfo, 0);
				// 显示InfoWindow
				mBaiduMap.showInfoWindow(mInfoWindow);
				// handle.sendEmptyMessage(HANDLE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 导航
			LinearLayout ll_navigation = (LinearLayout) findViewById(R.id.ll_navigation);
			ll_navigation.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mBaiduMap.hideInfoWindow();
					Log.i("info",
							"起点经度：\n" + start_Latlng.longitude + "起点纬度：\n" + start_Latlng.latitude + "起点地址：\n"
									+ start_address + "终点经度：\n" + result.getLocation().longitude + "终点纬度：\n"
									+ result.getLocation().latitude + "终点地址：\n" + result.getName());
					BaiduNaviManager.getInstance().launchNavigator(POIActivity.this, start_Latlng.longitude,
							start_Latlng.latitude, start_address, result.getLocation().longitude,
							result.getLocation().latitude, result.getName(), NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
							true, // 真实导航
							BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
							new OnStartNavigationListener() { // 跳转监听

								@Override
								public void onJumpToNavigator(Bundle configParams) {
									Intent intent = new Intent(POIActivity.this, BNavigatorActivity.class);
									intent.putExtras(configParams);
									startActivity(intent);
								}

								@Override
								public void onJumpToDownloader() {
								}
							});

				}
			});

		}

	}

	@Override
	public void onGetPoiResult(PoiResult result) {

		if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();

			MyOm onverlay = new MyOm(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(onverlay);
			onverlay.setResult(result);
			onverlay.addToMap();
			onverlay.zoomToSpan();

			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(POIActivity.this, strInfo, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 要显示poi上面的详细信息，要继承PoiOverlay类，重写onPoiClick方法，具体实现在jar包中已经封装好，
	 * 实现点击查看详细内容，只要调用mPoiSearch.searchPoiDetail。
	 * (如果要显示某一点详细信息，要跳转到详细内容Activity时，就需要重写onTap()方法， 只要调用
	 * mSearch.poiDetailSearch(info.uid)，就可以实现跳转 。 )
	 */
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap mBaiduMap) {
			super(mBaiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			return true;
		}

	}

	private class MyOm extends OverlayManager {
		private PoiResult result;

		public void setResult(PoiResult result) {
			this.result = result;
		}

		public MyOm(BaiduMap mBaiduMap) {
			super(mBaiduMap);
		}

		@Override
		public boolean onMarkerClick(Marker marker) {
			onClick(marker.getZIndex());
			return true;
		}

		public boolean onClick(int index) {
			poi = result.getAllPoi().get(index);

			if (poi.hasCaterDetails) {
				mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			}

			return true;
		}

		@Override
		public List<OverlayOptions> getOverlayOptions() {// 标注poi
			List<OverlayOptions> ops = new ArrayList<OverlayOptions>();
			pois = result.getAllPoi();
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
			for (int i = 0; i < pois.size(); i++) {
				OverlayOptions op = new MarkerOptions().position(pois.get(i).location).icon(bitmap);
				ops.add(op);
				mBaiduMap.addOverlay(op);
			}
			return ops;
		}

	}

}
