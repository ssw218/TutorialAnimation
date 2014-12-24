package com.example.tutorialanimation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListLayout extends LinearLayout {
	
	private static final String TAG = "ListLayout";
	private DirectoryView mTitleView;
	private List<TextView> mTextViewList;
	private boolean mShowChildren;
	
	// click directory
	private OnClickListener mDirectoryClickListenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mShowChildren) 
				hideList();
			else 
				showList();
		}
		
	};
	// click child directory
	private OnClickListener mChildClickListenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private OnTouchListener mTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == event.ACTION_DOWN) {
				v.setBackgroundColor(0xFF259B24);
			} else {
				v.setBackgroundColor(0xFFD0F8CE);
			}
			return true;
		}
		
	};
	
	public ListLayout(Context context, int id) {
		super(context);
		setOrientation(VERTICAL);
		String[] textArray = getResources().getStringArray(id);
		mTitleView = new DirectoryView(context, textArray[0]);	
		mTextViewList = new ArrayList<TextView>();
		for(int i = 1; i < textArray.length; i++) {
			TextView textView = new TextView(context);
			textView.setText(textArray[i]);
			textView.setTextSize(20);
			textView.setBackgroundColor(0xFFD0F8CE);
			textView.setPadding(30, 0, 30, 0);
			textView.setOnClickListener(mChildClickListenr);
			textView.setOnTouchListener(mTouchListener);
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
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	public String toString() {
		if (mTextViewList.size() > 0)
			return "name: " + mTextViewList.get(0).getText();
		else
			return super.toString();
	}
	
	class DirectoryView extends TextView {
		
		private Paint mPaint;

		public DirectoryView(Context context, String text) {
			super(context);
			setBackgroundColor(0xFF259B24);
			setText(text);
			setTextSize(25);
			setTextColor(0xFFFFFFFF);
			setOnClickListener(mDirectoryClickListenr);
			LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			setPadding(20, 0, 100, 0);
			setLayoutParams(p);
			mPaint = new Paint();
			mPaint.setColor(0xFFFFFFFF);
		}
		
		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
		
		@Override 
		public void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			int width = getWidth();
			int height = getHeight();
			int l = height / 3;
			int ds = (height - l) / 2;
			mPaint.setStrokeWidth(l / 5);
			canvas.drawLine(width - ds - l, height / 2, width - ds, height / 2, mPaint);
			canvas.drawLine(width - ds - l / 2, 0 + ds, width - ds - l / 2, 0 + ds + l, mPaint);
		}
		
	}
}
