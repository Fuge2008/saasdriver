package com.small.saasdriver.activity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.x;

import com.example.saascardriver.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.saas.chat.Constants;
import com.saas.chat.common.Utils;
import com.saas.chat.view.activity.WebViewActivity;
import com.small.saasdriver.adapter.DragAdapter;
import com.small.saasdriver.adapter.NewsAdapter;
import com.small.saasdriver.adapter.NewsButtonAdapter;
import com.small.saasdriver.adapter.OtherAdapter;
import com.small.saasdriver.entity.NewEntity;
import com.small.saasdriver.entity.NewsChannelEntity;
import com.small.saasdriver.global.HttpConstant;
import com.small.saasdriver.utils.CacheUtil;
import com.small.saasdriver.utils.StringUtils;
import com.small.saasdriver.view.CurtainView;
import com.small.saasdriver.view.DragGrid;
import com.small.saasdriver.view.OtherGridView;
import com.small.saasdriver.view.DragGrid.Exchangelistenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class NewsActivity extends BaseActivity implements OnItemClickListener {
	private ListView lv_news;
	private NewsAdapter new_adapter;
	private NewsButtonAdapter button_adapter;
	private List<NewEntity> data;
	private CurtainView cv_pull;
	private GridView gv_news;
	/** 用户栏目的GRIDVIEW */
	private DragGrid userGridView;
	/** 其它栏目的GRIDVIEW */
	private OtherGridView otherGridView;
	/** 用户栏目对应的适配器，可以拖动 */
	DragAdapter userAdapter;
	/** 其它栏目对应的适配器 */
	OtherAdapter otherAdapter;
	/** 其它栏目列表 */
	ArrayList<NewsChannelEntity> otherChannelList = new ArrayList<NewsChannelEntity>();
	/** 用户栏目列表 */
	ArrayList<NewsChannelEntity> userChannelList = new ArrayList<NewsChannelEntity>();
	ArrayList<NewsChannelEntity> myChannel;
	ArrayList<NewsChannelEntity> unmyChannel;

	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
	boolean isMove = false;
	// private UnivsDataBase<NewsChannelEntity> mUnivsDataBase;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_LOAD_EMP_SUCCESS:
				// 集合已经加载完毕 更新adapter
				showData(data);
				break;
			}
		}
	};

	public static final int HANDLER_LOAD_EMP_SUCCESS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		initView();
		getData();
		initData("top");
		initNaigation();
		chance(userChannelList);
		setListener();
	}

	private void initNaigation() {
		myChannel = new ArrayList<NewsChannelEntity>();
		unmyChannel = new ArrayList<NewsChannelEntity>();
		for (int i = 0; i < userChannelList.size(); i++) {
			if (i % 2 == 0) {
				myChannel.add(userChannelList.get(i));
			} else {
				unmyChannel.add(userChannelList.get(i));
			}

		}
		userAdapter = new DragAdapter(this, myChannel);
		userGridView.setAdapter(userAdapter);
		userGridView.setonExchangelistenter(new Exchangelistenter() {

			@Override
			public void exchange(int dragPostion, int dropPostion) {
				changeChannels = true;
				chance(myChannel);

			}
		});
		otherAdapter = new OtherAdapter(this, unmyChannel);
		otherGridView.setAdapter(this.otherAdapter);
		// 设置GRIDVIEW的ITEM的点击监听
		otherGridView.setOnItemClickListener(this);
		userGridView.setOnItemClickListener(this);
		// }

	}

	private void setListener() {
		iv_back.setOnClickListener(this);
	}

	private void initData(String str) {
		String resJson;
		try {
			resJson = CacheUtil.getFileCache(NewsActivity.this, HttpConstant.NEWS + str);
			if (resJson != null) {
				// processData(resJson);
			}
			RequestParams params = new RequestParams();
			params.addBodyParameter("type", str);
			params.addBodyParameter("key", "71b8bd3c57c382ac6e1bf2b45b9ef97a");
			sendDataToNet(params, HttpConstant.NEWS, str);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getData() {
		NewsChannelEntity n1 = new NewsChannelEntity();
		n1.isSelect = true;
		n1.keys = "top";
		n1.news_channel = "头条";
		userChannelList.add(n1);
		NewsChannelEntity n2 = new NewsChannelEntity();
		n2.isSelect = false;
		n2.keys = "shehui";
		n2.news_channel = "社会";
		userChannelList.add(n2);
		NewsChannelEntity n3 = new NewsChannelEntity();
		n3.isSelect = false;
		n3.keys = "guonei";
		n3.news_channel = "国内";
		userChannelList.add(n3);
		NewsChannelEntity n4 = new NewsChannelEntity();
		n4.isSelect = false;
		n4.keys = "guoji";
		n4.news_channel = "国际";
		userChannelList.add(n4);
		NewsChannelEntity n5 = new NewsChannelEntity();
		n5.isSelect = false;
		n5.keys = "yule";
		n5.news_channel = "娱乐";
		userChannelList.add(n5);
		NewsChannelEntity n6 = new NewsChannelEntity();
		n6.isSelect = false;
		n6.keys = "tiyu";
		n6.news_channel = "体育";
		userChannelList.add(n6);
		NewsChannelEntity n7 = new NewsChannelEntity();
		n7.isSelect = false;
		n7.keys = "junshi";
		n7.news_channel = "军事";
		userChannelList.add(n7);
		NewsChannelEntity n8 = new NewsChannelEntity();
		n8.isSelect = true;
		n8.keys = "keji";
		n8.news_channel = "科技";
		userChannelList.add(n8);
		NewsChannelEntity n9 = new NewsChannelEntity();
		n9.isSelect = true;
		n9.keys = "caijing";
		n9.news_channel = "财经";
		userChannelList.add(n9);
		NewsChannelEntity n10 = new NewsChannelEntity();
		n10.isSelect = true;
		n10.keys = "shishang";
		n10.news_channel = "时尚";
		userChannelList.add(n10);

	}

	private void initView() {
		lv_news = (ListView) findViewById(R.id.lv_news);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
		gv_news = (GridView) findViewById(R.id.gv_news);

	}

	private void sendDataToNet(RequestParams params, final String url, final String str) {

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {// 开始请求
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {// 加载请求

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
				String res = responseInfo.result;
				Log.i("info", "网络数据加载成功：" + res);
				try {
					if (StringUtils.equals(url, HttpConstant.NEWS)) {
						CacheUtil.setFileCache(NewsActivity.this, url + str, res);
						JSONObject obj = new JSONObject(res);
						JSONObject ob = obj.getJSONObject("result");
						JSONArray ary = ob.getJSONArray("data");
						data = new ArrayList<>();
						for (int i = 0; i < ary.length(); i++) {
							JSONObject news = ary.getJSONObject(i);
							NewEntity entity = new NewEntity(news.getString("author_name"), news.getString("category"),
									news.getString("date"), news.getString("thumbnail_pic_s"), news.getString("title"),
									news.getString("uniquekey"), news.getString("url"));
							data.add(entity);

						}

						Log.i("info", "" + data.toString());
						handler.sendEmptyMessage(HANDLER_LOAD_EMP_SUCCESS);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {// 请求失败
				Log.i("info", "请求数据失败原因：" + msg);
				error.printStackTrace();
			}
		});

	}

	private void showData(final List<NewEntity> data) {// 显示数据

		new_adapter = new NewsAdapter(NewsActivity.this, data);
		lv_news.setAdapter(new_adapter);// 设置适配器
		// XUtilsImageLoader image_head = new
		// XUtilsImageLoader(NewsActivity.this);
		// image_head.display(DriverPhotoPath,
		// "http://p3.so.qhmsg.com/t0130092dd6c3ea991d.jpg");

		lv_news.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Utils.start_Activity(NewsActivity.this, WebViewActivity.class,
						new BasicNameValuePair(Constants.Title, "新闻头条"),
						new BasicNameValuePair(Constants.URL, data.get(position).getUrl()));
			}
		});
	}

	/** GRIDVIEW对应的ITEM点击监听接口 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
		// 如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if (isMove) {
			return;
		}
		switch (parent.getId()) {
		case R.id.userGridView:
			// position为 0，1 的不可以进行任何操作
			// if (position != 0 && position != 1) {
			final ImageView moveImageView = getView(view);
			if (moveImageView != null) {
				TextView newTextView = (TextView) view;
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final NewsChannelEntity channel = ((DragAdapter) parent.getAdapter()).getItem(position);// 获取点击的频道内容
				otherAdapter.setVisible(false);
				// 添加到最后一个
				otherAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];

							// 获取终点的坐标
							otherGridView.getChildAt(otherGridView.getLastVisiblePosition())
									.getLocationInWindow(endLocation);
							MoveAnim(moveImageView, startLocation, endLocation, channel, userGridView);
							userAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			// }
			break;
		case R.id.otherGridView:
			final ImageView omoveImageView = getView(view);
			if (omoveImageView != null) {
				TextView newTextView = (TextView) view;
				final int[] startLocation = new int[2];

				newTextView.getLocationInWindow(startLocation);
				final NewsChannelEntity channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
				userAdapter.setVisible(false);
				// 添加到最后一个
				userAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {

					public void run() {
						try {
							int[] endLocation = new int[2];
							// 获取终点的坐标
							userGridView.getChildAt(userGridView.getLastVisiblePosition())
									.getLocationInWindow(endLocation);
							MoveAnim(omoveImageView, startLocation, endLocation, channel, otherGridView);
							otherAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}

				}, 50L);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 点击ITEM移动动画
	 *
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final NewsChannelEntity moveChannel,
			final GridView clickGridView) {
		int[] initLocation = new int[2];
		// 获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		// 得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		// 创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);// 动画时间
		// 动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof DragGrid) {
					changeChannels = true;
					chance(myChannel);
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				} else {
					changeChannels = true;
					chance(myChannel);
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				isMove = false;
			}
		});
	}

	/**
	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	 *
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}

	/**
	 * 创建移动的ITEM对应的ViewGroup布局容器
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout
				.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}

	/**
	 * 获取点击的Item的对应View，
	 *
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}

	private boolean changeChannels = false;

	private void chance(List<NewsChannelEntity> news) {
		button_adapter = new NewsButtonAdapter(NewsActivity.this, news);
		gv_news.setAdapter(button_adapter);
		gv_news.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				NewsChannelEntity new_s1 = userChannelList.get(position);
				if (StringUtils.equals(new_s1.keys, "top")) {// 根据不同的关键字，从数据聚合网获取不同的新闻资源
					initData("top");
				} else if (StringUtils.equals(new_s1.keys, "shehui")) {
					initData("shehui");
				} else if (StringUtils.equals(new_s1.keys, "guonei")) {
					initData("guonei");
				} else if (StringUtils.equals(new_s1.keys, "guoji")) {
					initData("guoji");
				} else if (StringUtils.equals(new_s1.keys, "yule")) {
					initData("yule");
				} else if (StringUtils.equals(new_s1.keys, "tiyu")) {
					initData("tiyu");
				} else if (StringUtils.equals(new_s1.keys, "junshi")) {
					initData("junshi");
				} else if (StringUtils.equals(new_s1.keys, "keji")) {
					initData("keji");
				} else if (StringUtils.equals(new_s1.keys, "caijing")) {
					initData("caijing");
				} else if (StringUtils.equals(new_s1.keys, "shishang")) {
					initData("shishang");
				}

			}
		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void doClick(View v) {
		// TODO Auto-generated method stub

	}
}
