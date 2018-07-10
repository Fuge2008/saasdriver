package com.small.saasdriver.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter{  
    
	private Context context;
	private List<View> pageList;

	public ViewPagerAdapter(Context context, List<View> pageList) {
		this.context = context;
		this.pageList = pageList;
	}

	@Override
	public int getCount() {
		return pageList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		if (view == object) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		object = null;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(pageList.get(position));
		return pageList.get(position);
	}


}  
