package com.small.saasdriver.application;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xutils.x;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.Trace;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.chat.EMMessage.ChatType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.saas.chat.CrashHandler;
import com.saas.chat.MainActivity;
import com.saas.chat.chat.ChatActivity;
import com.saas.chat.chat.VoiceCallActivity;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.PreferenceUtils;
import com.small.saasdriver.utils.SharePreferenceUtils;

public class MyApplication extends FrontiaApplication {

	//private static MyApplication context;
	public static boolean islogin;
	//private JSONObject userJson;

	public static List<Map<String, Object>> datalits_notify_message = new ArrayList<>();

	public static List<Map<String, Object>> getDatalits_notify_message() {
		return datalits_notify_message;
	}

	public static void setDatalits_notify_message(List<Map<String, Object>> datalits_notify_message) {
		MyApplication.datalits_notify_message = datalits_notify_message;
	}

	public static boolean isAtuoLogin;

	public static boolean isAtuoLogin() {
		return isAtuoLogin;
	}
//	public  void setUserJson( JSONObject userJson){
//
//		this.userJson=userJson;
//		LocalDataUtils.getInstance().setUserJson(userJson);
//
//	}
//	public JSONObject getUserJson(){
//		if(userJson==null){
//			userJson= LocalDataUtils.getInstance().getUserJson();
//		}
//		return  userJson;
//	}

	public static void setDataList(String tag, List<Map<String, Object>> datalist) {
		if (null == datalist || datalist.size() <= 0)
			return;
		Gson gson = new Gson();
		String strJson = gson.toJson(datalist);
		PreferenceUtils.writeString(MyApplication.getmContext(), tag, strJson);

	}

	public static List<Map<String, Object>> getDataList(String tag) {
		String strJson = PreferenceUtils.readString(MyApplication.getmContext(), tag);
		if (null == strJson) {
			return null;
		}
		Gson gson = new Gson();
		List<Map<String, Object>> datalist = gson.fromJson(strJson, new TypeToken<List<Map<String, Object>>>() {
		}.getType());

		return datalist;

	}

	public void setAtuoLogin(boolean isAtuoLogin) {

		MyApplication.isAtuoLogin = isAtuoLogin;
	}

	public static String Identifyt;
	public static String DriverID;

	public static String getDriverID() {
		return DriverID;
	}

	public static void setDriverID(String driverID) {
		DriverID = driverID;
	}

	public String getIdentifyt() {
		return Identifyt;
	}

	public void setIdentifyt(String identifyt) {
		Identifyt = identifyt;
	}

	public Retrofit createRetrofit() {

		Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpConstant.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create()).build();

		return retrofit;

	}

	/** 界面集合 */
	private List<Activity> activityList = new ArrayList<Activity>();

	public final String tag = MyApplication.class.getSimpleName();
	public static List<Map<String, Object>> datalits = new ArrayList<>();

	public static Context mContext;

	public List<Map<String, Object>> getDatalits() {
		return datalits;
	}

	public void setDatalits(List<Map<String, Object>> datalits) {
		MyApplication.datalits = datalits;
	}

	public static MyApplication instance = null;

	public MyApplication() {

	}

	public static Context getmContext() {

		return mContext;
	}
	/**
	 * 轨迹服务
	 */
	private Trace trace = null;

	/**
	 * 轨迹服务客户端
	 */
	private LBSTraceClient client = null;

	/**
	 * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
	 */
	private int serviceId = 129168;

	/**
	 * entity标识
	 */
	private String entityName = "myTrace";

	/**
	 * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
	 */
	private int traceType = 2;

	private MapView bmapView = null;

	private BaiduMap mBaiduMap = null;

	private TrackHandler myHandler = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		// 百度地图初始化
		SDKInitializer.initialize(mContext);
		// 初始化轨迹服务客户端
				client = new LBSTraceClient(mContext);

				// 初始化轨迹服务
				trace = new Trace(mContext, serviceId, entityName, traceType);

				// 设置定位模式
				client.setLocationMode(LocationMode.High_Accuracy);

				myHandler = new TrackHandler(this);

				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						SpeechUtility.createUtility(MyApplication.this,
								SpeechConstant.APPID + "=582bbff6," + SpeechConstant.FORCE_LOGIN + "=true");
					}
				}).start();

		islogin = SharePreferenceUtils.getBoolean(mContext, "isLogin", false);
		// XUtils框架初始化
		x.Ext.init(this);
		initEMChat();
		EMChat.getInstance().init(mContext);
		EMChat.getInstance().setDebugMode(true);
		EMChat.getInstance().setAutoLogin(true);
		EMChatManager.getInstance().getChatOptions().setUseRoster(true);
		FrontiaApplication.initFrontiaApplication(this);
