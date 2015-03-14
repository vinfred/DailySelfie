package com.paradiseoctopus.snapphoto.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.paradiseoctopus.snapphoto.R;
import com.paradiseoctopus.snapphoto.adapters.ListAdapter;

import java.util.ArrayList;

public class PhotoActivity extends FragmentActivity {
	public static final String ARG_CURPHOTO = "photo";
	private ImageView photoView;
	private ArrayList<String> photoPath;
	private int index;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_activity);

		index = getIntent().getIntExtra(ListAdapter.ARG_INDEX, 0);
		photoPath = getIntent().getStringArrayListExtra(ListAdapter.ARG_PHOTOS);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(index);
	}


	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

			Bundle args = new Bundle();
			args.putString(ARG_CURPHOTO, photoPath.get(position));
			fragment.setArguments(args);

			return fragment;
		}

		@Override
		public int getCount() {
			return photoPath.size();
		}
	}
}


