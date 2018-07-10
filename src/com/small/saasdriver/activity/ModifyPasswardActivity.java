package com.small.saasdriver.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.google.gson.Gson;
import com.small.saasdriver.application.MyApplication;
import com.small.saasdriver.entity.ChengPassWord;
import com.small.saasdriver.entity.ModifyPassward;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.AnimationUtil;
import com.small.saasdriver.utils.DialogUtil;
import com.small.saasdriver.utils.JudgeUtils;
import com.small.saasdriver.utils.NetUtil;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.SharedPreferencesUtil;
import com.small.saasdriver.utils.StringUtils;
import com.small.saasdriver.utils.ThreadJudge;
import com.small.saasdriver.utils.ToastUtil;

public class ModifyPasswardActivity extends BaseActivity {
	private Button bt, ModifyPassward_logins;
	private int time = 10;
	private ImageButton mdelete_password2, delete_ModifyPassward_password, delete_ModifyPassward_password2,
			ib_delete_new_passward;
	private boolean obtain;
	private String userstring, verificationstring, userpasswordString, userpasswordString1, userpasswordString2,
			newPassword;
	private EditText user, verification, ModifyPassward_password, ModifyPassward_password2, et_confirm_new_passward;
	private LinearLayout ll_user_phone, ll_confirm_code, ll_new_password;
	public InputMethodManager manager;
	private ThreadJudge myanzhengmathread;
	private SharedPreferencesUtil sp;
	private long f = 0;
	private int time2;
	private RequestParams params;
	private ModifyPassward mModifyPassward;
	private ChengPassWord mChengPassWord;
	private Dialog mDialog;
	private boolean isLogin;
	private SmsContent content;
	Handler timehandler2 = new Handler() {
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
		setContentView(R.layout.activity_modify_passward);
		initView();
		setListener();
		long oldtime = (Long) sp.get(ModifyPasswardActivity.this, "ModifyPasswardActivityTime", f);
		Log.i("oldtime", "onSuccess result:2" + oldtime);
		time2 = Integer.parseInt(String.valueOf((System.currentTimeMillis() - oldtime) / 1000));
		if (time2 <= 60) {
			time = 60 - time2;
			bt.setEnabled(false);
			myanzhengmathread = new ThreadJudge(time, timehandler2);
			myanzhengmathread.start();
		}
	}

