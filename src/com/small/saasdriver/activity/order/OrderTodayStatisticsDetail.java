package com.small.saasdriver.activity.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.example.saascardriver.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.small.saasdriver.activity.BaseActivity;
import com.small.saasdriver.utils.DateUtil;
import com.small.saasdriver.utils.SharedPreferencesUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderTodayStatisticsDetail extends BaseActivity {
	private LineChart mLineChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_today_statictics_detail);
		initView();

		showLine();

	}

	private void initView() {
		mLineChart = (LineChart) findViewById(R.id.chart);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
	}

	private void showLine() {// 折线图设置
		XAxis xAxis = mLineChart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置X轴的文字在底部
		mLineChart.setDescription("15天走势图");// 设置描述文字
		List<String> xValues = new ArrayList<String>();// 模拟一个x轴的数据
		Long current = System.currentTimeMillis();
		for (int a = 14; a >= 0; a--) {
			Long now = current - (a * 24 * 3600000);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(now);
			String month = formatter.format(calendar.getTime()).substring(5, 7);
			String day = formatter.format(calendar.getTime()).substring(8, 10);
			xValues.add(month + "/" + day);
		}
		Log.i("info", xValues.size() + "");
		// 模拟一组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一
		ArrayList<Entry> yValue = new ArrayList<>();
		yValue.add(new Entry(90, 0));
		yValue.add(new Entry(200, 1));
		yValue.add(new Entry(40, 2));
		yValue.add(new Entry(69, 3));
		yValue.add(new Entry(57, 4));
		yValue.add(new Entry(20, 5));
		yValue.add(new Entry(73, 6));
		yValue.add(new Entry(25, 7));
		yValue.add(new Entry(69, 8));
		yValue.add(new Entry(32, 9));
		yValue.add(new Entry(120, 10));
		yValue.add(new Entry(69, 11));
		yValue.add(new Entry(69, 12));
		yValue.add(new Entry(180, 13));
		yValue.add(new Entry(120, 14));
		LineDataSet dataSet = new LineDataSet(yValue, "收入曲线/元");// 构建一个LineDataSet
																// 代表一组Y轴数据
		// 模拟第二组组y轴数据(存放y轴数据的是一个Entry的ArrayList) 他是构建LineDataSet的参数之一
		ArrayList<Entry> yValue1 = new ArrayList<>();
		yValue1.add(new Entry(70, 0));
		yValue1.add(new Entry(170, 1));
		yValue1.add(new Entry(30, 2));
		yValue1.add(new Entry(50, 3));
		yValue1.add(new Entry(40, 4));
		yValue1.add(new Entry(30, 5));
		yValue1.add(new Entry(70, 6));
		yValue1.add(new Entry(30, 7));
		yValue1.add(new Entry(50, 8));
		yValue1.add(new Entry(40, 9));
		yValue1.add(new Entry(100, 10));
		yValue1.add(new Entry(70, 11));
		yValue1.add(new Entry(50, 12));
		yValue1.add(new Entry(230, 13));
		yValue1.add(new Entry(100, 14));
		Log.e("wing", yValue.size() + "");
		// 构建一个LineDataSet 代表一组Y轴数据 （比如不同的彩票： 七星彩 双色球）
		LineDataSet dataSet1 = new LineDataSet(yValue1, "里程曲线/km");
		dataSet1.setColor(Color.BLACK);// 设置颜色
		// 构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet
		// 他是构建最终加入LineChart数据集所需要的参数
		ArrayList<LineDataSet> dataSets = new ArrayList<>();
		// 将数据加入dataSets
		dataSets.add(dataSet);
		dataSets.add(dataSet1);
		// 构建一个LineData 将dataSets放入
		LineData lineData = new LineData(xValues, dataSets);
		mLineChart.setGridBackgroundColor(Color.GREEN);
		mLineChart.setNoDataTextDescription("You need to provide data for the chart.");
		mLineChart.setDrawGridBackground(false); // 是否显示表格颜色
		mLineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
		mLineChart.setTouchEnabled(true); // 设置是否可以触摸
		mLineChart.setDragEnabled(true);// 是否可以拖拽
		mLineChart.setScaleEnabled(true);// 是否可以缩放
		mLineChart.setPinchZoom(false);//
		mLineChart.setBackgroundColor(Color.rgb(114, 188, 223));// 设置背景
		Legend mLegend = mLineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.WHITE);// 颜色
		mLegend.setXEntrySpace(7f);
		mLegend.setYEntrySpace(5f);
		mLineChart.animateX(2500); // 立即执行的动画,x轴
		mLineChart.setData(lineData);// 将数据插入

	}

	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}

	}

}
