//package com.small.saasdriver.receiver;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.xutils.x;
//import org.xutils.common.Callback.CommonCallback;
//import org.xutils.http.RequestParams;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.util.Log;
//import cn.jpush.android.api.JPushInterface;
//
//import com.google.gson.Gson;
//import com.small.saasdriver.activity.DriverLoginActivity;
//import com.small.saasdriver.activity.MessageCenterActivity;
//import com.small.saasdriver.activity.order.OrderPlanActivity;
//import com.small.saasdriver.application.MyApplication;
//import com.small.saasdriver.entity.DriverID;
//import com.small.saasdriver.global.HttpConstant;
//import com.small.saasdriver.utils.SharedPreferencesUtil;
// 
//
///**
// * 自定义接收器
// * 
// * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
// */
//public class MyReceiver extends BroadcastReceiver {
//	private static final String TAG = "JPush";
//	private static final String DTAT_LIST = "datalists";
//	private JSONObject jsonObject;
//	private Map<String, Object> map_notify_messageCenter;
//
//	 
//	
// 
//
//	String message_type;
//	String message_content;
//	String message_title;
//
//	String notify_title;
//	String notify_content;
//	String notify_extra;
//	String nitify_order_id;
//	String nitify_type;
//
//	private SharedPreferencesUtil sp;
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//
//		Bundle bundle = intent.getExtras();
//
//		// map = new HashMap<String, Object>();
//
//		// if
//		// (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//		//
//		// message_title =
//		// bundle.getString(JPushInterface.EXTRA_TITLE);//自定义消息标题
//		//
//		// message_content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		// map.put("content", message_content);
//		// Log.i("消息内容",message_content);
//		// String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		// try {
//		// jsonObject = new JSONObject(extra);
//		// message_type = jsonObject.getString("type");
//		// map.put("type", message_type);
//		// Log.i("消息类型",message_type);
//		// } catch (JSONException e) {
//		// e.printStackTrace();
//		// }
//		// map.put("date", getDate());
//		// //获取本地数据
//		// if(SaasDriverApplication.datalits!=null){
//		// SaasDriverApplication.datalits=SaasDriverApplication.getInstance().getDatalits();
//		// SaasDriverApplication.datalits.add(map);
//		// SaasDriverApplication.getInstance().setDatalits(SaasDriverApplication.datalits);
//		// }else{
//		// SaasDriverApplication.datalits.add(map);
//		// SaasDriverApplication.getInstance().setDatalits(SaasDriverApplication.datalits);
//		//
//		// }
//		//
//		// //发广播通知页面更新
//		// intent = new Intent();
//		// intent.setAction(MessageCenterActivity.Message_Center);
//		// context.sendBroadcast(intent);
//		//
//		// // intent = new Intent();
//		// // intent.setAction(UserCenterActivity.User_Center);
//		// // context.sendBroadcast(intent);
//		// }
//
//		if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//			boolean connected = intent.getBooleanExtra(
//					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//			Log.w(TAG, "[MessageCenterReceiver]" + intent.getAction()
//					+ " connected state change to " + connected);
//		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
//				.getAction())) {
//
//			map_notify_messageCenter = new HashMap<String, Object>();
//
//			notify_content = bundle.getString(JPushInterface.EXTRA_ALERT);
//			// notify_title =
//			// bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//			notify_extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//
//			try {
//				jsonObject = new JSONObject(notify_extra);
//
//				notify_title = jsonObject.getString("title");
//
//				nitify_order_id = jsonObject.getString("orderid");
//
//				nitify_type = jsonObject.getString("type");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		 
//			MyApplication.Identifyt = MyApplication
//					.getInstance().getIdentifyt();
//
//		 
//
//			boolean isAutoLogin = MyApplication.getInstance()
//					.isAtuoLogin();
//
//			if (MyApplication.Identifyt != null) {
//				if (nitify_type.equals("orderPush")) {
//
//					Intent i = new Intent(context, OrderPlanActivity.class);
//					i.putExtras(bundle);
//					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					context.startActivity(i);
//
//				} else {
//					
//					map_notify_messageCenter.put("content",notify_content);
//					map_notify_messageCenter.put("title", notify_title);
//					map_notify_messageCenter.put("date", getDate());
//
//					intent = new Intent();
//					intent.setAction(MessageCenterActivity.Message_Center);
//					context.sendBroadcast(intent);
//
//					if(MyApplication.getDataList(DTAT_LIST)!=null){
//						MyApplication.datalits=MyApplication.getDataList(DTAT_LIST);
//						MyApplication.datalits.add(map_notify_messageCenter);
//						MyApplication.setDataList(DTAT_LIST,MyApplication.datalits);
//					}else{
//						MyApplication.datalits.add(map_notify_messageCenter);
//						MyApplication.setDataList(DTAT_LIST,MyApplication.datalits);
//
//					}
//					
//					
//					
//					
//					
//					Intent i = new Intent(context, MessageCenterActivity.class);
//					i.putExtras(bundle);
//					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					context.startActivity(i);
//				}
//			} else if (isAutoLogin) {
//
//				PackageManager pm = context.getPackageManager();
//
//				Intent intent2 = pm
//						.getLaunchIntentForPackage("com.example.saascardriver");
//
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 只要是调用系统的app，就要加上这个flag,否则会抛出异常。
//
//				context.startActivity(intent2);
//
//			} else {
//
//				Intent i = new Intent(context, DriverLoginActivity.class);
//				i.putExtras(bundle);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(i);
//
//			}
//
//		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
//				.getAction())) {
//
//			notify_content = bundle.getString(JPushInterface.EXTRA_ALERT);
//			// notify_title =
//			// bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//			notify_extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//
//			try {
//				jsonObject = new JSONObject(notify_extra);
//
//				notify_title = jsonObject.getString("title");
//
//				nitify_order_id = jsonObject.getString("orderid");
//
//				nitify_type = jsonObject.getString("type");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			 
//
//			// 保存订单ID
//			getDataFromService(context, nitify_order_id);
//
//		}
//
//	}
//
//	/** 联网获取订单数据 */
//	private void getDataFromService(final Context context, String orderID) {
//		Log.i("极光推送新订单", "联网获取订单开始");
//		RequestParams params = new RequestParams(HttpConstant.ORDER_NEW_URL);
//		DriverID driver = DriverID.getLoginData();
//		params.setCacheMaxAge(1000 * 30);// 设置联网缓存时间:1分钟
//		params.addBodyParameter("DriverID", driver.getDriverID());
//		params.addBodyParameter("Identify", driver.getIdentify());
//		params.addBodyParameter("OrderID", orderID);
//		params.addBodyParameter("CurrentOrHistorical", "0");
//		x.http().post(params, new CommonCallback<String>() {
//
//			@Override
//			public void onCancelled(CancelledException arg0) {
//				// TODO Auto-generated method stub
//				Log.i("极光推送新订单", "接收新订单取消:" + arg0.toString());
//
//			}
//
//			@Override
//			public void onError(Throwable arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				Log.e("极光推送新订单", "接收新订单失败：" + arg0);
//
//			}
//
//			@Override
//			public void onFinished() {
//				// TODO Auto-generated method stub
//				Log.i("极光推送新订单", "接收新订单完成");
//
//			}
//
//			@Override
//			public void onSuccess(String result) {
//				// TODO Auto-generated method stub
//				Log.i("极光推送新订单", "接收新订单成功:" + result.toString());
//				try {
//					JSONObject json = new JSONObject(result);
//					int code = json.getInt("ErrCode");
//					if (1 == code) {
////						Gson gson = new Gson();
////						NewOrderList list = gson.fromJson(result,
////								NewOrderList.class);
////
////						Log.i("极光推送新订单", "接收新订单成功,订单ID="
////								+ list.getData().get(0).getOrderID());
////						Log.i("极光推送新订单", "接收新订单成功,code=" + code);
////						Log.i("极光推送新订单", "接收新订单成功,订单ID="
////								+ list.getData().size());
////						DBUtil.addOrderDataToDB(context,list.getData().get(0));
//					}
//
//				} catch (JSONException e) {
//					Log.i("极光推送新订单", "存值失败");
//					e.printStackTrace();
//				}
//
//			}
//		});
//	}
//
//	// 获取接收到推送时的系统时间
//	private String getDate() {
//		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar
//				.getInstance().getTime());
//	}
//
//}
