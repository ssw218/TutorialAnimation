package com.example.tutorialanimation;

import java.util.ArrayList;
import java.util.List;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class IndexLayout extends LinearLayout {
	private static final String TAG = "IndexLayout";
	private static final boolean DEBUG = true;
	private Context mContext;
	private Resources mResources;
	private ScrollView mIndexScrollView;
	private TextView mDirectory;
	
	public IndexLayout(Context context) {
		super(context);
		mContext = context;
		mResources = mContext.getResources();
		setOrientation(VERTICAL);
		setBackgroundColor(0xFFD0F8CE);
		LayoutParams l = (LayoutParams) getLayoutParams();
		if(l == null)
			l = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(l);
		
		mIndexScrollView = new IndexScrollView(mContext);
		mDirectory = new Directory(mContext);
		
		LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mDirectory, p);
		addView(mIndexScrollView, p);
	}
	
	private class Directory extends TextView {

		public Directory(Context context) {
			super(context);
			setText("Tutorial");
			setTextSize(30);
			setBackgroundColor(0xFF22B14C);
			setPadding(10, 10, 10, 10);
		}
		
	}

	private class IndexScrollView extends ScrollView {
		private ListLayout mOperationLayout;
		private ListLayout mGestureLayout;
		private LinearLayout mIndexView;
		
		public IndexScrollView(Context context) {
			super(context);
			// init
			mOperationLayout = new ListLayout(mContext, R.array.operation);
			mGestureLayout = new ListLayout(mContext, R.array.gesture);
			mIndexView = new LinearLayout(mContext);
			mIndexView.setOrientation(VERTICAL);
			// add view
			LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			mIndexView.addView(mOperationLayout, p);
			mIndexView.addView(mGestureLayout, p);
			addView(mIndexView, p);
			// set status
			setVerticalScrollBarEnabled(true);
		}
		
	}
	
	private class ListLayout extends LinearLayout {
		
		private static final String TAG = "ListLayout";
		private SecondDirectoryItem mTitleView;
		private List<TextView> mTextViewList;
		private boolean mShowChildren;
		
		// click child directory
		private OnClickListener mChildClickListenr = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		public ListLayout(Context context, int id) {
			super(context);
			setOrientation(VERTICAL);
			LayoutTransition layoutTransition = new LayoutTransition();
			this.setLayoutTransition(layoutTransition);
			String[] textArray = getResources().getStringArray(id);
			mTitleView = new SecondDirectoryItem(context, textArray[0]);	
			mTextViewList = new ArrayList<TextView>();
			for(int i = 1; i < textArray.length; i++) {
				TextView textView = new ThirdDirectoryItem(context, textArray[i]);
				mTextViewList.add(textView);
			}
			
			// add title to layout
			addView(mTitleView);
		}
		
		private void showList() {
			
			for(int i = 0; i < mTextViewList.size(); i++) {
				addView(mTextViewList.get(i));
			}
			mShowChildren = true;
		}
		
		private void hideList() {
			
			for(int i = 0; i < mTextViewList.size(); i++) {
				removeView(mTextViewList.get(i));
			}
			mShowChildren = false;
		}

		@Override
		public String toString() {
			if (mTextViewList.size() > 0)
				return "name: " + mTextViewList.get(0).getText();
			else
				return super.toString();
		}
		
		private class ThirdDirectoryItem extends TextView {
			// default size
			private static final int PADDING_LEFT_DEFAULT = 30;
			private static final int PADDING_TOP_DEFAULT = 0;
			private static final int PADDING_RIGHT_DEFAULT = 30;
			private static final int PADDING_BOTTOM_DEFAULT = 0;
			private static final int TEXT_SIZE_DEFAULT = 20;
			
			private OnTouchListener mTouchListener = new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == event.ACTION_DOWN) {
						v.setBackgroundColor(mResources.getColor(R.color.third_directory_item_background_focus));
						// a test case to change layout size 
						//((TextView) v).setText("abcdefghijklmnhggkjhkhkhkhkjhkh");
					} else {
						v.setBackgroundColor(mResources.getColor(R.color.third_directory_item_background_normal));
					}
					return true;
				}
				
			};
			
			public ThirdDirectoryItem(Context context, String text) {
				super(context);
				setText(text);
				setTextSize(TEXT_SIZE_DEFAULT);
				setTextColor(mResources.getColor(R.color.third_directory_item_text));
				setBackgroundColor(mResources.getColor(R.color.third_directory_item_background_normal));
				setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
				setOnClickListener(mChildClickListenr);
				setOnTouchListener(mTouchListener);
			}
			
		}
		
		private class SecondDirectoryItem extends TextView {
			private static final String TAG = "SecondDirectoryItem";
			private static final boolean DEBUG = false;
			// default size
			private static final int PADDING_LEFT_DEFAULT = 20;
			private static final int PADDING_TOP_DEFAULT = 0;
			private static final int PADDING_RIGHT_DEFAULT = 100;
			private static final int PADDING_BOTTOM_DEFAULT = 0;
			private static final int TEXT_SIZE_DEFAULT = 25;
			
			private static final int ITEM_STATE_NORMAL = 1;
			private static final int ITEM_STATE_SHOWING = 2;
			private static final int ITEM_STATE_SHOW = 3;
			private static final int ITEM_STATE_HIDING = 4;
			private static final int ITEM_STATE_HIDE = 5;
			
			private Paint mPaint;
//			private Rect mDirty;
			private int mItemState;
			
			public SecondDirectoryItem(Context context, String text) {
				super(context);
				
				setBackgroundColor(mResources.getColor(R.color.second_directory_item_background_normal));
				setText(text);
				setTextSize(TEXT_SIZE_DEFAULT);
				setTextColor(mResources.getColor(R.color.second_directory_item_text));
				setOnClickListener(mDirectoryClickListenr);
				LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				setPadding(PADDING_LEFT_DEFAULT, PADDING_TOP_DEFAULT, PADDING_RIGHT_DEFAULT, PADDING_BOTTOM_DEFAULT);
				setLayoutParams(p);
				
				mPaint = new Paint();
				mPaint.setColor(mResources.getColor(R.color.second_directory_paint));
				mItemState = ITEM_STATE_NORMAL;
//				mDirty = new Rect();
//				mDirty.left = (int) getHorizontalStartX();
//				mDirty.top = (int) getVerticalStartY();
//				mDirty.right = (int) getHorizontalStopX();
//				mDirty.bottom = (int) getVerticalStopY();
			}
			
			// click directory and animation
			private OnClickListener mDirectoryClickListenr = new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mShowChildren) {
						hideList();
						// hiding animation
						mItemState = ITEM_STATE_HIDING;
						angle = ANGLE_START_LINE;
						invalidate();
					}
					else { 
						showList();
						// showing animation
						mItemState = ITEM_STATE_SHOWING;
						angle = ANGLE_START_LINE;
						invalidate();
					}
				}
				
			};
			
			
			int angle;
			static final int ANGLE_END_LINE = 90;
			static final int ANGLE_START_LINE = 0;
			@Override 
			public void onDraw(Canvas canvas) {
				super.onDraw(canvas);
				mPaint.setStrokeWidth(getStrokeWidth());
				// 2014/12/29 Lyk
				// It is formula to compute startX, startY, stopX, stopY.
				// It is self-adaption
				switch(mItemState) {
				// This is normal state
					case ITEM_STATE_NORMAL: {	
						canvas.drawLine(getHorizontalStartX(), getHorizontalStartY(), 
								getHorizontalStopX(), getHorizontalStopY(), mPaint);
						canvas.drawLine(getVerticalStartX(), getVerticalStartY(), 
								getVerticalStopX(), getVerticalStopY(), mPaint);
						break; }
					case ITEM_STATE_SHOWING: {
						// (x, y) -> (x + r * sina, y + r * (1 - cosa))
						canvas.drawLine(getShowStartHX(), getShowStartHY(), 
								getShowStopHX(), getShowStopHY(), mPaint);
						canvas.drawLine(getShowStartVX(), getShowStartVY(), 
								getShowStopVX(), getShowStopVY(), mPaint);
						angle += 10;
						if (DEBUG) Log.e(TAG, "angle: " + angle);
						if (angle > ANGLE_END_LINE) mItemState = ITEM_STATE_SHOW;
						invalidate();
						break; }
					case ITEM_STATE_SHOW: {
						if (DEBUG) Log.e(TAG, "item showed, angle: " + angle);
						canvas.drawLine(getHorizontalStartX(), getHorizontalStartY(), 
							getHorizontalStopX(), getHorizontalStopY(), mPaint);
						break; }
					case ITEM_STATE_HIDING: {
						canvas.drawLine(getHideStartVX(), getHideStartVY(), 
								getHideStopVX(), getHideStopVY(), mPaint);
						canvas.drawLine(getHideStartHX(), getHideStartHY(), 
								getHideStopHX(), getHideStopHY(), mPaint);
//						long i = System.currentTimeMillis(); while (System.currentTimeMillis() - i < 1000);
						angle += 10;
						if (angle > ANGLE_END_LINE) mItemState = ITEM_STATE_HIDE;
						invalidate();
						break; }
					case ITEM_STATE_HIDE: { 
						canvas.drawLine(getHorizontalStartX(), getHorizontalStartY(), 
								getHorizontalStopX(), getHorizontalStopY(), mPaint);
						canvas.drawLine(getVerticalStartX(), getVerticalStartY(), 
								getVerticalStopX(), getVerticalStopY(), mPaint);
						break; }
				}
			}
			
			/*
			 * This is a picture to explain animation formula
			 * 
			 *           			 -~-+====s=DD=s==++((                      			 
             *        			  (hh==='''..  hh  ..-'((DD=...                 
             *      			--(            hh          ~hhh++               
             *   			 sss==             hh              <<h              
             *			  sz+                  hh                h~~zhh         
           	 *  	   (((=+                   hh              zz'  ---z        
           	 *		   DDD                     hh           s=s~~      z--      
             *        +~((                     hh          =+++         DD      
             *        h                        hh        ==s            ==---   
             *      (((                        hhsszh..((zz             ''sss   
             *      ++'                        hh''. DDzz                 hhh   
             *      ss                         hh  ''ss                   ==='  
             *      ss                         zzDD                       ---(  
             *      BBzzhhhzhhhhhhhhhhhzhhhzzhhNNsszzzzhhhhzhhhhhhhhhhhhzzhhh~   
			 * 
			 * */
			
			// hide stop (x1, y1) -> (x1 - r * (1 - cosa), y1 - r * sina)
			// hide stop (x2, y2) -> (x1 + r * (1 - cosa), y1 + r * sina)
			private float getHideStartVX() {
				return (float) (getHorizontalStartX() - getRadius() * (1 - Math.cos(angle)));
			}
			
			private float getHideStartVY() {
				return (float) (getHorizontalStartY() - getRadius() * Math.sin(angle));
			}
			
			private float getHideStopVX() {
				return (float) (getHorizontalStopX() + getRadius() * (1 - Math.cos(angle)));
			}
			
			private float getHideStopVY() {
				return (float) (getHorizontalStopY() + getRadius() * Math.sin(angle));
			}
			
			private float getHideStartHX() {
				return (float) (getHorizontalStartX() - getRadius() * (1 - Math.cos(2 * angle)));
			}
			
			private float getHideStartHY() {
				return (float) (getHorizontalStartY() - getRadius() * Math.sin(2 * angle));
			}
			
			private float getHideStopHX() {
				return (float) (getHorizontalStopX() + getRadius() * (1 - Math.cos(2 * angle)));
			}
			
			private float getHideStopHY() {
				return (float) (getHorizontalStopY() + getRadius() * Math.sin(2 * angle));
			}
			
			// show stop (x1, y1) -> (x1 + r * (1 - cosa), y1 - r * sina)
			// show stop (x2, y2) -> (x2 - r * (1 - cosa), y2 + r * sina)
			private float getShowStartHX() {
				return (float) (getHorizontalStartX() + getRadius() * (1 - Math.cos(2 * angle)));
			}
			
			private float getShowStartHY() {
				return (float) (getHorizontalStartY() - getRadius() * Math.sin(2 * angle));
			}
			
			private float getShowStopHX() {
				return (float) (getHorizontalStopX() - getRadius() * (1 - Math.cos(2 * angle)));
			}
			
			private float getShowStopHY() {
				return (float) (getHorizontalStopY() + getRadius() * Math.sin(2 * angle));
			}
			
			// show start (x1, y1) -> (x1 + r * sina, y1 + r * (1 - cosa))
			// show start (x2, y2) -> (x2 - r * sina, y2 - r * (1 - cosa))
			private float getShowStartVX() {
				return (float) (getVerticalStartX() + getRadius() * Math.sin(angle));
			}
			
			private float getShowStartVY() {
				return (float) (getVerticalStartY() + getRadius() * (1 - Math.cos(angle)));
			}
			
			private float getShowStopVX() {
				return (float) (getVerticalStopX() - getRadius() * Math.sin(angle));
			}
			
			private float getShowStopVY() {
				return (float) (getVerticalStopY() - getRadius() * (1 - Math.cos(angle)));
			}
			
			// base
			private float getRadius() {
				return getEngth() / 2;
			}
			
			private float getEngth() {
				return getHeight() / 3;
			}
			
			private float getDistance() {
				return (getHeight() - getEngth()) / 2;
			}
			
			private float getStrokeWidth() {
				return getEngth() / 5;
			}
			
			private float getHorizontalStartX() {
				return getWidth() - getDistance() - getEngth();
			}
			
			private float getHorizontalStartY() {
				return getHeight() / 2;
			}
			
			private float getHorizontalStopX() {
				return  getWidth() - getDistance();
			}
			
			private float getHorizontalStopY() {
				return getHeight() / 2;
			}
			
			private float getVerticalStartX() {
				return getWidth() - getDistance() - getEngth() / 2;
			}
			
			private float getVerticalStartY() {
				return 0 + getDistance();
			}
			
			private float getVerticalStopX() {
				return getWidth() - getDistance() - getEngth() / 2;
			}
			
			private float getVerticalStopY() {
				return 0 + getDistance() + getEngth();
			}
			
		}
	}
	
}
