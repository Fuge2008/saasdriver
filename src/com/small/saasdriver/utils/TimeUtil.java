package com.small.saasdriver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

/**
 * 时间管理类
 */
public class TimeUtil {

	/**
	 * 获取系统当前时间
	 * 
	 * @param formate
	 *            时间显示格式：yyyy年MM月dd日 HH:mm:ss
	 * @return String
	 */
	public static String getCurrentTimeToString(String formate) {
		SimpleDateFormat formater = new SimpleDateFormat(formate, Locale.CHINA);
		Date curDate = new Date();
		Log.d("手机系统当前时间", "毫秒：" + System.currentTimeMillis() + "\n时间："
				+ formater.format(curDate));

		return formater.format(curDate);
	}

	/**
	 * 将指定的毫秒值转化成指定的时间字符串
	 * 
	 * @param formate
	 *            时间显示格式：yyyy年MM月dd日 HH:mm:ss
	 * @param millis
	 *            要转化的毫秒值
	 * @return String
	 */
	public static String getTimeToString(String formate, long millis) {
		SimpleDateFormat formatter = new SimpleDateFormat(formate, Locale.CHINA);
		Date curDate = new Date(millis);
		return formatter.format(curDate);
	}

	/**
	 * 将秒转换成时分秒显示
	 * 
	 * @param time
	 *            要转换的时间，单位：秒
	 * @return String
	 */
	public static String times(int time) {
		String times = null;
		int h = time / 3600;// 小时
		time = time - h * 3600;
		int m = time / 60;// 分钟
		time = time - m * 60;
		int s = time;
		if (h > 0) {
			times = h + " 小时";
		}
		if (m > 0) {
			if (times == null) {
				times = m + "分钟";
			} else {
				times = times + m + "分钟";
			}
		}
		if (times == null) {
			times = "无";
		}

		return times;
	}

}