	private void initView() {
		isLogin = SharePreferenceUtils.getBoolean(getApplicationContext(), "isLogin", false);
		user = (EditText) findViewById(R.id.ModifyPassward_user);
		user.setText(SharePreferenceUtils.getString(getApplicationContext(), "driver_phone", ""));
		verification = (EditText) findViewById(R.id.ModifyPassward_Verification);
		ModifyPassward_logins = (Button) findViewById(R.id.ModifyPassward_login);
		bt = (Button) findViewById(R.id.ModifyPassward_verification_btn);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		mdelete_password2 = (ImageButton) findViewById(R.id.delete_password2);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		ModifyPassward_password = (EditText) findViewById(R.id.ModifyPassward_password);
		ModifyPassward_password2 = (EditText) findViewById(R.id.ModifyPassward_password2);
		delete_ModifyPassward_password = (ImageButton) findViewById(R.id.delete_ModifyPassward_password);
		delete_ModifyPassward_password2 = (ImageButton) findViewById(R.id.delete_ModifyPassward_password2);
		et_confirm_new_passward = (EditText) findViewById(R.id.et_confirm_new_passward);
		ib_delete_new_passward = (ImageButton) findViewById(R.id.ib_delete_new_passward);
		ll_user_phone = (LinearLayout) findViewById(R.id.ll_user_phone);
		ll_confirm_code = (LinearLayout) findViewById(R.id.ll_confirm_code);
		ll_new_password = (LinearLayout) findViewById(R.id.ll_new_password);
		if (isLogin) {
			// ll_user_phone.setVisibility(View.GONE);
			ll_confirm_code.setVisibility(View.GONE);
			ll_new_password.setVisibility(View.VISIBLE);
		} else if (!isLogin) {
			ll_new_password.setVisibility(View.GONE);
			// ll_user_phone.setVisibility(View.VISIBLE);
			ll_confirm_code.setVisibility(View.VISIBLE);
		}

		sp = SharedPreferencesUtil.getIntance();

		content = new SmsContent(new Handler());
		// 注册短信变化监听
		this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
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

	private void setListener() {
		if (isLogin) {

			ModifyPassward_logins.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					userpasswordString1 = getPassword(ModifyPassward_password);
					userpasswordString2 = getPassword(ModifyPassward_password2);
					newPassword = getPassword(et_confirm_new_passward);
					String identify = SharePreferenceUtils.getString(getApplicationContext(), "Identify", null);
					if (StringUtils.equals(userpasswordString1, userpasswordString2)
							&& StringUtils.getLength(newPassword, true) >= 6) {

						params = new RequestParams(HttpConstant.DRIVER_CHANGE_PASSWORD);
						params.addBodyParameter("PhoneNum", getphone());
						params.addBodyParameter("LoginPwd", newPassword);
						params.addBodyParameter("Identify", identify);

						Log.i("ModifyPasswardActivity", "手机号：" + getphone() + "新密码：" + newPassword + "令牌：" + identify);
						if (NetUtil.isNetworkConnected(ModifyPasswardActivity.this)) {
							x.http().post(params, new Callback.CommonCallback<String>() {
								@Override
								public void onSuccess(String result) {
									mChengPassWord = new Gson().fromJson(result, ChengPassWord.class);
									Log.i("ModifyPasswardActivity", result);
									if (mChengPassWord.getErrCode() == 1) {
										mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this,
												"系统提示！", mChengPassWord.getErrMsg() + "，并返回到登录", "确定",
												new OnClickListener() {
													@Override
													public void onClick(View arg0) {
														mDialog.dismiss();
														ModifyPasswardActivity.this.finish();
													}
												});
										mDialog.show();
									} else {
										mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this,
												"系统提示！", mModifyPassward.getErrMsg(), "确定", new OnClickListener() {
													@Override
													public void onClick(View arg0) {
														mDialog.dismiss();
													}
												});
										mDialog.show();
									}

								}

								@Override
								public void onError(Throwable ex, boolean isOnCallback) {
									Log.i("info", "修改密码失败提示信息：" + ex.getMessage());
									mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this, "系统提示！",
											"系统故障", "确定", new OnClickListener() {
												@Override
												public void onClick(View arg0) {
													mDialog.dismiss();
												}
											});
									mDialog.show();
								}

								@Override
								public void onCancelled(CancelledException cex) {
								}

								@Override
								public void onFinished() {

								}
							});
						} else {

							DialogUtil.showNoNetWork(ModifyPasswardActivity.this);
						}
					} else if (StringUtils.getLength(newPassword, true) <= 6) {
						ToastUtil.show(ModifyPasswardActivity.this, "新密不能少于6位", 0);

					} else if (!StringUtils.equals(userpasswordString1, userpasswordString2)) {
						AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, ModifyPassward_password);
						AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, ModifyPassward_password2);
						ToastUtil.show(ModifyPasswardActivity.this, "密码不一致", 0);
					}

				}

			});

		} else if (!isLogin) {
			bt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!(getphone().equals(""))) {
						bt.setEnabled(false);
						bt.setText("正在获取中...");
						params = new RequestParams(HttpConstant.DRIVER_LOGIN_VERIFICATION);
						params.addBodyParameter("PhoneNum", getphone());
						Log.i("PhoneNum-->", getphone());
						if (NetUtil.isNetworkConnected(ModifyPasswardActivity.this)) {
							x.http().post(params, new Callback.CommonCallback<String>() {
								@Override
								public void onSuccess(String result) {
									mModifyPassward = new Gson().fromJson(result, ModifyPassward.class);
									Log.i("ssss", result);
									if (mModifyPassward.getErrCode() == 1) {
										time = 60;
										myanzhengmathread = new ThreadJudge(time, timehandler2);
										myanzhengmathread.start();
										sp.put(ModifyPasswardActivity.this, "ModifyPasswardActivityTime",
												System.currentTimeMillis());
									} else {

										mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this,
												"系统提示！", mModifyPassward.getErrMsg(), "确定", new OnClickListener() {
													@Override
													public void onClick(View arg0) {
														mDialog.dismiss();
													}
												});
										mDialog.show();
										Message m = timehandler2.obtainMessage();
										m.what = 2;
										m.arg1 = time;
										timehandler2.sendMessage(m);
									}
								}

								@Override
								public void onError(Throwable ex, boolean isOnCallback) {
									Log.i("info",
											"获取验证码失败返回信息：" + ex.getLocalizedMessage() + ex.getMessage() + isOnCallback);
									Message m = timehandler2.obtainMessage();
									m.what = 2;
									m.arg1 = time;
									timehandler2.sendMessage(m);
									mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this, "系统提示！",
											"系统故障", "确定", new OnClickListener() {
												@Override
												public void onClick(View arg0) {
													mDialog.dismiss();
												}
											});
								}

								@Override
								public void onCancelled(CancelledException cex) {
								}

								@Override
								public void onFinished() {

								}
							});
						} else {

							DialogUtil.showNoNetWork(ModifyPasswardActivity.this);
						}

					}
				}
			});
			ModifyPassward_logins.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					if (!(getphone().equals("")) && !(getCaptcha().equals(""))) {
						userpasswordString1 = getPassword(ModifyPassward_password);
						userpasswordString2 = getPassword(ModifyPassward_password2);
						if (userpasswordString1.equals(userpasswordString2) && !(userpasswordString1.equals(""))) {
							params = new RequestParams(HttpConstant.DRIVER_UNCHANGE_PASSWORD);
							params.addBodyParameter("PhoneNum", getphone());
							params.addBodyParameter("LoginPwd", userpasswordString1);
							params.addBodyParameter("ForgetCode", getCaptcha());
							Log.i("ModifyPasswardActivity", params.toString());
							if (NetUtil.isNetworkConnected(ModifyPasswardActivity.this)) {
								x.http().post(params, new Callback.CommonCallback<String>() {
									@Override
									public void onSuccess(String result) {
										mChengPassWord = new Gson().fromJson(result, ChengPassWord.class);
										Log.i("info", result);
										if (mChengPassWord.getErrCode() == 1) {
											mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this,
													"系统提示！", mChengPassWord.getErrMsg() + "，并返回到登录", "确定",
													new OnClickListener() {
														@Override
														public void onClick(View arg0) {
															mDialog.dismiss();
															ModifyPasswardActivity.this.finish();
															sp.put(ModifyPasswardActivity.this, "user", getphone());
															sp.put(ModifyPasswardActivity.this, "userpasswordString",
																	userpasswordString1);
															// 跳转界面
															Intent intent = new Intent(ModifyPasswardActivity.this,
																	DriverLoginActivity.class);
															startActivity(intent);
														}
													});
											mDialog.show();

										} else {
											mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this,
													"系统提示！", mModifyPassward.getErrMsg(), "确定", new OnClickListener() {
														@Override
														public void onClick(View arg0) {
															mDialog.dismiss();
														}
													});
											mDialog.show();
										}

									}

									@Override
									public void onError(Throwable ex, boolean isOnCallback) {
										mDialog = DialogUtil.createDialog_onebutton(ModifyPasswardActivity.this,
												"系统提示！", "系统故障", "确定", new OnClickListener() {
													@Override
													public void onClick(View arg0) {
														mDialog.dismiss();
													}
												});
										mDialog.show();
									}

									@Override
									public void onCancelled(CancelledException cex) {
									}

									@Override
									public void onFinished() {

									}
								});
							} else {

								DialogUtil.showNoNetWork(ModifyPasswardActivity.this);
							}
						} else {
							AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, ModifyPassward_password);
							AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, ModifyPassward_password2);
							ToastUtil.show(ModifyPasswardActivity.this, "密码不一致", 0);
						}

					}
				}
			});
		}

		iv_back.setOnClickListener(this);

		mdelete_password2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				user.setText("");
			}
		});
		delete_ModifyPassward_password.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ModifyPassward_password.setText("");
			}
		});
		delete_ModifyPassward_password2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ModifyPassward_password2.setText("");
			}
		});
		if (isLogin) {
			ib_delete_new_passward.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					et_confirm_new_passward.setText("");
				}
			});
		}

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
			ToastUtil.show(ModifyPasswardActivity.this, "请输入手机号!", 0);
			userstring = "";
			AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, user);
		} else if (!(JudgeUtils.isMobileNO(userstring))) {
			ToastUtil.show(ModifyPasswardActivity.this, "请输入11位数的手机号码!", 0);
			userstring = "";
			AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, user);
		}
		return userstring;
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
			ToastUtil.show(ModifyPasswardActivity.this, "请输入验证码!", 0);
			verificationstring = "";
			AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, verification);
		} else if (verificationstring.length() != 6) {
			ToastUtil.show(ModifyPasswardActivity.this, "请输入6位数的验证码!", 0);
			verificationstring = "";
			AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, verification);
		}
		return verificationstring;
	}

	/**
	 * 获取输入的密码
	 * 
	 * @return 密码(如果不符合条件,返回空)
	 */
	public String getPassword(EditText userpassword) {
		// 密码
		userpasswordString = userpassword.getText().toString().trim();
		// 判断密码是否为空
		if (TextUtils.isEmpty(userpasswordString)) {
			ToastUtil.show(ModifyPasswardActivity.this, "请输入密码!", 0);
			userpasswordString = "";
			AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, userpassword);
		} else if (userpasswordString.length() > 16 || userpasswordString.length() < 6) {
			ToastUtil.show(ModifyPasswardActivity.this, "请输入6-16位密码!", 0);
			userpasswordString = "";
			AnimationUtil.setShakeAnimation(ModifyPasswardActivity.this, userpassword);
		}
		return userpasswordString;
	}

	/**
	 * 监听短信数据库
	 */
	class SmsContent extends ContentObserver {
		private Cursor cursor = null;

		public SmsContent(Handler handler) {
			super(handler);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			// 监听的号码
			String listenerPhone = "10690570556058448";
			// 读取收件箱中指定号码的短信
			cursor = managedQuery(Uri.parse("content://sms/inbox"), new String[] { "_id", "address", "read", "body" },
					" address=? and read=?", new String[] { listenerPhone, "0" }, "_id desc");
			// 按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
			if (cursor != null && cursor.getCount() > 0) {
				ContentValues values = new ContentValues();
				values.put("read", "1"); // 修改短信为已读模式
				cursor.moveToNext();
				int smsbodyColumn = cursor.getColumnIndex("body");
				String smsBody = cursor.getString(smsbodyColumn);
				if (StringUtils.isNotEmpty(verification.getText().toString(), true)) {
					verification.setText("");
				}
				verification.setText(getDynamicPassword(smsBody));

			}
			// 在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
			if (Build.VERSION.SDK_INT < 14) {
				cursor.close();
			}
		}
	}

	/**
	 * 从字符串中截取连续6位数字组合 ([0-9]{" + 6 + "})截取六位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
	 * 
	 * @param str
	 *            短信内容
	 * 
	 * @return 截取得到的6位动态密码
	 */
	public static String getDynamicPassword(String str) {
		// 6是验证码的位数一般为六位
		Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{" + 6 + "})(?![0-9])");
		Matcher m = continuousNumberPattern.matcher(str);
		String dynamicPassword = "";
		while (m.find()) {
			System.out.print(m.group());
			dynamicPassword = m.group();
		}

		return dynamicPassword;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.getContentResolver().unregisterContentObserver(content);
	}

	public void doClick(View v) {

		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}
	}
}
