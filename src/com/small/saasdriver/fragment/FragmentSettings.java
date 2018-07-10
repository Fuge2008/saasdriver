package com.small.saasdriver.fragment;

import org.apache.http.message.BasicNameValuePair;
import org.xutils.x;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saascardriver.R;
import com.saas.chat.Constants;
import com.saas.chat.common.Utils;
import com.saas.chat.view.activity.SettingActivity;
import com.saas.chat.view.activity.WebViewActivity;
import com.small.saasdriver.activity.AppSettingActivity;
import com.small.saasdriver.activity.DriverLoginActivity;
import com.small.saasdriver.activity.FAQActivity;
import com.small.saasdriver.activity.SAASActivity;
import com.small.saasdriver.activity.UserGuideActivity;
import com.small.saasdriver.application.MyApplication;
import com.small.saasdriver.utils.SharePreferenceUtils;

public class FragmentSettings extends BaseFragment {
	private Button btn_logout;
	private TextView tv_app_setting, tv_contact_customer_service, tv_about_SAAS, tv_use_guide, faq;
	private Intent inent;

	@Override
	protected View initView() {// 初始化控件
		View view = View.inflate(mActivity, R.layout.fragment_settings, null);
		tv_app_setting = (TextView) view.findViewById(R.id.app_setting);
		tv_contact_customer_service = (TextView) view.findViewById(R.id.contact_customer_service);
		tv_about_SAAS = (TextView) view.findViewById(R.id.about_SAAS);
		tv_use_guide = (TextView) view.findViewById(R.id.use_guide);
		faq = (TextView) view.findViewById(R.id.faq);
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
		x.view().inject(getActivity(), view);// 初始化XUtils框架
		setListener();
		return view;
	}

	private void setListener() {
		tv_app_setting.setOnClickListener(this);
		tv_contact_customer_service.setOnClickListener(this);
		tv_about_SAAS.setOnClickListener(this);
		tv_use_guide.setOnClickListener(this);
		faq.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	public void showDialog() {
		new AlertDialog.Builder(getActivity()).setTitle("系统提示").setMessage("拨打客服电话 (400-6800-235)")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent(Intent.ACTION_CALL);
						Uri data = Uri.parse("tel:" + "4006800235");
						intent.setData(data);
						startActivity(intent);
						arg0.dismiss();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).create().show();
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.app_setting:

			Intent intent = new Intent(getActivity(), AppSettingActivity.class);
			startActivity(intent);
			break;
		case R.id.contact_customer_service:
			showDialog();
			break;
		case R.id.about_SAAS:

			Utils.start_Activity(mActivity, WebViewActivity.class, new BasicNameValuePair(Constants.Title, "关于SAAS"),
					new BasicNameValuePair(Constants.URL, "http://www.easemob.com/"));

			break;
		case R.id.use_guide:
			Utils.start_Activity(mActivity, WebViewActivity.class, new BasicNameValuePair(Constants.Title, "使用指南"),
					new BasicNameValuePair(Constants.URL,
							"http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/1025v4.1.1/index.html"));

			break;
		case R.id.faq:
			Utils.start_Activity(mActivity, WebViewActivity.class, new BasicNameValuePair(Constants.Title, "FAQ"),
					new BasicNameValuePair(Constants.URL, "http://www.imgeek.org/help/"));

			break;
		case R.id.btn_logout:
			MyApplication.getInstance().setIdentifyt(null);
			SharePreferenceUtils.putBoolean(getActivity(), "isLogin", false);
			inent = new Intent(getActivity(), DriverLoginActivity.class);
			startActivity(inent);
			getActivity().finish();
			break;

		}
	}

}
