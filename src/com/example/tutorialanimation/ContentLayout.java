package com.example.tutorialanimation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * Manager content of tutorial, controll animation with AnimationManager
 * Judge model what is it
 * 1 portrait
 * 		maybe two
 * 2 land
 * 		only one screen
 * */
public class ContentLayout extends LinearLayout {
	private static final String TAG = "ContentLayout";
	private static final boolean DEBUG = true;
	
	private static final int MODEL_ONE_SCREEN = 1;
	private static final int MODEL_TWO_SCREEN = 2;
	
	private Context mContext;
	
	private int mModel;
	private int mCurrentIndex;
	
	private TextView mFirstDirectory;
	private TextView mSecondDirectory;
	private TextView mThirdDirectory;
	private TextView mContent;
	// maybe a operation animation else
	private InkGestureAnimation mAnimation;
	// if two screen model
	private TextView mThirdDirectoryExtra;
	private TextView mContentExtra;
	
	public ContentLayout(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	private void init() {
		initLayoutParams();
		initChildren();
	}
	
	private void initLayoutParams() {
		LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}	
		setBackgroundColor(Color.WHITE);
		setOrientation(VERTICAL);
		mModel = judgeModel();
	}
	
	private void initChildren() {
		mFirstDirectory = new FirstDirectory(mContext);
		mSecondDirectory = new SecondDirectory(mContext);
		mThirdDirectory = new ThirdDirectory(mContext);
		mContent = new Content(mContext);
		mAnimation = new InkGestureAnimation(mContext, mCurrentIndex);
		
		addView(mFirstDirectory);
		addView(mSecondDirectory);
		addView(mThirdDirectory);
		addView(mContent);
		addView(mAnimation);

		mThirdDirectoryExtra = new ThirdDirectory(mContext);
		mContentExtra = new Content(mContext);
		
		if (mModel == MODEL_ONE_SCREEN) {
			
		} else if (mModel == MODEL_TWO_SCREEN) {
			addView(mThirdDirectoryExtra);
			addView(mContentExtra);
		}
	}
	
	private int judgeModel() {
//		if () return  MODEL_ONE_SCREEN;
//		} else if () 
			return  MODEL_ONE_SCREEN;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		int model = judgeModel();
		if(mModel != model && mModel == MODEL_ONE_SCREEN && model == this.MODEL_TWO_SCREEN) {
			// add extra text view, task: judge text index
			
			addView(mThirdDirectoryExtra);
			addView(mContentExtra);
		} else if (mModel != model && mModel == MODEL_TWO_SCREEN && model == this.MODEL_ONE_SCREEN) {
			// remove extra text view
			removeView(mThirdDirectoryExtra);
			removeView(mContentExtra);
		}
		// update size: text and animation
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(DEBUG) Log.e(TAG, event.toString());
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			
		}
		return super.onTouchEvent(event);
	}
	
	private class FirstDirectory extends TextView {

		public FirstDirectory(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	private class SecondDirectory extends TextView {

		public SecondDirectory(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	private class ThirdDirectory extends TextView {

		public ThirdDirectory(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	private class Content extends TextView {

		public Content(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
	}

}
