package com.small.saasdriver.view.stepsView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.example.saascardriver.R;

/**
 * 订单主流程fragment
 * 
 * @author admin
 *
 */
public class HorizontalStepviewFragment extends Fragment {
	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = View.inflate(container.getContext(), R.layout.fragment_horizontal_stepview, null);
		showSetpView0();
		return mView;
	}

	private void showSetpView0() {
		HorizontalStepView setpview = (HorizontalStepView) mView.findViewById(R.id.step_view0);
		List<StepBean> stepsBeanList = new ArrayList<StepBean>();
		StepBean stepBean0 = new StepBean("11:35", "新订单", 1);
		StepBean stepBean1 = new StepBean("11:40", "已接单", 1);
		StepBean stepBean2 = new StepBean("11:58", "进行中",0);//订单流程处于哪一个环节中
		StepBean stepBean3 = new StepBean(" ", "已结束", -1);
		StepBean stepBean4 = new StepBean(" ", "已完成", -1);
		StepBean stepBean5 = new StepBean(" ", "已评价", -1);

		stepsBeanList.add(stepBean0);
		stepsBeanList.add(stepBean1);
		stepsBeanList.add(stepBean2);
		stepsBeanList.add(stepBean3);
		stepsBeanList.add(stepBean4);
		stepsBeanList.add(stepBean5);

		setpview.setStepViewTexts(stepsBeanList).setTextSize(16)// set textSize
				.setStepsViewIndicatorCompletedLineColor(
						ContextCompat.getColor(getActivity(), R.color.completed_flag))// 设置StepsViewIndicator完成线的颜色
				.setStepsViewIndicatorUnCompletedLineColor(
						ContextCompat.getColor(getActivity(), R.color.uncompleted_flag))// 设置StepsViewIndicator未完成线的颜色
				.setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.completed_flag))// 设置StepsView
																												// text完成线的颜色
				.setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_flag))// 设置StepsView
																														// text未完成线的颜色
				.setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))// 设置StepsViewIndicator
																													// CompleteIcon
				.setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))// 设置StepsViewIndicator
																													// DefaultIcon
				.setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));// 设置StepsViewIndicator
																													// AttentionIcon
	}

}
