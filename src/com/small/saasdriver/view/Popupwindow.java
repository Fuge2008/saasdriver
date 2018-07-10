package com.small.saasdriver.view;



import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.saascardriver.R;

/**
 * @author Mr Qian
 * @since 2015-9-10 下午3:28:27
 */
public class Popupwindow extends PopupWindow implements OnClickListener, OnTouchListener {

	private View mMenuView;
	private Button scene_camera_btn, scene_photo_btns, cancel_btns;

	@SuppressLint("InflateParams")
	public Popupwindow(Context context, OnClickListener onClickListener) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popupwindow_driver, null);
		
		Button mCamera = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		Button mPhoto = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		Button mCancle = (Button) mMenuView.findViewById(R.id.btn_cancel);
		mCamera.setOnClickListener(onClickListener);
		mCancle.setOnClickListener(this);
		mPhoto.setOnClickListener(onClickListener);
		
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setAnimationStyle(R.style.popwin_anim_style);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		mMenuView.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel:
			dismiss();
			break;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP) {
			dismiss();
		}
		return true;
	}
}
