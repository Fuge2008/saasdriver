package com.small.saasdriver.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类Fragment, 所有Fragment继承此类
 * 
 * 1. 定义Activity常量,方便子类使用 2. 定义抽象方法initViews,初始化布局,必须实现
 * 3.定义方法initData,初始化数据,可以不实现
 */

public abstract class BaseFragment extends Fragment implements android.view.View.OnClickListener {
	public Activity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {// Fragment创建
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {// Fragment填充布局
		View view = initView();
		initData();
		return view;
	}

	/**
	 * 初始化数据，子类可以不是实现
	 */
	protected void initData() {

	}

	/**
	 * 初始化控件，子类必须实现
	 */
	protected abstract View initView();

	@Override
	public final void onClick(View v) {
		doClick(v);

	}

	public void doClick(View v) {

	}

}
