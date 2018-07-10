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

public class OtherAdapter extends BaseAdapter {
	private Context context;
	public List<NewsChannelEntity> channelList;
	private TextView item_text;
	/** 是否可见 */
	boolean isVisible = true;
	/** 要删除的position */
	public int remove_position = -1;

	public OtherAdapter(Context context, List<NewsChannelEntity> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public NewsChannelEntity getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView item_text = (TextView) LayoutInflater.from(context).inflate(
				R.layout.news_channel_click1, null);
		NewsChannelEntity channel = getItem(position);
		item_text.setText(channel.news_channel);
		if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
		}
		if (remove_position == position) {
			item_text.setText("");
		}
		return item_text;
	}

	/** 获取频道列表 */
	public List<NewsChannelEntity> getChannnelLst() {
		return channelList;
	}

	/** 添加频道列表 */
	public void addItem(NewsChannelEntity channel) {
		channel.setSelect(false);
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
		// notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}

	/** 设置频道列表 */
	public void setListDate(List<NewsChannelEntity> list) {
		channelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}