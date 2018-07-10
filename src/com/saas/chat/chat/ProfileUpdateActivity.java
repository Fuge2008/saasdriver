package com.saas.chat.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.easemob.util.PathUtil;
import com.example.saascardriver.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.saas.chat.Constants;
import com.small.saasdriver.application.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileUpdateActivity extends BaseActivity {
	public static final int TYPE_NICK = 0;
	public static final int TYPE_FXID = 1;
	public static final int TYPE_SIGN = 2;

	private String defaultStr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fx_activity_update_info);
		int type = getIntent().getIntExtra("type", 0);
		defaultStr = getIntent().getStringExtra("default");
		TextView titleTV = (TextView) findViewById(R.id.tv_title);
		TextView saveTV = (TextView) findViewById(R.id.tv_save);
		EditText infoET = (EditText) findViewById(R.id.et_info);
		if (defaultStr != null) {

			infoET.setText(defaultStr);
		}
		initView(type, titleTV, saveTV, infoET);

	}

	private void initView(int type, TextView titleTV, TextView saveTV, final EditText infoET) {
		String title = "";
		String key = "";

		switch (type) {
		case TYPE_NICK:
			title = "修改昵称";
			key = Constants.JSON_KEY_NICK;
			break;
		case TYPE_FXID:
			title = "修改号";
			key = Constants.JSON_KEY_FXID;
			break;
		case TYPE_SIGN:
			title = "修改个人签名";
			key = Constants.JSON_KEY_SIGN;
			break;

		}
		titleTV.setText(title);
		final String finalKey = key;
		saveTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				sendDataToNet(finalKey, infoET.getText().toString().trim());
			}
		});

	}

	private void sendDataToNet(final String key, final String value) {
		RequestParams params = new RequestParams();
		// params.addBodyParameter("DriverID",
		// SharePreferenceUtils.getString(ProfileActivity.this, "DriverID",
		// ""));
		// params.addBodyParameter("Identify",
		// SharePreferenceUtils.getString(ProfileActivity.this, "Identify",
		// ""));
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value) || ((defaultStr != null) && value.equals(defaultStr))) {
			return;
		}
		if (value.length() > 30) {
			Toast.makeText(getApplicationContext(), "不能超过30个字符", Toast.LENGTH_SHORT).show();
			return;
		}
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在更新...");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
		// 本地用户资料
		//final JSONObject userJson = MyApplication.getInstance().getUserJson();

		params.addBodyParameter("key", key);
		params.addBodyParameter("value", value);
		//params.addBodyParameter("hxid", userJson.getString(Constants.JSON_KEY_HXID));
		List<File> files = new ArrayList<File>();
		if (key == Constants.JSON_KEY_AVATAR) {
			File file = new File(PathUtil.getInstance().getImagePath(), value);
			if (file.exists()) {
				files.add(file);
			}
		}
		params.addBodyParameter(value, new File(PathUtil.getInstance().getImagePath() + ""));

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, "http://120.24.211.126/fanxin3/upload/", params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载请求
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
				String result = responseInfo.result;
				progressDialog.dismiss();

//				userJson.put(key, value);
//				MyApplication.getInstance().setUserJson(userJson);
				setResult(RESULT_OK, new Intent().putExtra("value", value));
				finish();

			}

			@Override
			public void onFailure(HttpException error, String msg) {// 请求失败
				progressDialog.dismiss();
				error.printStackTrace();
			}
		});

	}
	// private void updateInServer(final String key, final String value) {
	//
	// if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value) || ((defaultStr !=
	// null) && value.equals(defaultStr))) {
	// return;
	// }
	// if (value.length() > 30) {
	// Toast.makeText(getApplicationContext(), "不能超过30个字符",
	// Toast.LENGTH_SHORT).show();
	// return;
	// }
	// final ProgressDialog progressDialog = new ProgressDialog(this);
	// progressDialog.setMessage("正在更新...");
	// progressDialog.setCanceledOnTouchOutside(false);
	// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	// progressDialog.show();
	// //本地用户资料
	// final JSONObject userJson = MyApplication.getInstance().getUserJson();
	//
	//
	//
	// List<Param> params = new ArrayList<Param>();
	// params.add(new Param("key", key));
	// params.add(new Param("value", value));
	// params.add(new Param("hxid",
	// userJson.getString(Constants.JSON_KEY_HXID)));
	// List<File> files = new ArrayList<File>();
	// if (key == Constants.JSON_KEY_AVATAR) {
	// File file = new File(PathUtil.getInstance().getImagePath(), value);
	// if (file.exists()) {
	// files.add(file);
	// }
	// }
	// OkHttpManager.getInstance().post(params, files, Constants.URL_UPDATE, new
	// OkHttpManager.HttpCallBack() {
	// @Override
	// public void onResponse(JSONObject jsonObject) {
	// progressDialog.dismiss();
	// int code = jsonObject.getIntValue("code");
	// if (code == 1000) {
	// userJson.put(key, value);
	// MyApplication.getInstance().setUserJson(userJson);
	// setResult(RESULT_OK, new Intent().putExtra("value", value));
	// finish();
	//
	// } else {
	//
	// Toast.makeText(getApplicationContext(), "更新失败,code:" + code,
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// @Override
	// public void onFailure(String errorMsg) {
	// progressDialog.dismiss();
	// }
	// });
	//
	// }

}
