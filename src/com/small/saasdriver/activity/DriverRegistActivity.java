package com.small.saasdriver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.small.saasdriver.utils.AnimationUtil;
import com.small.saasdriver.utils.JudgeUtils;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.SharedPreferencesUtil;
import com.small.saasdriver.utils.ThreadJudge;
import com.small.saasdriver.utils.ToastUtil;

public class DriverRegistActivity extends BaseActivity {
	private Button bt, regist_btn;
	private ImageButton deleteuser, deletepassword, deleteemail;
	private int time = 60;
	private String userstring, verificationstring, passwordstring;
	private EditText user, verification, password, email, password2;
	public InputMethodManager manager;
	private boolean obtain;
	private ThreadJudge myanzhengmathread;
	// private SharedPreferencesUtil sp;
	private long f = 0;
	private int time2;
	Handler timehandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 1) {
				bt.setText(msg.arg1 + "s后重新获取");
			}
			if (msg.what == 2) {
				bt.setText("获取验证码");
				bt.setEnabled(true);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_regist);
		initView();
		setListener();
		long oldtime = SharePreferenceUtils.getLong(DriverRegistActivity.this, "DriverRegistTime", f);
		Log.i("oldtime", "onSuccess result:2" + oldtime);
		time2 = Integer.parseInt(String.valueOf((System.currentTimeMillis() - oldtime) / 1000));
		if (time2 <= 60) {
			time = 60 - time2;
			bt.setEnabled(false);
			myanzhengmathread = new ThreadJudge(time, timehandler);
			myanzhengmathread.start();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		onHideSoftInput(event);
		return super.onTouchEvent(event);

	}

	public void onHideSoftInput(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}

		}

	}

	private void initView() {
		user = (EditText) findViewById(R.id.DriverRegist_user);
		verification = (EditText) findViewById(R.id.DriverRegist_Verification);
		password = (EditText) findViewById(R.id.DriverRegist_password);
		password2 = (EditText) findViewById(R.id.DriverRegist_password2);
		regist_btn = (Button) findViewById(R.id.regist);
		bt = (Button) findViewById(R.id.activity_user_registered_btn);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		deleteuser = (ImageButton) findViewById(R.id.delete_password);
		deletepassword = (ImageButton) findViewById(R.id.delete_password2);
		deleteemail = (ImageButton) findViewById(R.id.delete_DriverRegist_password2);

	}

	private void setListener() {
		bt.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		deleteuser.setOnClickListener(this);
		deletepassword.setOnClickListener(this);
		deleteemail.setOnClickListener(this);
		regist_btn.setOnClickListener(this);
	}

	/**
	 * 获取输入的手机号
	 * 
	 * @return 手机号(如果不符合条件,反悔空)
	 */
	public String getphone() {
		// 手机号
		userstring = user.getText().toString().trim();
		// 判断手机号是否为空
		if (TextUtils.isEmpty(userstring)) {
			ToastUtil.show(DriverRegistActivity.this, "请输入手机号!", 0);
			userstring = "";
			AnimationUtil.setShakeAnimation(DriverRegistActivity.this, user);
		} else if (!(JudgeUtils.isMobileNO(userstring))) {
			ToastUtil.show(DriverRegistActivity.this, "请输入11位数的手机号码!", 0);
			userstring = "";
			AnimationUtil.setShakeAnimation(DriverRegistActivity.this, user);
		}
		return userstring;
	}

	/**
	 * 获取输入的密码
	 * 
	 * @return 密码(如果不符合条件,反悔空)
	 */
	public String getPassword() {
		// 密码
		passwordstring = password.getText().toString().trim();
		// 判断密码是否为空
		if (TextUtils.isEmpty(passwordstring)) {
			ToastUtil.show(DriverRegistActivity.this, "请输入密码!", 0);
			passwordstring = "";
			AnimationUtil.setShakeAnimation(DriverRegistActivity.this, password);
		} else if (passwordstring.length() > 16 || passwordstring.length() < 6) {
			ToastUtil.show(DriverRegistActivity.this, "请输入6-16位密码!", 0);
			passwordstring = "";
			AnimationUtil.setShakeAnimation(DriverRegistActivity.this, password);
		}
		return passwordstring;
	}

	/**
	 * 获取输入的验证码
	 * 
	 * @return 验证码(如果不符合条件,反悔空)
	 */
	public String getCaptcha() {
		// 验证码
		verificationstring = verification.getText().toString().trim();
		// 判断手机号是否为空
		if (TextUtils.isEmpty(verificationstring)) {
			ToastUtil.show(DriverRegistActivity.this, "请输入验证码!", 0);
			verificationstring = "";
			AnimationUtil.setShakeAnimation(DriverRegistActivity.this, verification);
		} else if (verificationstring.length() != 6) {
			ToastUtil.show(DriverRegistActivity.this, "请输入6位数的验证码!", 0);
			verificationstring = "";
			AnimationUtil.setShakeAnimation(DriverRegistActivity.this, verification);
		}
		return verificationstring;
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.activity_user_registered_btn:
			if (!(getphone().equals(""))) {
				time = 60;
				bt.setEnabled(false);
				myanzhengmathread = new ThreadJudge(time, timehandler);
				myanzhengmathread.start();
				SharePreferenceUtils.putLong(DriverRegistActivity.this, "DriverRegistTime", System.currentTimeMillis());
			}
			break;

		case R.id.delete_password:
			user.setText("");
			break;
		case R.id.delete_password2:
			password.setText("");
			break;
		case R.id.delete_DriverRegist_password2:
			password2.setText("");
			break;
		case R.id.regist:
			if (!(getphone().equals("")) && !(getPassword().equals("")) && !(getCaptcha().equals(""))) {
				Intent inent = new Intent(DriverRegistActivity.this, DriverLoginActivity.class);
				startActivity(inent);
				finish();
			}
			break;
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}

	}
}
