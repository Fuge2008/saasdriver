package com.small.saasdriver.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.util.verify.BNKeyVerifyListener;
import com.example.saascardriver.R;
import com.small.saasdriver.fragment.FragmentDriver;
import com.small.saasdriver.fragment.FragmentOrder;
import com.small.saasdriver.fragment.FragmentSettings;
import com.small.saasdriver.view.NoScrollViewPager;
import com.small.saasdriver.view.stepsView.HorizontalStepviewFragment;

public class MainActivity extends BaseActivity implements OnCheckedChangeListener {

	private RadioGroup rg_menu;
	private TextView tvTitle;
	private RadioButton rb_order, rb_center, rb_settings;
	private NoScrollViewPager nvp_fragment;
	private FrameLayout flStepsView;
	List<Fragment> list = null;
	public boolean mIsEngineInitSuccess = false;// 导航初始化引擎

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

	}

	public void initViews() {
		BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(), mNaviEngineInitListener,
				new LBSAuthManagerListener() {// 初始化导航引擎
					@Override
					public void onAuthResult(int status, String msg) {
						String str = null;
						if (0 == status) {
							str = "key校验成功!";
						} else {
							str = "key校验失败, " + msg;
						}

					}
				});
		tvTitle = (TextView) findViewById(R.id.activity_main_title_tv);
		rg_menu = (RadioGroup) findViewById(R.id.rg_menu);
		rb_order = (RadioButton) findViewById(R.id.rb_order);
		rb_center = (RadioButton) findViewById(R.id.rb_center);
		rb_settings = (RadioButton) findViewById(R.id.rb_settings);
		flStepsView = (FrameLayout) findViewById(R.id.steps_container);

		nvp_fragment = (NoScrollViewPager) findViewById(R.id.nvp_fragment);
		list = new ArrayList<Fragment>();
		FragmentOrder order = new FragmentOrder();
		FragmentDriver driver = new FragmentDriver();
		FragmentSettings setting = new FragmentSettings();
		list.add(order);
		list.add(driver);
		list.add(setting);
		MenuAdapter adapter = new MenuAdapter(getSupportFragmentManager(), list);
		nvp_fragment.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		rg_menu.setOnCheckedChangeListener(this);
		rb_order.setChecked(true);
		showStepsView();
	}

	class MenuAdapter extends FragmentStatePagerAdapter {

		List<Fragment> list;

		public MenuAdapter(FragmentManager fm, List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {

			return list.size();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		if (checkedId == rb_order.getId()) {
			nvp_fragment.setCurrentItem(0);
			flStepsView.setVisibility(View.VISIBLE);
			showStepsView();
		} else if (checkedId == rb_center.getId()) {
			nvp_fragment.setCurrentItem(1);
			flStepsView.setVisibility(View.GONE);

		} else if (checkedId == rb_settings.getId()) {
			nvp_fragment.setCurrentItem(2);
			flStepsView.setVisibility(View.GONE);
		}
	}

	private void showStepsView() {
		HorizontalStepviewFragment mHorizontalStepViewFragment = new HorizontalStepviewFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.steps_container, mHorizontalStepViewFragment)
				.commit();

	}

	private String getSdcardDir() {// 获取sd卡路径
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	public NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {// 导航引擎初始化监听器

		public void engineInitSuccess() {// 引擎初始化成功
			mIsEngineInitSuccess = true;
		}

		public void engineInitStart() {// 引擎开始初始化
		}

		public void engineInitFail() {// 引擎初始化失败
		}
	};

	private BNKeyVerifyListener mKeyVerifyListener = new BNKeyVerifyListener() {// 校验APP-KEY监听器
		@Override
		public void onVerifySucc() {
		}

		@Override
		public void onVerifyFailed(int arg0, String arg1) {
		}
	};

	private static boolean isExit = false;// 定义是否已退出应用

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			isExit = false;
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {// 返回事件
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {// 双击返回键退出应用
		if (!isExit) {
			isExit = true;
			Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
			// 返回键双击延迟
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myUid());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void doClick(View v) {

	}

}