//		 CrashHandler crashHandler = CrashHandler.getInstance();// 全局异常捕捉
//		crashHandler.init(mContext);

	}
	
	private void initEMChat() {
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		if (processAppName == null
				|| !processAppName.equalsIgnoreCase("com.saas.chat")) {
			return;
		}
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		// 获取到EMChatOptions对象
		// 设置自定义的文字提示
		options.setNotifyText(new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				return "你的好友发来了一条消息哦";
			}

			@Override
			public String onLatestMessageNotify(EMMessage message,
					int fromUsersNum, int messageNum) {
				return fromUsersNum + "个好友，发来了" + messageNum + "条消息";
			}

			@Override
			public String onSetNotificationTitle(EMMessage arg0) {
				return null;
			}

			@Override
			public int onSetSmallIcon(EMMessage arg0) {
				return 0;
			}
		});
		options.setOnNotificationClickListener(new OnNotificationClickListener() {

			@Override
			public Intent onNotificationClick(EMMessage message) {
				Intent intent = new Intent(mContext, MainActivity.class);
				ChatType chatType = message.getChatType();
				if (chatType == ChatType.Chat) { // 单聊信息
					intent.putExtra("userId", message.getFrom());
					intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
				} else { // 群聊信息
					// message.getTo()为群聊id
					intent.putExtra("groupId", message.getTo());
					intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
				}
				return intent;
			}
		});
		//视频通话     ----->调试
//		 IntentFilter callFilter = new		
//		 IntentFilter(EMChatManager.getInstance()
//		 .getIncomingCallBroadcastAction());
//		 registerReceiver(new CallReceiver(), callFilter);
	}

	private class CallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 拨打方username
			String from = intent.getStringExtra("from");
			// call type
			String type = intent.getStringExtra("type");
			startActivity(new Intent(mContext, VoiceCallActivity.class)
					.putExtra("username", from).putExtra("isComingCall", true));
		}
	}

	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
			}
		}
		return processName;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		try {
			deleteCacheDirFile(getHJYCacheDir(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.gc();
	}

//	public static Context getmContext() {
//		return mContext;
//	}

	// 运用list来保存们每一个activity是关键
	//private List<Activity> mList = new LinkedList<Activity>();
	//private static MyApplication instance;

	// 构造方法
	// 实例化一次
	public synchronized static MyApplication getInstance2() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

//	// add Activity
//	public void addActivity(Activity activity) {
//		mList.add(activity);
//	}

	// 关闭每一个list内的activity
	public void exit() {
		try {
			for (Activity activity : activityList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public static String getHJYCacheDir() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return Environment.getExternalStorageDirectory().toString()
					+ "/Health/Cache";
		else
			return "/System/com.saas.Walk/Walk/Cache";
	}

	public static String getHJYDownLoadDir() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return Environment.getExternalStorageDirectory().toString()
					+ "/Walk/Download";
		else {
			return "/System/com.saas.Walk/Walk/Download";
		}
	}

	public static void deleteCacheDirFile(String filePath,
			boolean deleteThisPath) throws IOException {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file.isDirectory()) {// 处理目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteCacheDirFile(files[i].getAbsolutePath(), true);
				}
			}
			if (deleteThisPath) {
				if (!file.isDirectory()) {// 如果是文件，删除
					file.delete();
				} else {// 目录
					if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
						file.delete();
					}
				}
			}
		}
	}

	/**
	 * 单例模式
	 * 
	 * @return Application对象
	 */
	public static MyApplication getInstance() {
		// 判断对象是否为空
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}

	/**
	 * 添加界面到集合
	 * 
	 * @param activity
	 *            界面
	 */
	public void addActivity(Activity activity) {
		// 判断传入的界面是否有效
		if (activity != null) {
			activityList.add(activity);
		}
	}

	/** 删除List集合 */
	public void delActivityList(Activity activity) {
		if (activityList != null && activity != null) {
			activity.finish();
			activityList.remove(activity);
			activity = null;
		}
	}

	/**
	 * 退出所有的界面
	 */
	public void exitActivity(Context context) {
		for (Activity activity : activityList) {
			if (activity != null) {
				// 关闭界面
				activity.finish();
			}
		}
	}
	//百度鹰眼
		public void initBmap(MapView bmapView) {
			this.bmapView = bmapView;
			this.mBaiduMap = bmapView.getMap();
			this.bmapView.showZoomControls(false);
		}

		static class TrackHandler extends Handler {
			WeakReference<MyApplication> trackApp;

			TrackHandler(MyApplication myApplication) {
				trackApp = new WeakReference<MyApplication>(myApplication);
			}

			@Override
			public void handleMessage(Message msg) {
				Toast.makeText(trackApp.get().mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
			}
		}

		public Context getmyContext() {
			return mContext;
		}

		public Trace getTrace() {
			return trace;
		}

		public LBSTraceClient getClient() {
			return client;
		}

		public int getServiceId() {
			return serviceId;
		}

		public String getEntityName() {
			return entityName;
		}

		public Handler getmyHandler() {
			return myHandler;
		}

		public MapView getBmapView() {
			return bmapView;
		}

		public BaiduMap getmBaiduMap() {
			return mBaiduMap;
		}

		/**
		 * 获取设备IMEI码，手机设备唯一标识
		 * 
		 * @param context
		 * @return
		 */
		protected static String getImei(Context context) {
			String mImei = "NULL";
			try {
				mImei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			} catch (Exception e) {
				System.out.println("获取IMEI码失败");
				mImei = "NULL";
			}
			return mImei;
		}


}
