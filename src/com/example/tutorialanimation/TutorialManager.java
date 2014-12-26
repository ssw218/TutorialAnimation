package com.example.tutorialanimation;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class TutorialManager {
	private final static String TAG = "LayoutManager";
	private final static boolean DEBUG = true;
	
	private Context mContext;
	private Resources mResources;
	private static TutorialManager mInstance;
	
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
	
	private String[] mOperation;
	private String[] mGesture;
	
	private TutorialManager(Context context) {
		mContext = context;
		mResources = mContext.getResources();
		mOperation = mResources.getStringArray(R.array.operation);
		mGesture = mResources.getStringArray(R.array.gesture);
	}
	
	public static TutorialManager getInstance(Context context) {
		if(mInstance == null)
			mInstance = new TutorialManager(context);
		return mInstance;
	}
	
	private void updateScreenSize() {
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		mScreenWidth = displayMetrics.widthPixels;
		mScreenHeight = displayMetrics.heightPixels;
	}
	
	public String findScecondDirectory(String text) {
		String secondDirectory = null;
		int i;
		for (i = 1; i < mOperation.length; i++) {
			if(mOperation[i].equals(text)) secondDirectory = mOperation[0];
		}
		for (i = 1; i < mGesture.length; i++) {
			if(mGesture[i].equals(text)) secondDirectory = mGesture[0];
		}
		return secondDirectory;
	}
	
	public boolean isLastSecondDirectory(String text) {
		if (mGesture[0].equals(text))
			return true;
		return false;
	}
	
	public boolean isLastThirdDirectory(String text) {
		if (mOperation[mOperation.length - 1].equals(text) ||
				mGesture[mGesture.length - 1].equals(text))
			return true;
		return false;
	}
	
	public boolean isFirstSecondDirectory(String text) {
		if (mOperation[0].equals(text))
			return true;
		return false;
	}
	
	public boolean isFirstThirdDirectory(String text) {
		if (mOperation[1].equals(text) || mGesture[1].equals(text))
			return true;
		return false;
	}
	
	public String getNextThirdDirectory(String text) {
		if(findScecondDirectory(text).equals(mOperation[0])) {
			for(int i = 1; i < mOperation.length; i ++) {
				if(mOperation[i].equals(text)) return mOperation[i + 1];
			}
		} else if(findScecondDirectory(text).equals(mGesture[0])) {
			for(int i = 1; i < mGesture.length; i ++) {
				if(mGesture[i].equals(text)) return mGesture[i + 1];
			}
		}
		return null;
	}
	
	public String getFirstSecondDirectory() {
		return mOperation[0];
	}
	
	public String getLastSecondDirectory(String secondDirectory) {
		if (secondDirectory.equals(mOperation[0])) 
			return mOperation[mOperation.length - 1];
		else if (secondDirectory.equals(mGesture[0]))
			return mGesture[mGesture.length - 1];
		return null;
	}
	
	public String getPreviousThirdDirectory(String text) {
		int i;
		for(i = mOperation.length - 1; i > 0; i--) {
			if (mOperation[i].equals(text)) return mOperation[i-1];
		}
		for(i = mGesture.length - 1; i > 0; i--) {
			if (mGesture[i].equals(text)) return mGesture[i-1];
		}
		return null;
	}
	
	public String getContentByThirdDirectory(String text) {
		
		return mResources.getStringArray(R.array.content)[1];
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
