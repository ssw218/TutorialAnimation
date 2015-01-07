package com.example.tutorialanimation;

import java.util.ArrayList;

import android.animation.LayoutTransition;
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
import android.widget.RelativeLayout;
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
	private TutorialManager mTutorialManager;
	
	private int mModel;
	private int mCurrentIndex;
	
	private FirstDirectory mFirstDirectory;
	private SecondDirectory mBasicDirectory;
	private SecondDirectory mGestureDirectory;
	
	private ArrayList<ThirdDirectory> mBasicThirdDirectorys;
	private ArrayList<ThirdDirectory> mGestureThirdDirectorys; 
	
	public ContentLayout(Context context) {
		super(context);
		mContext = context;
		mResources = getResources();
		mTutorialManager = TutorialManager.getInstance(mContext);
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
		LayoutTransition layoutTransition = new LayoutTransition();
		this.setLayoutTransition(layoutTransition);
	}
	
	private void initChildren() {
		mBasicThirdDirectorys = new ArrayList<ThirdDirectory>();
		mGestureThirdDirectorys = new ArrayList<ThirdDirectory>();
		
		LayoutParams l = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutParams p = new LayoutParams(TutorialManager.dipToPx(mContext, 352), TutorialManager.dipToPx(mContext, 288));
		//p.topMargin = mResources.getInteger(R.integer.animation_top_margin);
		p.gravity = Gravity.CENTER;
		
		mFirstDirectory = new FirstDirectory(mContext);
		addView(mFirstDirectory, l);
		mBasicDirectory = new SecondDirectory(mContext, mTutorialManager.getFirstSecondDirectory());
		addView(mBasicDirectory, l);
		
		int length = 0;
		for (int i = 1; i < mTutorialManager.getBasicIndex(); i++, length++) {
			ThirdDirectory thirdDirectory = new ThirdDirectory(mContext, mTutorialManager.getBasicTD(i));
			mBasicThirdDirectorys.add(thirdDirectory);
			Content content = new Content(mContext, mTutorialManager.getBasicContent(i - 1));	
			AnimationVideo animation = new AnimationVideo(mContext, AnimationVideo.ANIMATION_BASIC_FIRST + length);
			addView(thirdDirectory, l);
			addView(content, l);
			addView(animation, p);
		}
		
		mGestureDirectory = new SecondDirectory(mContext, mTutorialManager.getLastSecondDirectory());
		addView(mGestureDirectory, l);
		
		for (int j = 1; j < mTutorialManager.getGestureIndex(); length++, j++) {
			ThirdDirectory thirdDirectory = new ThirdDirectory(mContext, mTutorialManager.getGestureTD(j));
			mGestureThirdDirectorys.add(thirdDirectory);
			Content content = new Content(mContext, mTutorialManager.getGestureContent(j - 1));	
			AnimationVideo animation = new AnimationVideo(mContext, AnimationVideo.ANIMATION_BASIC_FIRST + length);
			addView(thirdDirectory, l);
			addView(content, l);
			addView(animation, p);
		}

	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
	}
	
	static final int SCROLL_DISTANCE = 100;
	float actionDownY;
	float actionUpY;
	float actionDownX;
	float actionUpX;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled = super.onTouchEvent(event);
		//if(DEBUG) Log.e(TAG, event.toString());
		// scroll up and scroll down
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			actionDownY = event.getY();
			actionDownX = event.getX();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			actionUpY = event.getY();
			actionUpX = event.getX();
			if (Math.abs(actionDownX - actionUpX) < SCROLL_DISTANCE) {
				if (actionUpY - actionDownY > SCROLL_DISTANCE) {
					if(DEBUG) Log.e(TAG, "gesture down");
					//doScrollDown();
				} else if (actionDownY - actionUpY > SCROLL_DISTANCE) {
					if(DEBUG) Log.e(TAG, "gesture up");
					//doScrollUp();
				}
			} else if (Math.abs(actionDownY - actionUpY) < SCROLL_DISTANCE) {
				if (actionUpX - actionDownX > SCROLL_DISTANCE) { // add scroll left and scroll right
					if(DEBUG) Log.e(TAG, "gesture right");
					doScrollRight();
				} else if (actionDownX - actionUpX > SCROLL_DISTANCE) {
					if(DEBUG) Log.e(TAG, "gesture left");
					doScrollLeft();
				}
			}
		}
		return true;
	}
	
	public interface ScrollEvent{
		public void onScrollRight();
		public void onScrollLeft();
	}
	
	public void setScrollEvent(ScrollEvent scrollEvent) {
		mScrollEvent = scrollEvent;
	}
	
	private ScrollEvent mScrollEvent;
	
	private void doScrollRight() {
		// show index, called activity do the thing
		mScrollEvent.onScrollRight();
	}
	
	private void doScrollLeft() {
		//hide index, called activity do the thing
		mScrollEvent.onScrollLeft();
	}

	// called by ContentFragment
	public int getThirdTextViewTop(TextView view) {
		int top = 0;
		for(TextView next: mBasicThirdDirectorys) {
			if (view.getText().equals(next.getText())) {
				top = next.getTop();
				break;
			}
		}
		
		for(TextView next: mGestureThirdDirectorys) {
			if (view.getText().equals(next.getText())) {
				top = next.getTop();
				break;
			}
		}
		return top;
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
			setText(mResources.getString(R.string.second_directory_text_default));
			init();
		}
		
		public SecondDirectory(Context context, String text) {
			super(context);
			setText(text);
			init();
		}
		
		private void init() {
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
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
			setText(mResources.getString(R.string.third_directory_text_default));
			init();
		}
		
		public ThirdDirectory(Context context, String text) {
			super(context);
			setText(text);
			init();
		}
		
		private void init() {
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
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
			setText(mResources.getString(R.string.content_text_default));
			init();
		}
		
		public Content(Context context, String text) {
			super(context);
			setText(text);
			init();
		}
		
		private void init() {
			setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
			setTextColor(mResources.getColor(R.color.content_text));
			setTextSize(TEXT_SIZE_DEFAULT);
			setSingleLine(false);
		}
		
	}

}
