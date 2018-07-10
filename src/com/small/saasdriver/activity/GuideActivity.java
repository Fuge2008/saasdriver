package com.small.saasdriver.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.saascardriver.R;
import com.small.saasdriver.adapter.ViewPagerAdapter;
import com.small.saasdriver.utils.SharePreferenceUtils;
import com.small.saasdriver.utils.SharedPreferencesUtil;

public class GuideActivity extends BaseActivity {
	private android.support.v4.view.ViewPager ViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	// 引导图片资源
	private static final int[] pics = { R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3,
			R.drawable.guide_4 };
	private List<View> pageList;
	private Bitmap b1;
	private Bitmap b2;
	private Bitmap b3;
	private Bitmap b4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		if (SharePreferenceUtils.getString(GuideActivity.this, "isfristlogin", "false").equals("ture")) {

			Intent intent = new Intent(GuideActivity.this, DriverLoginActivity.class);

			startActivity(intent);
			this.finish();
		}
		initView();
		setListener();
		getdata();
	}

	private void getdata() {

	}

	private void setListener() {
		initViewPager();
		mViewPagerAdapter = new ViewPagerAdapter(this, pageList);
		ViewPager.setAdapter(mViewPagerAdapter);

	}

	private void initView() {
		ViewPager = (android.support.v4.view.ViewPager) findViewById(R.id.viewpager);
	}

	private void initViewPager() {
		pageList = new ArrayList<View>();
		ImageView image1 = new ImageView(this);
		image1.setScaleType(ScaleType.FIT_XY);

		b1 = BitmapFactory.decodeResource(getResources(), R.drawable.guide_1);
		image1.setImageBitmap(b1);

		ImageView image2 = new ImageView(this);
		image2.setScaleType(ScaleType.FIT_XY);

		b2 = BitmapFactory.decodeResource(getResources(), R.drawable.guide_2);
		image2.setImageBitmap(b2);

		ImageView image3 = new ImageView(this);
		image3.setScaleType(ScaleType.FIT_XY);

		b3 = BitmapFactory.decodeResource(getResources(), R.drawable.guide_3);
		image3.setImageBitmap(b3);

		View page3 = View.inflate(this, R.layout.start_3, null);
		ImageView guidance_page3_bg = (ImageView) page3.findViewById(R.id.guidance_page3_bg);
		guidance_page3_bg.setScaleType(ScaleType.FIT_XY);

		b4 = BitmapFactory.decodeResource(getResources(), R.drawable.guide_4);
		guidance_page3_bg.setImageBitmap(b4);

		Button btn = (Button) page3.findViewById(R.id.start_btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharePreferenceUtils.putString(GuideActivity.this, "isfristlogin", "ture");
				Intent intent = new Intent(GuideActivity.this, DriverLoginActivity.class);
				startActivity(intent);
				GuideActivity.this.finish();
			}
		});
		pageList.add(image1);
		pageList.add(image2);
		pageList.add(image3);
		pageList.add(page3);
	}

	@Override
	protected void onDestroy() {
		try {

			b1.recycle();
			b2.recycle();
			b3.recycle();
			b4.recycle();
			b1 = null;
			b2 = null;
			b3 = null;
			b4 = null;

		} catch (Exception e) {
		}
		super.onDestroy();
	}

	public void doClick(View v) {
		// TODO Auto-generated method stub

	}
}
