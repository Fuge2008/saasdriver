package com.small.saasdriver.fragment;

import org.xutils.x;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.saascardriver.R;
import com.saas.chat.SplashActivity;
import com.small.saasdriver.activity.PersonalInfoAcitivity;
import com.small.saasdriver.activity.VehicleCenterAcitvity;
import com.small.saasdriver.activity.map.POIActivity;

public class FragmentDriver extends BaseFragment {
	private LinearLayout search_poi, Personal_information, Vehicle_information, message_center;

	private Intent inent;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_driver, null);
		Personal_information = (LinearLayout) view.findViewById(R.id.Personal_information);
		Vehicle_information = (LinearLayout) view.findViewById(R.id.Vehicle_information);
		search_poi = (LinearLayout) view.findViewById(R.id.search_poi);
		message_center = (LinearLayout) view.findViewById(R.id.message_center);
		setListener();
		return view;
	}

	private void setListener() {

		Personal_information.setOnClickListener(this);
		search_poi.setOnClickListener(this);
		Vehicle_information.setOnClickListener(this);
		message_center.setOnClickListener(this);

	}

	public void doClick(View v) {
		switch (v.getId()) {

		case R.id.Personal_information:
			inent = new Intent(mActivity, PersonalInfoAcitivity.class);
			startActivity(inent);
			break;
		case R.id.Vehicle_information:
			inent = new Intent(mActivity, VehicleCenterAcitvity.class);
			startActivity(inent);
			break;

		case R.id.message_center:

			inent = new Intent(mActivity, SplashActivity.class);
			startActivity(inent);

			break;
		case R.id.search_poi:

			inent = new Intent(mActivity, POIActivity.class);
			startActivity(inent);

			break;

		}
	}
}
