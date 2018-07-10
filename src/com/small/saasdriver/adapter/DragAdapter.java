package com.small.saasdriver.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.saascardriver.R;
import com.small.saasdriver.entity.NewsChannelEntity;

import android.R.array;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DragAdapter extends BaseAdapter {
	/** TAG */
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<NewsChannelEntity> channelList;
	/** 要删除的position */
	public int remove_position = -1;

	public DragAdapter(Context context, List<NewsChannelEntity> channelList) {
		this.context = context;
		this.channelList = channelList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public NewsChannelEntity getItem(int position) {
		// TODO Auto-generated method stub
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.news_channel_click1, null);
		NewsChannelEntity channel = getItem(position);
		view.setText(channel.news_channel);
		if ((position == 0) || (position == 1)) {
			// item_text.setTextColor(context.getResources().getColor(R.color.black));
			view.setEnabled(false);
		}
		if (isChanged && (position == holdPosition) && !isItemShow) {
			view.setText("");
			view.setSelected(true);
			view.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {
			view.setText("");
			view.setSelected(true);
			view.setEnabled(true);
		}
		if (remove_position == position) {
			view.setText("");
		}
		return view;
	}

	/** 添加频道列表 */
	public void addItem(NewsChannelEntity channel) {
		channel.setSelect(true);
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		NewsChannelEntity dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}

	/** 获取频道列表 */
	public List<NewsChannelEntity> getChannnelLst() {
		return channelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
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

	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}

}