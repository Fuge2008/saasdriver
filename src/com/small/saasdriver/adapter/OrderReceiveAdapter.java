package com.small.saasdriver.adapter;
//package com.small.saasdriver.order.receive.adapter;
//
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.saascardriver.R;
//import com.small.saasdriver.db.table.OrderDataTable;
//
///**
// * 订单接收界面ListView适配器
// * 
// * @author ZhangKui
// * @Data 2016-09-27
// */
//@SuppressLint("InflateParams")
//public class OrderReceiveAdapter extends
//		OrderReceiveBaseAdapter<OrderDataTable> {
//
//	public OrderReceiveAdapter(Context context, List<OrderDataTable> data) {
//		super(context, data);
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//
//		if (convertView == null) {
//			convertView = getInflater().inflate(R.layout.item_order_receive,
//					null);
//			holder = new ViewHolder();
//			// 加载布局中的控件
//			holder = findViewById(holder, convertView);
//			convertView.setTag(holder);
//		}
//		holder = (ViewHolder) convertView.getTag();
//		OrderDataTable orderData = getData().get(position);
//		holder.typeView.setText(orderData.getOrderID() + "");// 订单类型
//		holder.distanceView.setText("政企用户");// 用户类型
//		holder.numberView.setText((orderData.getAdditionalPersonNum() + 1)
//				+ "人");// 用车人数
//		holder.startView.setText(orderData.getOrigin());// 起点
//		holder.endView.setText(orderData.getDestination());// 终点
//
////		 int cost = Integer.parseInt(orderData.getActualCost());// 订单总价
////		 int discount = Integer.parseInt(orderData.getActualDiscount());//
////		 //订单折扣价
////		 int fee = cost - discount;// 客户实际要付的费用
////		 holder.priceView.setText(fee + "");
//
//		return convertView;
//	}
//
//	class ViewHolder {
//		TextView typeView;
//		TextView distanceView;
//		TextView numberView;
//		TextView startView;
//		TextView endView;
//		TextView priceView;
//	}
//
//	/** 加载布局中的控件 */
//	ViewHolder findViewById(ViewHolder holder, View convertView) {
//		holder.typeView = (TextView) convertView
//				.findViewById(R.id.order_receive_using_type);
//		holder.distanceView = (TextView) convertView
//				.findViewById(R.id.order_receive_using_mileage);
//		holder.numberView = (TextView) convertView
//				.findViewById(R.id.order_receive_using_number);
//		holder.startView = (TextView) convertView
//				.findViewById(R.id.order_receive_using_start_point);
//		holder.endView = (TextView) convertView
//				.findViewById(R.id.order_receive_using_end_point);
//		holder.priceView = (TextView) convertView
//				.findViewById(R.id.order_receive_using_order_price);
//
//		return holder;
//	}
//
//}
