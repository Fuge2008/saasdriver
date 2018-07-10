package com.small.saasdriver.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
/**
 * 订单接收界面ListView适配器基类
 * 
 * @author ZhangKui
 * @Data 2016-09-27
 */
public abstract class OrderReceiveBaseAdapter<T> extends BaseAdapter{

	private Context context;
	private  List<T> data;
	private LayoutInflater inflater;
	
	public OrderReceiveBaseAdapter(Context context, List<T> data) {
		super();
		setContext(context);
		setData(data);
		setInflater();
	}
	
	public LayoutInflater getInflater() {
		return inflater;
	}
	public Context getContext() {
		return context;
	}
	public List<T> getData() {
		return data;
	}
	
	public void setContext(Context context){
		if (context==null) {
			throw new IllegalArgumentException("context不能为null");
		}else{
			this.context=context;
		}
	}
	public void setData(List<T> data){
		if (data==null) {
			data=new ArrayList<T>();
		}else{
			this.data=data;
		}
	}
	public void setInflater(){
		inflater=LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
