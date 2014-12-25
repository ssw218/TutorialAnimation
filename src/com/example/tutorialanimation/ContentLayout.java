package com.example.tutorialanimation;

import android.content.Context;
import android.content.res.Configuration;
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
		
		LayoutParams l = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mFirstDirectory, l);
		addView(mSecondDirectory, l);
		addView(mThirdDirectory, l);
		addView(mContent, l);
		
		LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		p.gravity = Gravity.CENTER;
		p.topMargin = 100;
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
	
	long actionDownTime;
	long actionUpTime;
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
			if (actionUpY - actionDownY > 50) {
				if(DEBUG) Log.e(TAG, "gesture down");
				doScrollDown();
			} else if (actionDownY - actionUpY > 50) {
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
		
		return mContext.getResources().getStringArray(R.array.content)[1];
	}
	
	private class FirstDirectory extends TextView {

		public FirstDirectory(Context context) {
			super(context);
			setPadding(10, 20, 0, 0);
			setText("Tutorial");
			setTextColor(Color.BLACK);
			setTextSize(40);
		}
		
	}
	
	private class SecondDirectory extends TextView {

		public SecondDirectory(Context context) {
			super(context);
			setPadding(50, 0, 0, 0);
			setText("Basic operation");
			setTextColor(Color.BLACK);
			setTextSize(30);
		}
		
	}
	
	private class ThirdDirectory extends TextView {

		public ThirdDirectory(Context context) {
			super(context);
			setPadding(80, 0, 0, 0);
			setText("Delete");
			setTextColor(Color.BLACK);
			setTextSize(25);
		}
		
	}
	
	private class Content extends TextView {

		public Content(Context context) {
			super(context);
			setPadding(100, 0, 50, 0);
			setText("Deletion is the act of deleting or removal " +
					"by striking out material, such as a word or passage, " +
					"that has been removed from a body of written or printed matter.");
			setTextColor(Color.BLACK);
			setTextSize(20);
			setSingleLine(false);
		}
		
	}

}
