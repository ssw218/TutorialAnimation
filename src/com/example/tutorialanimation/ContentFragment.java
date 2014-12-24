package com.example.tutorialanimation;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ContentFragment extends Fragment {
	private static final String TAG = "ContentFragment";
	private static final boolean DEBUG = true;
	
	private LinearLayout mContentLayout;
	private LayoutParams mLayoutParams;
	
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
		mContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.content, null);
		//return mContentLayout;
		return new ContentLayout(getActivity());
	}
	
	public void updateSize() {
		mContentLayout.setLeft(500);
	}
	
}
