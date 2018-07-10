package com.small.saasdriver.utils;

import com.example.saascardriver.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;

import android.content.Context;

public class SystemUtil {
	
	public static BitmapUtils getBitMapUtils(Context context){
		
		BitmapUtils bu = new BitmapUtils(context);
		bu.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bu.configMemoryCacheEnabled(true);//支持双缓存
		bu.configDiskCacheEnabled(true);
		return bu;
	}
	
	public static HttpUtils getHttpUtils()
	{
		HttpUtils hu = new HttpUtils();
		hu.configResponseTextCharset("UTF-8");//获得服务器应答数据的编码一致！
		return hu;
	}
	
	public static DbUtils getDbUtils(Context context)
	{
		DbUtils db = DbUtils.create(context);
		return db;
	}
}
