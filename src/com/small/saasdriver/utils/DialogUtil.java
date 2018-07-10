package com.small.saasdriver.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.example.saascardriver.R;

/**
 * 对话框工具类
 * 
 * @author ZhangKui
 * @Data 2016-09-27
 */
public class DialogUtil {

	/**
	 * 系统单选列表对话框
	 * 
	 * @param context
	 *            上下文
	 * @param IconId
	 *            标题栏图标
	 * @param title
	 *            标题
	 * @param str
	 *            列表内容
	 * @param listener
	 *            回调
	 */
	public static void createDialog(Context context, int IconId, String title,
			String[] str, OnClickListener listener) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 设置图片
		builder.setIcon(IconId);
		// 设置标题
		builder.setTitle(title);
		// 设置单选
		builder.setSingleChoiceItems(str, -1, listener);
		// 显示
		builder.show();

	}

	/**
	 * 系统单选按钮普通对话框
	 * 
	 * @param context
	 *            上下文
	 * @param IconId
	 *            标题图标
	 * @param title
	 *            标题
	 * @param message
	 *            要显示的内容
	 * @param ensure
	 *            确定按钮显示的内容
	 * @param cancel
	 *            取消按钮显示的内容
	 * @param listener
	 *            回调
	 */
	public static void creaddanDialog(Context context, String title,
			String message, String ensure) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);

		// 确定
		builder.setPositiveButton(ensure, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		// 显示
		builder.show();

	}

	/**
	 * 创建普通单按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title,
			String message, String btnName, View.OnClickListener listener) {

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_alter, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		ImageView img_log = (ImageView) view.findViewById(R.id.img_log);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_title.setText(title);
		tv_msg.setText(message);
		img_log.setImageResource(iconId);
		btn_cancel.setText(btnName);
		btn_cancel.setOnClickListener(listener);

		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;

	}

	/**
	 * 创建自定义双选按钮对话框
	 * 
	 * @param ctx
	 *            上下文
	 * @param iconId
	 *            图标id
	 * @param titleMsg
	 *            标题
	 * @param msg
	 *            提示信息
	 * @param btnSureName
	 *            确认按钮显示内容
	 * @param btnCancelName
	 *            取消按钮显示内容
	 * @param listener
	 *            确认按钮回调
	 * @return Dialog实例
	 */
	public static Dialog createDialog(Context ctx, int iconId, String titleMsg,
			String msg, String btnSureName, String btnCancelName,
			View.OnClickListener listener) {

		final Dialog dialog = new Dialog(ctx, R.style.loading_dialog);
		View dialogView = View
				.inflate(ctx, R.layout.dialog_double_button, null);
		ImageView icon = (ImageView) dialogView
				.findViewById(R.id.dialog_double_button_icon);
		TextView title = (TextView) dialogView
				.findViewById(R.id.dialog_double_button_title);
		TextView message = (TextView) dialogView
				.findViewById(R.id.dialog_double_button_msg);
		Button cancel = (Button) dialogView
				.findViewById(R.id.dialog_double_button_cancel);
		Button ensure = (Button) dialogView
				.findViewById(R.id.dialog_double_button_ensure);

		if (iconId != 0) {
			icon.setImageResource(iconId);
		}
		if (titleMsg != null) {
			title.setText(titleMsg);
		}
		if (msg != null) {
			message.setText(msg);
		}
		if (btnSureName != null) {
			ensure.setText(btnSureName);
		}
		if (btnCancelName != null) {
			cancel.setText(btnCancelName);
		}

		ensure.setOnClickListener(listener);
		cancel.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});

		dialog.setContentView(dialogView, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		return dialog;

	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_launcher)
				//
				.setTitle(R.string.app_name)
				//
				.setMessage("当前无网络")
				.setPositiveButton("设置", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// 跳转到系统的网络设置界面
						dialog.dismiss();
						Intent intent = new Intent(
								Settings.ACTION_WIFI_SETTINGS);
						context.startActivity(intent);

					}
				}).setNegativeButton("知道了", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 *            要提示的内容
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Dialog createLoadingDialog(Context context, String msg) {
		// RuntimeException: Can't create handler inside thread that has not
		// called Looper.prepare()

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.loading_dialog);// 加载布局
		// main.xml中的ImageView
		ImageView imageView = (ImageView) v.findViewById(R.id.loading_img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);
		// 加载帧动画
		imageView.setImageResource(R.drawable.loading_animation_list);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getDrawable();
		animationDrawable.start();

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

		return loadingDialog;
	}

	/**
	 * 得到自定义的系统提示Dialog
	 * 
	 * @param context
	 * @param msg
	 *            要提示的内容
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Dialog createsystemDialog(Context context, String msg) {
		// RuntimeException: Can't create handler inside thread that has not
		// called Looper.prepare()
		final Dialog systemDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.system_prompt, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.system_layout);// 加载布局
		// main.xml中的ImageView
		TextView system_massge = (TextView) v.findViewById(R.id.system_massge);
		Button system_btn = (Button) v.findViewById(R.id.system_btn);// 提示文字
		system_massge.setText(msg);
		system_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				systemDialog.dismiss();
			}
		});
		systemDialog.setCancelable(false);// 不可以用“返回键”取消
		systemDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		systemDialog.setCanceledOnTouchOutside(true);// 点击空白处取消dialog
		return systemDialog;
	}

//	/**
//	 * 创建ListViewDialog
//	 * 
//	 * @param ctx
//	 *            上下文
//	 * @param title
//	 *            标题
//	 * @param route
//	 *            路线信息
//	 * @param adapter
//	 *            适配器
//	 * @return Dialog
//	 */
//	public static Dialog createListViewDialog(Context ctx, String title,
//			RouteLine<?> route, ListAdapter adapter, String lineType) {
//
//		final Dialog listViewDialog = new Dialog(ctx, R.style.loading_dialog);
//		// 加载布局
//		View view = View.inflate(ctx, R.layout.dialog_listview, null);
//		TextView tv_title = (TextView) view
//				.findViewById(R.id.dialog_listView_title);
//		TextView tv_route_info = (TextView) view
//				.findViewById(R.id.dialog_listView_route_info);
//		ListView listView = (ListView) view
//				.findViewById(R.id.dialog_listView_msg);
//		// 设置内容
//		tv_title.setText(title);
//		listView.setAdapter(adapter);
//
//		if (lineType == ConstantsUtils.DRIVING_ROUTE) {
//			DrivingRouteLine line = (DrivingRouteLine) route;
//			tv_route_info.setText("起点：" + line.getStarting().getTitle()
//					+ "\n终点：" + line.getTerminal().getTitle() + "\n距离："
//					+ (float) line.getDistance() / 1000 + "km" + "\n时长："
//					+ TimeUtil.times(line.getDuration()) + "\n红绿灯："
//					+ line.getLightNum() + "\n拥堵距离："
//					+ line.getCongestionDistance());
//
//		} else if (lineType == ConstantsUtils.TRANSIT_ROUTE) {
//			TransitRouteLine line = (TransitRouteLine) route;
//			tv_route_info.setText("起点：" + line.getStarting().getTitle()
//					+ "\n终点：" + line.getTerminal().getTitle() + "\n距离："
//					+ (float) line.getDistance() / 1000 + "km" + "\n时长："
//					+ TimeUtil.times(line.getDuration()));
//
//		} else if (lineType == ConstantsUtils.WALKING_ROUTE) {
//			WalkingRouteLine line = (WalkingRouteLine) route;
//			tv_route_info.setText("起点：" + line.getStarting().getTitle()
//					+ "\n终点：" + line.getTerminal().getTitle() + "\n距离："
//					+ (float) line.getDistance() / 1000 + "km" + "\n时长："
//					+ TimeUtil.times(line.getDuration()));
//
//		} else if (lineType == ConstantsUtils.BIKING_ROUTE) {
//			BikingRouteLine line = (BikingRouteLine) route;
//			tv_route_info.setText("起点：" + line.getStarting().getTitle()
//					+ "\n终点：" + line.getTerminal().getTitle() + "\n距离："
//					+ (float) line.getDistance() / 1000 + "km" + "\n时长："
//					+ TimeUtil.times(line.getDuration()));
//		}
//		// dialog属性设置
//		listViewDialog.setCancelable(true);// 不可以用“返回键”取消
//		listViewDialog.setContentView(view, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT,
//				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
//		listViewDialog.setCanceledOnTouchOutside(true);// 点击空白处取消dialog
//
//		return listViewDialog;
//	}

	/**
	 * 设置Dialog的大小和Dialog中ListView的自适应
	 * 
	 * @param dialog
	 *            对话框
	 * @param list
	 *            对话框中的ListView
	 */
	@SuppressWarnings("deprecation")
	public static void setDialog(Dialog dialog, Context context) {
		if (dialog != null) {
			// 得到当前dialog对应的窗体
			Window dialogWindow = dialog.getWindow();
			// 管理器
			WindowManager m = ((Activity) context).getWindowManager();
			// 屏幕分辨率，获取屏幕宽、高用
			Display d = m.getDefaultDisplay();
			// 获取对话框当前的参数值
			WindowManager.LayoutParams p = dialogWindow.getAttributes();
			// 宽度设置为屏幕的0.8
			p.width = (int) (d.getWidth() * 0.8);
			p.height = (int) (d.getHeight() * 0.9);
			// 获取ListView的高度和当前屏幕的0.6进行比较，如果高，就自适应改变
			// 设置Dialog的高度
			dialogWindow.setAttributes(p);
		}
	}
	/**
	 * 创建单按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createDialog_onebutton(Context ctx, String title, String message, String Buttonname,View.OnClickListener listener) {

		final Dialog loadingDialog = new Dialog(ctx, R.style.loading_dialog);
		View view = View.inflate(ctx, R.layout.dialog_alter, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		tv_title.setText(title);
		tv_msg.setText(message);
		btn_cancel.setText(Buttonname);
		btn_cancel.setOnClickListener(listener);
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}
}
