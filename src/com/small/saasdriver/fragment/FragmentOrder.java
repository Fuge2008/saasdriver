package com.small.saasdriver.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.saascardriver.R;
import com.small.saasdriver.activity.NewsActivity;
import com.small.saasdriver.activity.order.OrderNowActivity;
import com.small.saasdriver.activity.order.OrderPlanActivity;
import com.small.saasdriver.activity.order.OrderRecordActivity;
import com.small.saasdriver.activity.order.OrderStatisticsActivity;
import com.small.saasdriver.view.AutoTextView;

public class FragmentOrder extends BaseFragment {

	private AutoTextView at_notify;// 消息显示
	private LinearLayout ll_now;// 正在进行
	private LinearLayout ll_plan;// 未来计划
	private LinearLayout ll_record;// 历史记录
	private LinearLayout btStatistics;// 数据统计

	final Handler handler = new Handler();
	private static int sCount = 0;// 自定义信息条数
	private List<String> str = new ArrayList<String>();

	@Override
	protected View initView() {// 初始化控件
		View view = View.inflate(mActivity, R.layout.fragment_order, null);
		at_notify = (AutoTextView) view.findViewById(R.id.at_notify);// 消息提示
		ll_now = (LinearLayout) view.findViewById(R.id.ll_now);// 进行中
		ll_plan = (LinearLayout) view.findViewById(R.id.ll_plan);// 未来计划
		ll_record = (LinearLayout) view.findViewById(R.id.ll_record);// 历史记录
		btStatistics = (LinearLayout) view.findViewById(R.id.ll_statistics);// 数据统计
		setListener();
		return view;
	}

	@Override
	protected void initData() {// 垂直滚动,初始化数据
		str.add("今日深圳梅林关发生重大交通事故...");
		str.add("国际油价大幅上涨,油企迎来第二春..");
		str.add("SAAS本季度收益较上季度上涨200%...");
		str.add("最新版司机行车指南出台，老司机必备");
		str.add("特普朗又爆丑闻，美国民众示威游行..");
		str.add("滴滴公司打出宣布永远退出中国市场..");
		sCount = str.size();
		at_notify.setText(str.get(0));
		handler.postDelayed(runnable, 5000);// 启动计时器

	}

	private void setListener() {// 设置监听
		ll_now.setOnClickListener(this);
		ll_plan.setOnClickListener(this);
		ll_record.setOnClickListener(this);
		btStatistics.setOnClickListener(this);
		at_notify.setOnClickListener(this);

	}

	Runnable runnable = new Runnable() {// 定时刷新信息提示栏
		@Override
		public void run() {// 在此处添加执行的代码
			at_notify.next();
			sCount++;
			if (sCount >= Integer.MAX_VALUE) {
				sCount = str.size();
			}
			at_notify.setText(str.get(sCount % (str.size())));
			if (str.size() > 1) {
				handler.postDelayed(this, 5000);// 50是延时时长
			}

		}
	};

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.ll_now:// 进行中
			Intent intent_now = new Intent(getActivity(), OrderNowActivity.class);
			startActivity(intent_now);
			break;
		case R.id.ll_plan:// 未来计划
			Intent intent_plan = new Intent(getActivity(), OrderPlanActivity.class);
			startActivity(intent_plan);
			break;
		case R.id.ll_record:// 历史记录
			Intent intent_record = new Intent(getActivity(), OrderRecordActivity.class);
			startActivity(intent_record);
			break;
		case R.id.ll_statistics:// 数据统计
			Intent intent_statistics = new Intent(getActivity(), OrderStatisticsActivity.class);
			startActivity(intent_statistics);
			break;

		case R.id.at_notify:// 数据统计
			Intent intent_news = new Intent(getActivity(), NewsActivity.class);
			startActivity(intent_news);
			break;
		}
	}

}
