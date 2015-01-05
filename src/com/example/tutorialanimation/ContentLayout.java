package com.example.tutorialanimation;

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
		mModel = judgeModel();
	}
	
	private void initChildren() {
		mFirstDirectory = new FirstDirectory(mContext);
		mSecondDirectory = new SecondDirectory(mContext);
		mThirdDirectory = new ThirdDirectory(mContext);
		mContent = new Content(mContext);
		mCurrentIndex = InkGestureAnimation.ANIMATION_BASIC_FIRST_ID;
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
					doScrollDown();
				} else if (actionDownY - actionUpY > SCROLL_DISTANCE) {
					if(DEBUG) Log.e(TAG, "gesture up");
					doScrollUp();
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
	
	private void doScrollDown() {
		if (mTutorialManager.isFirstSecondDirectory(mSecondDirectory.getText().toString()) && 
				mTutorialManager.isFirstThirdDirectory(mThirdDirectory.getText().toString()))
			return ;
		// up 
		if (mTutorialManager.isLastSecondDirectory(mSecondDirectory.getText().toString()) && 
				mTutorialManager.isFirstThirdDirectory(mThirdDirectory.getText().toString())) {
			mSecondDirectory.setText(mTutorialManager.getFirstSecondDirectory());
			mThirdDirectory.setText(mTutorialManager.getLastThirdDirectory(mSecondDirectory.getText().toString()));
			mContent.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectory.getText().toString()));
			if (mModel == MODEL_TWO_SCREEN) {
				mThirdDirectoryExtra.setText("");
				mContentExtra.setText("");
			}
			return ;
		}
		// normal 
		if (mModel == MODEL_TWO_SCREEN) {
			mThirdDirectoryExtra.setText(mThirdDirectory.getText().toString());
			mContentExtra.setText(mContent.getText().toString());
		}
		mThirdDirectory.setText(mTutorialManager.getPreviousThirdDirectory(mThirdDirectory.getText().toString()));
		mContent.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectory.getText().toString()));
		mAnimation.setAnimationName(mThirdDirectory.getText().toString());
	}
	
	private void doScrollUp() {
		if (mTutorialManager.isLastSecondDirectory(mSecondDirectory.getText().toString()) && 
				mTutorialManager.isLastThirdDirectory(mThirdDirectory.getText().toString())) 
			return ;
		// down 
		if (mTutorialManager.isFirstSecondDirectory(mSecondDirectory.getText().toString()) && 
				mTutorialManager.isLastThirdDirectory(mThirdDirectory.getText().toString())) {
			mSecondDirectory.setText(mTutorialManager.getLastSecondDirectory());
			mThirdDirectory.setText(mTutorialManager.getFirstThirdDirectory(mSecondDirectory.getText().toString()));
			mContent.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectory.getText().toString()));
			if (mModel == MODEL_TWO_SCREEN) {
				mThirdDirectoryExtra.setText(mTutorialManager.getNextThirdDirectory(mThirdDirectory.getText().toString()));
				mContentExtra.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectoryExtra.getText().toString()));
			}
			return ;
		}
		// normal
		if (mModel == MODEL_TWO_SCREEN) {
			mThirdDirectory.setText(mThirdDirectoryExtra.getText().toString());
			mContent.setText(mContentExtra.getText().toString());
			mThirdDirectoryExtra.setText(mTutorialManager.getNextThirdDirectory(mThirdDirectory.getText().toString()));
			mContentExtra.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectoryExtra.getText().toString()));
		} else {
			mThirdDirectory.setText(mTutorialManager.getNextThirdDirectory(mThirdDirectory.getText().toString()));
			mContent.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectoryExtra.getText().toString()));
		}
		mAnimation.setAnimationName(mThirdDirectory.getText().toString());
	}
	
	// called by ContentFragment
	public void onDirectoryClick(TextView view) {
		if (view.getText().equals(mThirdDirectory.getText())) return;
		
		String secondDirectory = mTutorialManager.findScecondDirectory(view.getText().toString());
		if (!mSecondDirectory.getText().equals(secondDirectory)) // if neccessary
			mSecondDirectory.setText(secondDirectory);
		mThirdDirectory.setText(view.getText());
		mContent.setText(mTutorialManager.getContentByThirdDirectory(view.getText().toString()));
		
		// if necessary, change extra text
		if (mModel == MODEL_TWO_SCREEN) {
			if (mTutorialManager.isLastSecondDirectory(view.getText().toString()) && 
					mTutorialManager.isLastThirdDirectory(view.getText().toString())) {
				mThirdDirectoryExtra.setText("");
				mContentExtra.setText("");
			} else if (mTutorialManager.isFirstSecondDirectory(view.getText().toString()) &&
					mTutorialManager.isLastThirdDirectory(view.getText().toString())) {
				mThirdDirectoryExtra.setText("");
				mContentExtra.setText("");
			} else {
				mThirdDirectoryExtra.setText(mTutorialManager.getNextThirdDirectory(view.getText().toString()));
				mContentExtra.setText(mTutorialManager.getContentByThirdDirectory(mThirdDirectoryExtra.getText().toString()));
			}
		}
		mAnimation.startAnimation();
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
