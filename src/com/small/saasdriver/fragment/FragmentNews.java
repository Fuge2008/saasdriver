//package com.small.saasdriver.fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.example.saascardriver.R;
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//import com.saas.chat.Constants;
//import com.saas.chat.common.Utils;
//import com.saas.chat.view.activity.WebViewActivity;
//import com.small.saasdriver.adapter.NewsAdapter;
//import com.small.saasdriver.entity.NewEntity;
//import com.small.saasdriver.global.HttpConstant;
//import com.small.saasdriver.utils.CacheUtil;
//
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//
//public class FragmentNews extends BaseFragment {
//	private ListView lv_news;
//	private NewsAdapter new_adapter;
//	private List<NewEntity> data;
//
//	private Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case HANDLER_LOAD_EMP_SUCCESS:
//				// 集合已经加载完毕 更新adapter
//				showData(data);
//				break;
//			}
//		}
//	};
//
//	public static final int HANDLER_LOAD_EMP_SUCCESS = 1;
//
//	@Override
//	protected View initView() {
//		View view = View.inflate(mActivity, R.layout.fragment_news, null);
//		lv_news = (ListView) view.findViewById(R.id.lv_news);
//		return view;
//	}
//
//	public void initData() {
//		String resJson;
//		try {
//			resJson = CacheUtil.getFileCache(mActivity, HttpConstant.NEWS);
//			if (resJson != null) {
//				// processData(resJson);
//			}
//			sendDataToNet();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void sendDataToNet() {
//		RequestParams params = new RequestParams();
//		params.addBodyParameter("type", "top");
//		params.addBodyParameter("key", "71b8bd3c57c382ac6e1bf2b45b9ef97a");
//		HttpUtils utils = new HttpUtils();
//		utils.send(HttpMethod.POST, HttpConstant.NEWS, params, new RequestCallBack<String>() {
//
//			@Override
//			public void onStart() {// 开始请求
//			}
//
//			@Override
//			public void onLoading(long total, long current, boolean isUploading) {// 加载请求
//
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> responseInfo) {// 请求成功
//				String res = responseInfo.result;
//				Log.i("info", "网络数据加载成功：" + res);
//				try {
//					CacheUtil.setFileCache(mActivity, HttpConstant.ORDER_NOW_OR_PLAN, res);
//					JSONObject obj = new JSONObject(res);
//					JSONObject ob = obj.getJSONObject("result");
//					JSONArray ary = ob.getJSONArray("data");
//					data = new ArrayList<>();
//					for (int i = 0; i < ary.length(); i++) {
//						JSONObject news = ary.getJSONObject(i);
//						NewEntity entity = new NewEntity(news.getString("author_name"), news.getString("category"),
//								news.getString("date"), news.getString("thumbnail_pic_s"), news.getString("title"),
//								news.getString("uniquekey"), news.getString("url"));
//						data.add(entity);
//
//					}
//
//					Log.i("info", "" + data.toString());
//					handler.sendEmptyMessage(HANDLER_LOAD_EMP_SUCCESS);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//
//			@Override
//			public void onFailure(HttpException error, String msg) {// 请求失败
//				Log.i("info", "请求数据失败原因：" + msg);
//				error.printStackTrace();
//			}
//		});
//
//	}
//
//	private void showData(final List<NewEntity> data) {// 显示数据
//
//		new_adapter = new NewsAdapter(mActivity, data);
//		lv_news.setAdapter(new_adapter);// 设置适配器
//		// XUtilsImageLoader image_head = new
//		// XUtilsImageLoader(NewsActivity.this);
//		// image_head.display(DriverPhotoPath,
//		// "http://p3.so.qhmsg.com/t0130092dd6c3ea991d.jpg");
//
//		lv_news.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Utils.start_Activity(mActivity, WebViewActivity.class, new BasicNameValuePair(Constants.Title, "新闻头条"),
//						new BasicNameValuePair(Constants.URL, data.get(position).getUrl()));
//			}
//		});
//	}
//
//}
