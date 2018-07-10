package com.small.saasdriver.activity.order;

import java.util.ArrayList;

import com.example.saascardriver.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.utils.SharedPreferencesUtil;

import android.graphics.Color;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderTotalStatisticsDetail extends BaseActivity {
	private PieChart mChart;
	private BarChart mBarChart;
	public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
	public BarDataSet dataset;
	public ArrayList<String> labels = new ArrayList<String>();
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_total_statictics_detail);
		initView();
		initData();
		setListener();

	}

	private void initView() {
		mChart = (PieChart) findViewById(R.id.charts);// 饼状图
		mBarChart = (BarChart) findViewById(R.id.bar_chart);
		iv_back = (ImageView) findViewById(R.id.iv_back);

	}

	private void initData() {
		PieData mPieData = getPieData(4, 100);
		showCharts(mChart, mPieData);
		initEntriesData();
		initLableData();
		show();

	}

	private void setListener() {
		iv_back.setOnClickListener(this);
	}

	private void showCharts(PieChart pieChart, PieData pieData) {// 饼状图
		pieChart.setHoleColorTransparent(true);
		pieChart.setHoleRadius(60f); // 半径
		pieChart.setTransparentCircleRadius(64f); // 半透明圈
		// pieChart.setHoleRadius(0) //实心圆
		pieChart.setDescription("消费饼状图");
		// mChart.setDrawYValues(true);
		pieChart.setDrawCenterText(true); // 饼状图中间可以添加文字
		pieChart.setDrawHoleEnabled(true);
		pieChart.setRotationAngle(90); // 初始旋转角度
		// draws the corresponding description value into the slice
		// mChart.setDrawXValues(true);
		// enable rotation of the chart by touch
		pieChart.setRotationEnabled(true); // 可以手动旋转
		// display percentage values
		pieChart.setUsePercentValues(true); // 显示成百分比
		// mChart.setUnit(" €");
		// mChart.setDrawUnitsInChart(true);
		// add a selection listener
		// mChart.setOnChartValueSelectedListener(this);
		// mChart.setTouchEnabled(false);
		// mChart.setOnAnimationListener(this);
		pieChart.setCenterText("消费分析"); // 饼状图中间的文字
		pieChart.setData(pieData);// 设置数据
		// undo all highlights
		// pieChart.highlightValues(null);
		// pieChart.invalidate();
		Legend mLegend = pieChart.getLegend(); // 设置比例图
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART); // 最右边显示
		// mLegend.setForm(LegendForm.LINE); //设置比例图的形状，默认是方形
		mLegend.setXEntrySpace(7f);
		mLegend.setYEntrySpace(5f);
		pieChart.animateXY(1000, 1000); // 设置动画
		// mChart.spin(2000, 0, 360);
	}

	private PieData getPieData(int count, float range) {// 分成几部分
		ArrayList<String> xValues = new ArrayList<String>(); // xVals用来表示每个饼块上的内容
		// 饼块上显示成
		xValues.add("加油");
		xValues.add("吃饭");
		xValues.add("修车");
		xValues.add("其他");
		ArrayList<Entry> yValues = new ArrayList<Entry>(); // yVals用来表示封装每个饼块的实际数据
		// 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38 所以 14代表的百分比就是14%
		float quarterly1 = 14;
		float quarterly2 = 14;
		float quarterly3 = 34;
		float quarterly4 = 38;
		yValues.add(new Entry(quarterly1, 0));
		yValues.add(new Entry(quarterly2, 1));
		yValues.add(new Entry(quarterly3, 2));
		yValues.add(new Entry(quarterly4, 3));
		// y轴的集合
		PieDataSet pieDataSet = new PieDataSet(yValues, "2016/12/20统计"/* 显示在比例图上 */);
		pieDataSet.setSliceSpace(0f); // 设置个饼状图之间的距离
		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.rgb(205, 205, 205));// 饼图颜色
		colors.add(Color.rgb(114, 188, 223));
		colors.add(Color.rgb(255, 123, 124));
		colors.add(Color.rgb(57, 135, 200));
		pieDataSet.setColors(colors);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = 5 * (metrics.densityDpi / 160f);
		pieDataSet.setSelectionShift(px); // 选中态多出的长度
		PieData pieData = new PieData(xValues, pieDataSet);
		return pieData;
	}

	public void initEntriesData() {
		entries.add(new BarEntry(4800f, 0));
		entries.add(new BarEntry(8000f, 1));
		entries.add(new BarEntry(6800f, 2));
		entries.add(new BarEntry(12000f, 3));
		entries.add(new BarEntry(8900f, 4));
		entries.add(new BarEntry(9900f, 5));
	}

	public void initLableData() {
		labels.add("六月");
		labels.add("七月");
		labels.add("八月");
		labels.add("九月");
		labels.add("十月月");
		labels.add("十一月");
	}

	public void show() {
		dataset = new BarDataSet(entries, "每月收入");
		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
		BarData data = new BarData(labels, dataset);
		LimitLine line = new LimitLine(10f);
		mBarChart.setData(data);
		// chart.animateXY(5000,5000);
		// chart.animateX(5000);
		mBarChart.animateY(2000);
		mBarChart.setDescription("收入条形图");
	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}

	}

}
