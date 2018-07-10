//package com.small.saasdriver.adapter;
//
//import java.util.ArrayList;
//
//import com.small.saasdriver.entity.NewsChannelEntity;
//import com.small.saasdriver.fragment.FragmentNews;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.app.FragmentTransaction;
//import android.view.ViewGroup;
//
//public class NewsFragmentPagerAdapter extends FragmentStatePagerAdapter {
//	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
//	private FragmentManager fm;
//
//	public NewsFragmentPagerAdapter(FragmentManager fm) {
//		super(fm);
//		this.fm = fm;
//	}
//
//	public NewsFragmentPagerAdapter(FragmentManager fm,
//			ArrayList<NewsChannelEntity> categorys) {
//		super(fm);
//		this.fm = fm;
//		FragmentNews newInstance = null;
//		for (NewsChannelEntity itme : categorys) {
//			newInstance = FragmentNews.newInstance(itme.news_channel);
//			fragments.add(newInstance);
//		}
//	}
//
//	@Override
//	public int getCount() {
//		return fragments.size();
//	}
//
//	@Override
//	public Fragment getItem(int position) {
//		return fragments.get(position);
//	}
//
//	@Override
//	public int getItemPosition(Object object) {
//		return POSITION_NONE;
//	}
//
//	public void setFragments(ArrayList<Fragment> fragments) {
//		if (this.fragments != null) {
//			FragmentTransaction ft = fm.beginTransaction();
//			for (Fragment f : this.fragments) {
//				ft.remove(f);
//			}
//			ft.commit();
//			ft = null;
//			fm.executePendingTransactions();
//		}
//		this.fragments = fragments;
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public Object instantiateItem(ViewGroup container, final int position) {
//		Object obj = super.instantiateItem(container, position);
//		return obj;
//	}
//
//}
