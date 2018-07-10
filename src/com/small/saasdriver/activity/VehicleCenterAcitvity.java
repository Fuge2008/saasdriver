package com.small.saasdriver.activity;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.google.gson.Gson;
import com.small.saasdriver.entity.VehicleCenterData;
import com.small.saasdriver.entity.VehicleCenterRoot;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.DialogUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;

public class VehicleCenterAcitvity extends BaseActivity {
	private TextView LicenseNum, Brand, GasDisplacement, Color, FuelCost, SeatingCapacity, FuelType, VehicleType, Score,
			Level, ServicedLife, VehicleProperty, LeasingCompanyName, EnterpriseName, ExtenrlCarLC, BusinessTypeList,
			title_right;
	VehicleCenterRoot mVehicleCenterRoot;
	VehicleCenterData mVehicleCenterData;
	private ImageView driver_vehicle_center_header;
	private StringBuilder sb;
	private ImageOptions options;
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_vehicle_center);
		mDialog = DialogUtil.createLoadingDialog(VehicleCenterAcitvity.this, "正在加载.....");
		mDialog.show();
		initView();
		setLiseter();
		request();
	}

	private void request() {
		Resources resources = VehicleCenterAcitvity.this.getResources();
		Drawable drawable = resources.getDrawable(R.drawable.driverlogo);
		options = new ImageOptions.Builder().setFadeIn(true).setSquare(true).setFailureDrawable(drawable).build(); // 淡入效果
		RequestParams params = new RequestParams(HttpConstant.DRIVER_VEHICLE_CENTER);
		params.addBodyParameter("DriverID", SharePreferenceUtils.getString(VehicleCenterAcitvity.this, "DriverID", ""));
		params.addBodyParameter("Identify", SharePreferenceUtils.getString(VehicleCenterAcitvity.this, "Identify", ""));
		Log.i("JAVA", params.toString());
		Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				mVehicleCenterRoot = new Gson().fromJson(result, VehicleCenterRoot.class);
				Log.i("JAVA", result);
				if (mVehicleCenterRoot.getErrCode() == 1) {

					mVehicleCenterData = mVehicleCenterRoot.getData();
					Log.i("JAVA", "onSuccess result:1" + mVehicleCenterData.toString());
					LicenseNum.setText(mVehicleCenterData.getLicenseNum() + "");
					Brand.setText(mVehicleCenterData.getBrand() + "");
					GasDisplacement.setText(mVehicleCenterData.getGasDisplacement() + "");
					Color.setText(mVehicleCenterData.getColor() + "");
					FuelCost.setText(mVehicleCenterData.getFuelCost() + "");
					SeatingCapacity.setText(mVehicleCenterData.getSeatingCapacity() + "");
					FuelType.setText(mVehicleCenterData.getFuelType() + "");
					VehicleType.setText(mVehicleCenterData.getVehicleType() + "");
					Score.setText(mVehicleCenterData.getScore() + "");
					Level.setText(mVehicleCenterData.getLevel() + "");
					ServicedLife.setText(mVehicleCenterData.getServicedLife() + "");
					VehicleProperty.setText(mVehicleCenterData.getVehicleProperty() + "");
					LeasingCompanyName.setText(mVehicleCenterData.getLeasingCompanyName() + "");
					EnterpriseName.setText(mVehicleCenterData.getEnterpriseName() + "");
					ExtenrlCarLC.setText(mVehicleCenterData.getExtenrlCarLC() + "");
					sb = new StringBuilder();
					for (int i = 0; i < mVehicleCenterData.getString().size(); i++) {
						sb.append(mVehicleCenterData.getString().get(i));
						if (i != (mVehicleCenterData.getString().size() - 1)) {
							sb.append("/");
						}

					}
					BusinessTypeList.setText(sb);

					String path = HttpConstant.BASE_URL + mVehicleCenterData.getVehiclePhotoPath() + "";

					x.image().loadDrawable(path, options, new Callback.CommonCallback<Drawable>() {
						@Override
						public void onSuccess(Drawable result) {

							driver_vehicle_center_header.setImageDrawable(result);
							driver_vehicle_center_header.setScaleType(ImageView.ScaleType.FIT_CENTER);
						}

						@Override
						public void onError(Throwable ex, boolean isOnCallback) {
						}

						@Override
						public void onCancelled(CancelledException cex) {
						}

						@Override
						public void onFinished() {
						}
					});
					mDialog.dismiss();

				} else {
					DialogUtil.createsystemDialog(VehicleCenterAcitvity.this, "" + mVehicleCenterRoot.getErrMsg())
							.show();
				}
			}

			// 请求异常后的回调方法
			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Log.i("JAVA", "onSuccess result:2" + ex.toString());
				DialogUtil.createsystemDialog(VehicleCenterAcitvity.this, "服务器故障").show();
			}

			// 主动调用取消请求的回调方法
			@Override
			public void onCancelled(CancelledException cex) {
				Log.i("JAVA", "onSuccess result:3");
			}

			@Override
			public void onFinished() {
				Log.i("JAVA", "onSuccess result:4");
				mDialog.dismiss();
			}
		});
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		LicenseNum = (TextView) findViewById(R.id.query_tv_LicenseNum);
		Brand = (TextView) findViewById(R.id.query_tv_Brand);
		GasDisplacement = (TextView) findViewById(R.id.query_tv_GasDisplacement);
		Color = (TextView) findViewById(R.id.query_tv_Color);
		FuelCost = (TextView) findViewById(R.id.query_tv_FuelCost);
		SeatingCapacity = (TextView) findViewById(R.id.query_tv_SeatingCapacity);
		FuelType = (TextView) findViewById(R.id.query_tv_FuelType);
		VehicleType = (TextView) findViewById(R.id.query_tv_VehicleType);
		Score = (TextView) findViewById(R.id.query_tv_Score);
		Level = (TextView) findViewById(R.id.query_tv_Level);
		ServicedLife = (TextView) findViewById(R.id.query_tv_ServicedLife);
		VehicleProperty = (TextView) findViewById(R.id.query_tv_VehicleProperty);
		LeasingCompanyName = (TextView) findViewById(R.id.query_tv_LeasingCompanyName);
		EnterpriseName = (TextView) findViewById(R.id.query_tv_EnterpriseName);
		ExtenrlCarLC = (TextView) findViewById(R.id.query_tv_ExtenrlCarLC);
		BusinessTypeList = (TextView) findViewById(R.id.query_tv_BusinessTypeList2);
		title_right = (TextView) findViewById(R.id.title_right);
		driver_vehicle_center_header = (ImageView) findViewById(R.id.driver_vehicle_center_header);
	}

	private void setLiseter() {
		iv_back.setOnClickListener(this);
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}

	}

}
