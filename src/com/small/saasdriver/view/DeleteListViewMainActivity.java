package com.small.saasdriver.view;

import java.util.ArrayList;
import java.util.List;

import com.example.saascardriver.R;
import com.small.saasdriver.view.SlideView;
import com.small.saasdriver.view.XListView;
import com.small.saasdriver.view.SlideView.OnSlideListener;
import com.small.saasdriver.view.XListView.OnLoadListener;
import com.small.saasdriver.view.XListView.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("rawtypes")
public class DeleteListViewMainActivity extends Activity implements OnItemClickListener, OnClickListener,
        OnSlideListener, OnRefreshListener,OnLoadListener {

    private static final String TAG = "MainActivity";

    private XListView mListView;
    private ScrollView scrollview;

    private List<MessageItem> mMessageItems = new ArrayList<DeleteListViewMainActivity.MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;
    
    private SlideAdapter adapter;
    private int allCount = 40;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_listview_delete_main);
    	initView();
    	loadData(XListView.REFRESH);
    }
    

	public void initView() {
		  mListView = (XListView) findViewById(R.id.list);
			adapter = new SlideAdapter();
	        mListView.setAdapter(adapter);
	        mListView.setOnItemClickListener(this);
	        mListView.setOnRefreshListener(this);
	        mListView.setOnLoadListener(this);
		
	}
	
	private void loadData(final int what) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.what = what;
				msg.obj = getData();
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	// 测试数据
		public List<MessageItem> getData() {
			List<MessageItem> result = new ArrayList<MessageItem>();
			 for (int i = 0; i < 10; i++) {
		            MessageItem item = new MessageItem();
		            if (i % 3 == 0) {
		                item.iconRes = R.drawable.delete_default_qq_avatar;
		                item.title = "腾讯新闻";
		                item.msg = "深圳西站增开两趟临客";
		                item.time = "下午14:15";
		            } else {
		                item.iconRes = R.drawable.delete_wechat_icon;
		                item.title = "微信团队";
		                item.msg = "欢迎你使用微信";
		                item.time = "12:28";
		            }
		            result.add(item);
		        }
			return result;
		}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			List<MessageItem> result = (List<MessageItem>) msg.obj;
			switch (msg.what) {
			case XListView.REFRESH://更新消息
				mListView.onRefreshComplete();
				mMessageItems.clear();
				mMessageItems.addAll(result);
				break;
			case XListView.LOAD://加载更多
				mListView.onLoadComplete();
				mMessageItems.addAll(result);
				break;
			case 2:
				mListView.onLoadComplete();//加载完成
				Toast.makeText(DeleteListViewMainActivity.this, "亲，已到底啦！", Toast.LENGTH_SHORT).show();
				break;
			}
			mListView.setResultSize(result.size());
			adapter.notifyDataSetChanged();
		};
	};
    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.item_listview_delete, null);

                slideView = new SlideView(DeleteListViewMainActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(DeleteListViewMainActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mMessageItems.remove(position);
			    	adapter.notifyDataSetChanged();
			    	Toast.makeText(DeleteListViewMainActivity.this, "删除" + position+"个条", 0).show();
				}
			});
            holder.edit.setOnClickListener(new OnClickListener() {
            	
            	@Override
            	public void onClick(View v) {
            		adapter.notifyDataSetChanged();
            		Toast.makeText(DeleteListViewMainActivity.this, "编辑" + position+"个条", 0).show();
            	}
            });
            
            return slideView;
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public TextView deleteHolder;
        public TextView edit;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (TextView) view.findViewById(R.id.delete);
            edit = (TextView) view.findViewById(R.id.edit);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Toast.makeText(this, "onItemClick position=" + position, 0).show();
    	
    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
	case R.id.holder:
		
		break;

	default:
		break;
	}
    }
	@Override
	public void onLoad() {
		if(adapter.getCount()<allCount){
			loadData(XListView.LOAD);
		}else{
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = mMessageItems;
			handler.sendMessage(msg);
			
		}
	}
	@Override
	public void onRefresh() {
		loadData(XListView.REFRESH);
	}


}
