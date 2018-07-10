package com.small.saasdriver.adapter;

import java.util.List;

import org.xutils.x;

import com.example.saascardriver.R;
import com.small.saasdriver.entity.NewEntity;
import com.small.saasdriver.utils.bitmap.MyBitmapUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	private Context context;
	private List<NewEntity> data;
	private LayoutInflater inflater;

	public NewsAdapter() {
		super();
	}

	public NewsAdapter(Context context, List<NewEntity> data) {
		super();
		this.context = context;
		this.data = data;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public NewEntity getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_news, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.source = (TextView) convertView.findViewById(R.id.tv_source);
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.picture = (ImageView) convertView.findViewById(R.id.iv_picture);

			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		// 把当前emp对象的属性赋值给相应的控件
		NewEntity emp = getItem(position);
		holder.title.setText(emp.getTitle());
		holder.source.setText(emp.getAuthor_name());
		holder.time.setText(emp.getDate());
		loadImageView(holder.picture, emp.getThumbnail_pic_s());//图片不拉伸模式
		//x.image().bind(holder.picture, emp.getThumbnail_pic_s());//图片拉伸模式
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView source;
		TextView time;
		ImageView picture;
	}

	private void loadImageView(ImageView ivPic, String url) {
		MyBitmapUtils net = new MyBitmapUtils();
		net.display(ivPic, url);
	}

}
