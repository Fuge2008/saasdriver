package com.small.saasdriver.activity;

import java.util.Set;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.google.gson.Gson;
import com.small.saasdriver.application.MyApplication;
import com.small.saasdriver.entity.LoginData;
import com.small.saasdriver.entity.LoginrRoot;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.AnimationUtil;
import com.small.saasdriver.utils.DialogUtil;
import com.small.saasdriver.utils.JudgeUtils;
import com.small.saasdriver.utils.NetUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.SharedPreferencesUtil;
import com.small.saasdriver.utils.ToastUtil;

public class DriverLoginActivity extends BaseActivity {
	private static final String TAG = "DriverLoginActivity";
	private TextView t1, t2, t3;
	private String userString, userpasswordString;
	private EditText user, userpassword;
	private CheckBox remindpassword, remindpassword2;
	private Button login;
	private ImageButton deleteueser, deletepassword;
	public InputMethodManager manager;
	// private SharedPreferencesUtil sp;
	private Intent inent;
	private LoginrRoot mLoginrRoot;
	private LoginData mLoginData;
	private RequestParams params;
	private Dialog mDialog, mDialog2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_login);
		initView();
		setListener();

	}

	// 点击事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		onHideSoftInput(event);
		return super.onTouchEvent(event);

	}

	// 点击Activity 空白处隐藏键盘
	public void onHideSoftInput(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	private void initView() {
		t1 = (TextView) findViewById(R.id.wangle);
		t3 = (TextView) findViewById(R.id.wangle3);
		user = (EditText) findViewById(R.id.User);
		userpassword = (EditText) findViewById(R.id.UserPassword);
		remindpassword = (CheckBox) findViewById(R.id.remindpassword);
		remindpassword2 = (CheckBox) findViewById(R.id.remindpassword2);
		deleteueser = (ImageButton) findViewById(R.id.delete_user);
		deletepassword = (ImageButton) findViewById(R.id.delete_password);
		login = (Button) findViewById(R.id.login);
		// 获取上次登录的账号或者密码
		user.setText(SharePreferenceUtils.getString(DriverLoginActivity.this, "user", "").toString());
		if (SharePreferenceUtils.getString(DriverLoginActivity.this, "ischeck1", "flase").toString().equals("true")) {
			userpassword.setText(
					SharePreferenceUtils.getString(DriverLoginActivity.this, "userpasswordString", "").toString());
			remindpassword.setChecked(true);
		}
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (SharePreferenceUtils.getString(DriverLoginActivity.this, "ischeck2", "flase").toString().equals("true")) {
			login();
		} else {
			remindpassword2.setChecked(false);
			SharePreferenceUtils.putString(DriverLoginActivity.this, "ischeck2", "false");
		}
	}

	private void setListener() {
		// 记住密码
		remindpassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				SharePreferenceUtils.putString(DriverLoginActivity.this, "ischeck1", "true");
			}
		});
		remindpassword2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				SharePreferenceUtils.putString(DriverLoginActivity.this, "ischeck2", "true");
			}
		});
		deleteueser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				user.setText("");
			}
		});
		deletepassword.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userpassword.setText("");
			}
		});
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				login();
			}
		});
		t1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到修改密码界面

				Intent inent = new Intent(DriverLoginActivity.this, ModifyPasswardActivity.class);
				startActivity(inent);
			}
		});
		// t2.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// //跳转到验证码登录
		//
		// Intent inent=new
		// Intent(DriverLoginActivity.this,DriverVerificationLogin.class);
		// startActivity(inent);
		// }
		// });
		t3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到用户注册界面

				Intent inent = new Intent(DriverLoginActivity.this, DriverRegistActivity.class);
				startActivity(inent);
			}
		});

	}

	/**
	 * 获取输入的手机号
	 * 
	 * @return 手机号(如果不符合条件,反悔空)
	 */
	public String getphone() {
		// 手机号
		userString = user.getText().toString().trim();
		// 判断手机号是否为空
		if (TextUtils.isEmpty(userString)) {
			ToastUtil.show(DriverLoginActivity.this, "请输入手机号!", 0);
			userString = "";
			AnimationUtil.setShakeAnimation(DriverLoginActivity.this, user);
		} else if (!(JudgeUtils.isMobileNO(userString))) {
			ToastUtil.show(DriverLoginActivity.this, "请输入11位数的手机号码!", 0);
			userString = "";
			AnimationUtil.setShakeAnimation(DriverLoginActivity.this, user);
		}
		return userString;
	}

	/**
	 * 获取输入的密码
	 * 
	 * @return 密码(如果不符合条件,返回空)
	 */
	public String getPassword() {
		// 密码
		userpasswordString = userpassword.getText().toString().trim();
		// 判断密码是否为空
		if (TextUtils.isEmpty(userpasswordString)) {
			ToastUtil.show(DriverLoginActivity.this, "请输入密码!", 0);
			userpasswordString = "";
			AnimationUtil.setShakeAnimation(DriverLoginActivity.this, userpassword);
		} else if (userpasswordString.length() > 16 || userpasswordString.length() < 6) {
			ToastUtil.show(DriverLoginActivity.this, "请输入6-16位密码!", 0);
			userpasswordString = "";
			AnimationUtil.setShakeAnimation(DriverLoginActivity.this, userpassword);
		}
		return userpasswordString;
	}

	public void login() {

		mDialog = DialogUtil.createLoadingDialog(DriverLoginActivity.this, "正在登陆.....");

		if (!(getphone().equals("")) && !(getPassword().equals(""))) {
			params = new RequestParams(HttpConstant.DRIVER_LOGIN);
			params.addBodyParameter("PhoneNum", getphone());
			params.addBodyParameter("LoginPwd", getPassword());
			params.addBodyParameter("MobilePlatform", "2");
			params.addBodyParameter("IMEICode", getDvired_ID());
			params.addBodyParameter("Alias", getphone());

			// setAlias(getphone().toString().trim());

			// Log.i("PersonalinformationAcitivity", "onSuccess result:2" +
			// params);
			if (NetUtil.isNetworkConnected(DriverLoginActivity.this)) {
				mDialog.show();
				x.http().post(params, new Callback.CommonCallback<String>() {
					@Override
					public void onSuccess(String result) {
						mLoginrRoot = new Gson().fromJson(result, LoginrRoot.class);
						if (mLoginrRoot.getErrCode() == 1) {
							if ((SharePreferenceUtils.getString(DriverLoginActivity.this, "ischeck1", "false")
									.toString().equals("true"))) {
								if (!(user.getText().toString().equals(""))
										&& !(userpassword.getText().toString().equals(""))) {
									userString = user.getText().toString().trim();// 登录时保持账号密码
									userpasswordString = userpassword.getText().toString().trim();
									SharePreferenceUtils.putString(DriverLoginActivity.this, "user", userString);
									SharePreferenceUtils.putString(DriverLoginActivity.this, "userpasswordString",
											userpasswordString);
								}
							}
							if (SharePreferenceUtils.getString(DriverLoginActivity.this, "ischeck2", "false").toString()
									.equals("true")) {
								SharePreferenceUtils.putString(DriverLoginActivity.this, "ischeck2", "true");
							}
							// DriverID mDriverID = DriverID.getLoginData();
							// mDriverID.setDriverID(mLoginrRoot.getData().getDriverID());
							// mDriverID.setIdentify(mLoginrRoot.getData().getIdentify());
							// 保存Identify

							MyApplication.getInstance().setIdentifyt(mLoginrRoot.getData().getIdentify());
							SharePreferenceUtils.putBoolean(DriverLoginActivity.this, "isLogin", true);
							SharePreferenceUtils.putString(DriverLoginActivity.this, "Identify",
									mLoginrRoot.getData().getIdentify());
							SharePreferenceUtils.putString(DriverLoginActivity.this, "DriverID",
									mLoginrRoot.getData().getDriverID());
							SharePreferenceUtils.putString(DriverLoginActivity.this, "driver_phone", getphone());
							// mDriverID.setLoginID(mLoginrRoot.getData().getLoginID());
							inent = new Intent(DriverLoginActivity.this, MainActivity.class);
							startActivity(inent);
							finish();
						} else {
							mDialog2 = DialogUtil.createDialog_onebutton(DriverLoginActivity.this, "系统提示！",
									mLoginrRoot.getErrMsg(), "确定", new OnClickListener() {
										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub
											mDialog2.dismiss();
										}
									});
							mDialog2.show();

						}
					}

					@Override
					public void onError(Throwable ex, boolean isOnCallback) {
						mDialog2 = DialogUtil.createDialog_onebutton(DriverLoginActivity.this, "系统提示！", "登录失败，请稍后重试",
								"确定", new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										mDialog2.dismiss();
									}
								});
						mDialog2.show();
					}

					@Override
					public void onCancelled(CancelledException cex) {
					}

					@Override
					public void onFinished() {
						mDialog.dismiss();
					}
				});
			} else {
				mDialog.dismiss();
				DialogUtil.showNoNetWork(DriverLoginActivity.this);
			}

		}
	}

	public String getDvired_ID() {
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		return DEVICE_ID;
	}

	// String alias;
	//
	// public void setAlias(String phone) {
	// alias = phone;
	// // 调用 Handler 来异步设置别名
	// mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	// }
	//
	// private TagAliasCallback mAliasCallback = new TagAliasCallback() {
	//
	// public void gotResult(int code, String alias, Set<String> tags) {
	// String logs;
	// switch (code) {
	// case 0:
	// logs = "Set tag and alias success";
	// Log.i(TAG, logs);
	// Log.i("nihao", alias);
	// // 建议这里往 SharePreference 里写一个设置的状态。成功设置一次后，以后不必再次设置了。
	// break;
	// case 6002:
	// logs = "Failed to set alias and tags due to timeout. Try again after
	// 60s.";
	// Log.i(TAG, logs);
	// // 延迟 60 秒来调用 Handler 设置别名
	// mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias),
	// 1000 * 60);
	// break;
	// default:
	// logs = "Failed with errorCode = " + code;
	// Log.e(TAG, logs);
	// }
	//
	// }
	// };
	// private static final int MSG_SET_ALIAS = 1001;
	// private final Handler mHandler = new Handler() {
	// @Override
	// public void handleMessage(android.os.Message msg) {
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case MSG_SET_ALIAS:
	// Log.d(TAG, "Set alias in handler.");
	// // 调用 JPush 接口来设置别名。
	// JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj,
	// null, mAliasCallback);
	// break;
	// default:
	// Log.i(TAG, "Unhandled msg - " + msg.what);
	// }
	// }
	// };
	public void doClick(View v) {
	}

}
