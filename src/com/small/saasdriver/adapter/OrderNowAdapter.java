package com.small.saasdriver.adapter;

import java.util.List;

import com.example.saascardriver.R;
import com.small.saasdriver.adapter.base.BaseViewHolder;
import com.small.saasdriver.adapter.base.MyBaseAdapter;
import com.small.saasdriver.entity.order.OrderNowEntity.Data;
import com.small.saasdriver.utils.StringUtils;

import android.content.Context;
import android.util.Log;

/**
 * 订单记录适配器
 */
public class OrderNowAdapter extends MyBaseAdapter<Data> {

	public OrderNowAdapter(Context context, int resource, List<Data> list) {
		super(context, resource, list);
	}

	@Override
	public void setConvert(BaseViewHolder viewHolder, Data data) {
		if (StringUtils.isEmpty(data.Destination)) {
			data.Destination = "未知";
			viewHolder.setTextView(R.id.tv_end, data.Destination);
		} else {
			viewHolder.setTextView(R.id.tv_end, data.Destination);
		}
		if (StringUtils.isEmpty(data.UseVehicleDate)) {
			data.UseVehicleDate = "未知";
			viewHolder.setTextView(R.id.tv_date, data.UseVehicleDate);
		} else {
			viewHolder.setTextView(R.id.tv_date, data.UseVehicleDate);
		}
		if (StringUtils.isEmpty(data.Origin)) {
			data.Origin = "未知";
			viewHolder.setTextView(R.id.tv_start, data.Origin);
		} else {
			viewHolder.setTextView(R.id.tv_start, data.Origin);
		}

		data.OrderState = "正进行";
		viewHolder.setTextView(R.id.tv_statue, data.OrderState);

	}

}