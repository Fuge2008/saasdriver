package com.small.saasdriver.adapter;

import java.util.List;

import com.example.saascardriver.R;
import com.small.saasdriver.entity.NewsChannelEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsButtonAdapter extends BaseAdapter {
	private Context context;
	private List<NewsChannelEntity> NewsChannelEntitys;
	private LayoutInflater inflater;

	public NewsButtonAdapter(Context context, List<NewsChannelEntity> NewsChannelEntitys) {
		this.context = context;
		this.NewsChannelEntitys = NewsChannelEntitys;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return NewsChannelEntitys.size();
	}

	@Override
	public NewsChannelEntity getItem(int i) {
		return NewsChannelEntitys.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.item_button_news, null);
			holder = new ViewHolder();
			holder.item_news_button = (TextView) view.findViewById(R.id.item_news_button);
			view.setTag(holder);
		}
		holder = (ViewHolder) view.getTag();
		NewsChannelEntity NewsChannelEntity = getItem(i);
		holder.item_news_button.setText(NewsChannelEntity.news_channel);
		return view;
	}

	class ViewHolder {
		TextView item_news_button;
	}

}
