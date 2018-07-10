package com.saas.chat.view.fragment;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saascardriver.R;
import com.saas.chat.Constants;
import com.saas.chat.GloableParams;
import com.saas.chat.chat.ProfileActivity;
import com.saas.chat.common.UserUtils;
import com.saas.chat.common.Utils;
import com.saas.chat.view.activity.MyCodeActivity;
import com.saas.chat.view.activity.PublicActivity;
import com.saas.chat.view.activity.SetMoneyActivity;
import com.saas.chat.view.activity.SettingActivity;

//我
public class Fragment_Profile extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
	private TextView tvname, tv_accout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.em_fragment_profile, null);
			initViews();
			initData();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	private void initViews() {
		tvname = (TextView) layout.findViewById(R.id.tvname);
		tv_accout = (TextView) layout.findViewById(R.id.tvmsg);
		String id = Utils.getValue(getActivity(), Constants.User_ID);
		tv_accout.setText(getString(R.string.wechat_id) + "：" + id);
		if (GloableParams.UserInfos != null) {
			String name = UserUtils.getUserName(ctx);
			if (name != null && !TextUtils.isEmpty(name))
				tvname.setText(name);
		}
	}

	private void setOnListener() {
		layout.findViewById(R.id.view_user).setOnClickListener(this);
		layout.findViewById(R.id.txt_album).setOnClickListener(this);
		layout.findViewById(R.id.txt_collect).setOnClickListener(this);
		layout.findViewById(R.id.txt_money).setOnClickListener(this);
		layout.findViewById(R.id.txt_card).setOnClickListener(this);
		layout.findViewById(R.id.txt_smail).setOnClickListener(this);
		layout.findViewById(R.id.txt_setting).setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			// startActivityForResult(new Intent(getActivity(),
			// ProfileActivity.class),0);
			// Utils.start_Activity(getActivity(), ProfileActivity.class);
			// Utils.start_Activity(getActivity(), MyCodeActivity.class);
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			startActivity(intent);
			break;
		case R.id.txt_album:// 相册
			Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME, getString(R.string.my_posts)));
			break;
		case R.id.txt_collect:// 收藏
			Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME, getString(R.string.collection)));
			break;
		case R.id.txt_money:// 钱包
			Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME, getString(R.string.wallet)));
			break;
		case R.id.txt_card:// 相册
			Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME, getString(R.string.card_bag)));
			break;
		case R.id.txt_smail:// 表情
			Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME, getString(R.string.expression)));
			break;
		case R.id.txt_setting:// 设置
			Utils.start_Activity(getActivity(), SettingActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Toast.makeText(getActivity(), "youfanhuizhi", 0).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}