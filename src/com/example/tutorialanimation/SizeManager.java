package com.example.tutorialanimation;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

public class SizeManager {
	private final static String TAG = "LayoutManager";
	private final static boolean DEBUG = true;
	
	private Context mContext;
	private static SizeManager mInstance;
	
	private boolean mPortrait;
	
	// Screen size
	private int mScreenWidth;
	private int mScreenHeight;
	// Fragment size
	private int indexFragment_width;
	private int indexFragment_Hheight;
	private int contentFragment_width;
	private int contentFragment_height;
	// Text size 
	private int text_size_index_first_directory;
	private int text_size_index_second_directory;
	private int text_size_index_thrid_directory;
	private int text_size_content_first_directory;
	private int text_size_content_second_directory;
	private int text_size_content_third_directory;
	private int text_size_content_introduce;
	// Animation size
	private int animation_width;
	private int animation_height;
	
	private SizeManager(Context context) {
		mContext = context;
	}
	
	public static SizeManager getInstance(Context context) {
		if(mInstance == null)
			mInstance = new SizeManager(context);
		return mInstance;
	}
	
	private void updateScreenSize() {
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		mScreenWidth = displayMetrics.widthPixels;
		mScreenHeight = displayMetrics.heightPixels;
	}
	
	public int getTextSize() {
		updateScreenOrientation();
		updateScreenSize();
		return mScreenWidth / 50;
	}
	
	private void updateScreenOrientation() {
		Configuration configuration = mContext.getResources().getConfiguration();
		if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if(DEBUG) Log.v(TAG, "Now is landscape");
			mPortrait = false;
		} else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
			if(DEBUG) Log.v(TAG, "Now is portait");
			mPortrait = true;
		} else {
			throw new IllegalArgumentException("Orientation configuration is wrong"); 
		}
	}
	
	public boolean getPortrait() {
		return mPortrait;
	}
	
}
