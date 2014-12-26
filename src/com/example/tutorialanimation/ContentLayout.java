package com.example.tutorialanimation;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.Gravity;
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
	private Resources mResources;
	
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
		mResources = getResources();
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
		setBackgroundResource(R.color.content_layout_background);
		setOrientation(VERTICAL);
		mModel = judgeModel();
	}
	
	private void initChildren() {
		mFirstDirectory = new FirstDirectory(mContext);
		mSecondDirectory = new SecondDirectory(mContext);
		mThirdDirectory = new ThirdDirectory(mContext);
		mContent = new Content(mContext);
		mAnimation = new InkGestureAnimation(mContext, mCurrentIndex);
		
		LayoutParams l = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mFirstDirectory, l);
		addView(mSecondDirectory, l);
		addView(mThirdDirectory, l);
		addView(mContent, l);
		
		LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		p.gravity = Gravity.CENTER;
		p.topMargin = mResources.getInteger(R.integer.animation_top_margin);
		addView(mAnimation, p);

		mThirdDirectoryExtra = new ThirdDirectory(mContext);
		mContentExtra = new Content(mContext);
		
		if (mModel == MODEL_ONE_SCREEN) {
			
		} else if (mModel == MODEL_TWO_SCREEN) {
			addView(mThirdDirectoryExtra, l);
			addView(mContentExtra, l);
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
	
	static final int SCROLL_DISTANCE = 50;
	float actionDownY;
	float actionUpY;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled = super.onTouchEvent(event);
		//if(DEBUG) Log.e(TAG, event.toString());
		// scroll up and scroll down
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			actionDownY = event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			actionUpY = event.getY();
			if (actionUpY - actionDownY > SCROLL_DISTANCE) {
				if(DEBUG) Log.e(TAG, "gesture down");
				doScrollDown();
			} else if (actionDownY - actionUpY > SCROLL_DISTANCE) {
				if(DEBUG) Log.e(TAG, "gesture up");
				doScrollUp();
			}
		}
		return true;
	}
	
	private void doScrollDown() {
		
	}
	
	private void doScrollUp() {
		
	}
	
	// called by ContentFragment
	public void onDirectoryClick(TextView view) {
		if(false) // if neccessary
			mSecondDirectory.setText("");
		mThirdDirectory.setText(view.getText());
		mContent.setText(getContentByText(view.getText().toString()));
		
		// if necessary, change extra text
		if(mModel == MODEL_TWO_SCREEN) {
			mThirdDirectoryExtra.setText("");
			mContentExtra.setText(getContentByText(""));
		}
	}
	
	private String getContentByText(String text) {
		
		return mResources.getStringArray(R.array.content)[1];
	}
	
	private class FirstDirectory extends TextView {
		// default size
		private static final int PADDING_LEFT_DEFAULT = 20;
		private static final int PADDING_TOP_DEFAULT = 0;
		private static final int PADDING_RIGHT_DEFAULT = 0;
		private static final int PADDING_BOTTOM_DEFAULT = 0;
		private static final int TEXT_SIZE_DEFAULT = 40;
		
		public FirstDirectory(Context context) {
			super(context);
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
			setText(mResources.getString(R.string.first_directory_text_default));
			setTextColor(mResources.getColor(R.color.first_directory_text));
			setTextSize(TEXT_SIZE_DEFAULT);
		}
		
	}
	
	private class SecondDirectory extends TextView {
		// default size
		private static final int PADDING_LEFT_DEFAULT = 50;
		private static final int PADDING_TOP_DEFAULT = 0;
		private static final int PADDING_RIGHT_DEFAULT = 0;
		private static final int PADDING_BOTTOM_DEFAULT = 0;
		private static final int TEXT_SIZE_DEFAULT = 30;
		
		public SecondDirectory(Context context) {
			super(context);
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
			setText(mResources.getString(R.string.second_directory_text_default));
			setTextColor(mResources.getColor(R.color.second_directory_text));
			setTextSize(TEXT_SIZE_DEFAULT);
		}
		
	}
	
	private class ThirdDirectory extends TextView {
		// default size
		private static final int PADDING_LEFT_DEFAULT = 80;
		private static final int PADDING_TOP_DEFAULT = 0;
		private static final int PADDING_RIGHT_DEFAULT = 0;
		private static final int PADDING_BOTTOM_DEFAULT = 0;
		private static final int TEXT_SIZE_DEFAULT = 25;
		
		public ThirdDirectory(Context context) {
			super(context);
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
			setText(mResources.getString(R.string.third_directory_text_default));
			setTextColor(mResources.getColor(R.color.third_directory_text));
			setTextSize(TEXT_SIZE_DEFAULT);
		}
		
	}
	
	private class Content extends TextView {
		// default size
		private static final int PADDING_LEFT_DEFAULT = 100;
		private static final int PADDING_TOP_DEFAULT = 0;
		private static final int PADDING_RIGHT_DEFAULT = 50;
		private static final int PADDING_BOTTOM_DEFAULT = 0;
		private static final int TEXT_SIZE_DEFAULT = 20;
		
		public Content(Context context) {
			super(context);
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
			setText(mResources.getString(R.string.content_text_default));
			setTextColor(mResources.getColor(R.color.content_text));
			setTextSize(TEXT_SIZE_DEFAULT);
			setSingleLine(false);
		}
		
	}

}
