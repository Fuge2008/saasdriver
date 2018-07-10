package com.small.saasdriver.adapter;

import java.util.List;
import java.util.Map;

 


import com.example.saascardriver.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private List<Map<String, Object>> data;
	private LayoutInflater mInflater;
	Context context;

	public MessageAdapter(Context context, List<Map<String, Object>> data) {
		this.data = data;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.msg_item,null);

			holder = new ViewHolder();
			holder.tv_msg_title = (TextView) convertView
					.findViewById(R.id.tv_msg_title);
			holder.tv_msg_content = (TextView) convertView
					.findViewById(R.id.tv_msg_content);
			holder.tv_msg_date = (TextView) convertView
					.findViewById(R.id.tv_msg_date);
			holder.iv_msg = (ImageView) convertView.findViewById(R.id.iv_msg);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		Map<String, Object> map = data.get(position);
		String content = map.get("content").toString();
		String type = map.get("type").toString();
		String date = map.get("date").toString();
		
		if (type.equals("积分动态")) {

			holder.tv_msg_title.setText("积分动态");
			holder.iv_msg.setImageResource(R.drawable.taxi);
		} else if (type.equals("促销提醒")) {
			holder.tv_msg_title.setText("促销提醒");
			holder.iv_msg.setImageResource(R.drawable.taxi);
		} else if (type.equals("发货通知")) {
			holder.tv_msg_title.setText("发货通知");
			holder.iv_msg.setImageResource(R.drawable.taxi);
		} else if (type.equals("退款通知")) {
			holder.tv_msg_title.setText("退款通知");
			holder.iv_msg.setImageResource(R.drawable.taxi);
		} else if (type.equals("团购预告")) {
			holder.tv_msg_title.setText("团购预告");
			holder.iv_msg.setImageResource(R.drawable.taxi);
		} else if (type.equals("生日礼品信息")) {
			holder.tv_msg_title.setText("生日礼品信息");
			holder.iv_msg.setImageResource(R.drawable.taxi);
		}

		holder.tv_msg_content.setText(content);
		holder.tv_msg_date.setText(date);

		return convertView;
	}

	class ViewHolder {

		public TextView tv_msg_title, tv_msg_content, tv_msg_date;
		public ImageView iv_msg;

	}

}
