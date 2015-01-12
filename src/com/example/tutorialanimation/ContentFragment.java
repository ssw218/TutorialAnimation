package com.example.tutorialanimation;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ContentFragment extends Fragment {
	private static final String TAG = "ContentFragment";
	private static final boolean DEBUG = true;
	
	private ContentLayout mContentLayout;
	private ScrollView mScrollView;
	
	public ContentFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(DEBUG) Log.v(TAG, "onCreateView()");
		super.onCreateView(inflater, container, savedInstanceState);
		//mContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.content, null);
		//return mContentLayout;
		mContentLayout = new ContentLayout(getActivity());
		mScrollView = new ScrollView(getActivity());
		mScrollView.setVerticalScrollBarEnabled(true);
		mScrollView.addView(mContentLayout);
		return mScrollView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (DEBUG) Log.e(TAG, "onResume");
		mContentLayout.onVideoResume();
	}
	
	public void thirdClick(TextView view) {
		mScrollView.scrollTo(0, mContentLayout.getThirdTextViewTop(view));
	}
	
	public void setScrollEvent(ContentLayout.ScrollEvent scrollEvent) {
		mContentLayout.setScrollEvent(scrollEvent);
	}
	
	public void updateSize() {
		//mContentLayout.setLeft(500);
	}
	
}
