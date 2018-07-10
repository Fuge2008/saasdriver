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
import com.small.saasdriver.entity.driverPersonalData;
import com.small.saasdriver.entity.driverPersonalRoot;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.DialogUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.view.CircleImageView;
import com.small.saasdriver.view.XUtilsImageLoader;

public class PersonalInfoAcitivity extends BaseActivity {
	private TextView Name, Sex, Age, DrivingYear, DriverLevel, PhoneNum, Identification, Email, CompanyName, LevelID,
			IsExistDrunkDrivingRecord, BusinessTypeList, query_tv_BusinessType, title_right;
	private driverPersonalData MdriverPersonalData;
	private driverPersonalRoot MdriverPersonalRoot;
	private CircleImageView DriverPhotoPath;
	private String TAG = "PersonalinformationAcitivity";
	private StringBuilder sb, sb2;
	private Dialog mDialog;
	private ImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_personal_information);
		mDialog = DialogUtil.createLoadingDialog(PersonalInfoAcitivity.this, "正在加载.....");
		mDialog.show();
		initView();
		setLiseter();
		request();
		getPicturefromNet();
	}

	/**
	 * 加载头像,已进行了压缩处理和优化缓存机制，无需考虑oom
	 */
	private void getPicturefromNet() {
		XUtilsImageLoader image_head = new XUtilsImageLoader(PersonalInfoAcitivity.this);
		image_head.display(DriverPhotoPath, "http://p3.so.qhmsg.com/t0130092dd6c3ea991d.jpg");

	}

	private void request() {
		Resources resources = PersonalInfoAcitivity.this.getResources();
		Drawable drawable = resources.getDrawable(R.drawable.driverlogo);
		options = new ImageOptions.Builder().setFadeIn(true).setSquare(true).setFailureDrawable(drawable).build(); // 淡入效果

		RequestParams params = new RequestParams(HttpConstant.DRIVER_PRESONAL_INFROMATION);
		params.addBodyParameter("DriverID", SharePreferenceUtils.getString(PersonalInfoAcitivity.this, "DriverID", ""));
		params.addBodyParameter("Identify", SharePreferenceUtils.getString(PersonalInfoAcitivity.this, "Identify", ""));
		Log.i(TAG, params.toString());
		Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Log.i("PersonalinformationAcitivity", "onSuccess result:1" + result);
				MdriverPersonalRoot = new Gson().fromJson(result, driverPersonalRoot.class);
				if (MdriverPersonalRoot.getErrCode() == 1) {
					MdriverPersonalData = MdriverPersonalRoot.getData();
					Name.setText(MdriverPersonalData.getDriverName().toString().trim());
					Sex.setText(MdriverPersonalData.getSex() + "".trim());
					DriverLevel.setText(MdriverPersonalData.getDriverLevel() + "".trim());
					Age.setText(MdriverPersonalData.getAge() + "".trim());
					DrivingYear.setText(MdriverPersonalData.getDrivingAge() + "".trim());
					PhoneNum.setText(MdriverPersonalData.getPhoneNum().toString().trim());
					Identification.setText(MdriverPersonalData.getIdentification().toString().trim());
					Email.setText(MdriverPersonalData.getEmail() + "");
					CompanyName.setText(MdriverPersonalData.getCompanyName() + "");
					LevelID.setText(MdriverPersonalData.getLevelID() + "");
					if (MdriverPersonalData.getIsExistDrunkDrivingRecord() == 0) {
						IsExistDrunkDrivingRecord.setText("无");

					} else {
						IsExistDrunkDrivingRecord.setText(MdriverPersonalData.getIsExistDrunkDrivingRecord() + "");
					}

					sb = new StringBuilder();
					for (int i = 0; i < MdriverPersonalData.getString().size(); i++) {
						sb.append(MdriverPersonalData.getString().get(i));
						if (i != (MdriverPersonalData.getString().size() - 1)) {
							sb.append("/");
						}
					}
					BusinessTypeList.setText(sb);

					mDialog.dismiss();
				} else {
					DialogUtil.createsystemDialog(PersonalInfoAcitivity.this, "" + MdriverPersonalRoot.getErrMsg())
							.show();
				}
			}

			// 请求异常后的回调方法
			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Log.i("PersonalinformationAcitivity", "onSuccess result:2" + ex.toString());
				DialogUtil.createsystemDialog(PersonalInfoAcitivity.this, "服务器故障!").show();
			}

			// 主动调用取消请求的回调方法
			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {
				mDialog.dismiss();
			}
		});
		// 主动调用取消请求
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		Name = (TextView) findViewById(R.id.query_tv_name);
		Sex = (TextView) findViewById(R.id.query_tv_Sex);
		Age = (TextView) findViewById(R.id.query_tv_Age);
		DrivingYear = (TextView) findViewById(R.id.query_tv_DrivingYear);
		DriverLevel = (TextView) findViewById(R.id.query_tv_DriverLevel);
		PhoneNum = (TextView) findViewById(R.id.query_tv_PhoneNum);
		Identification = (TextView) findViewById(R.id.query_tv_Identification);
		Email = (TextView) findViewById(R.id.query_tv_Email);
		CompanyName = (TextView) findViewById(R.id.query_tv_CompanyName);
		LevelID = (TextView) findViewById(R.id.query_tv_LevelID);
		IsExistDrunkDrivingRecord = (TextView) findViewById(R.id.query_tv_IsExistDrunkDrivingRecord);
		BusinessTypeList = (TextView) findViewById(R.id.query_tv_BusinessTypeList);
		title_right = (TextView) findViewById(R.id.title_right);
		DriverPhotoPath = (CircleImageView) findViewById(R.id.query_tv_touxiang);
	}

	private void setLiseter() {
		iv_back.setOnClickListener(this);
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
