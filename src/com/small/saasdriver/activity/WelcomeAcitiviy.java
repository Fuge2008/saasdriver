package com.small.saasdriver.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.saascardriver.R;

public class WelcomeAcitiviy extends BaseActivity {
	private ImageView mImageView;
	private AnimationDrawable AniDraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		initView();
		setListener();
	}

	private void initView() {
		mImageView = (ImageView) findViewById(R.id.welcome);
	}

	private void setListener() {

		mImageView.setImageResource(R.drawable.loading_animation_list);
		AniDraw = (AnimationDrawable) mImageView.getDrawable();
		AniDraw.start();
	}

	public void doClick(View v) {

	}

}
