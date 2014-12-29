package com.example.tutorialanimation;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class InkGestureAnimation extends View {

	private static final String TAG = "InkGestureAnimation";
	
	private static final boolean DEBUG = true;
	private static final boolean DRAW_DEBUG = true;
	
	private static final String ANIMATION_DELETE_INK = "ink delete";
	private static final String ANIMATION_ERASE_ALL_INK = "ink erase all";
	private static final String ANIMATION_RETURN_INK = "ink return";
	private static final String ANIMATION_TAB_INK = "ink tab";
	private static final String ANIMATION_DELETE_TYPESET = "typeset delete";
	private static final String ANIMATION_INSERT_SPACE_TYPESET = "typeset insert space";
	private static final String ANIMATION_ERASE_ALL_TYPESET = "typeset erase all";
	private static final String ANIMATION_RETURN_TYPESER = "typeset return";
	private static final String ANIMATION_TAB_TYPESET = "typeset tab";
	private static final String ANIMATION_SELECT_TEXT_TYPESET = "typeset select text";
	private static final String ANIMATION_INSERT_TEXT_TYPESET = "typeset insert text";
	
	private static final int ANIMATION_DELETE_INK_ID = 1;
	private static final int ANIMATION_ERASE_ALL_INK_ID = 2;
	private static final int ANIMATION_RETURN_INK_ID = 3;
	private static final int ANIMATION_TAB_INK_ID = 4;
	private static final int ANIMATION_DELETE_TYPESET_ID = 5;
	private static final int ANIMATION_INSERT_SPACE_TYPESET_ID = 6;
	private static final int ANIMATION_ERASE_ALL_TYPESET_ID = 7;
	private static final int ANIMATION_RETURN_TYPESER_ID = 8;
	private static final int ANIMATION_TAB_TYPESET_ID = 9;
	private static final int ANIMATION_SELECT_TEXT_TYPESET_ID = 10;
	private static final int ANIMATION_INSERT_TEXT_TYPESET_ID = 11;
	private static final int DEFINED_VALUE = -1;
	
	private String mAnimationName;
	private int mAnimationId;
	
	private Bitmap mBeforeGesture;
	private Bitmap mAfterGesture;
	private Bitmap mGesture;
	
	private Matrix mNormalMatrix;
	private Matrix mGestureMatrix;
	
	public InkGestureAnimation(Context context, int index) {
		super(context);
		//init(context, attrs);
	}
	
	public InkGestureAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	public InkGestureAnimation(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public InkGestureAnimation(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs);
	}
	
	final float SCALE = 4 / 5;
	
	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InkGestureAnimation);
//		Log.e(TAG, "length : " + a.length() + " " + a.hasValue(R.styleable.InkGestureAnimation_animation));
		mAnimationId = a.getInt(R.styleable.InkGestureAnimation_animation, DEFINED_VALUE);
		if(DEBUG) Log.v(TAG, "InkGestureAnimation_animation: " + mAnimationId);
		a.recycle();
		
		mAnimationName = findAnimationName(mAnimationId);
		
		mGesture = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_handwriting_gesture);
		mNormalMatrix = new Matrix();
		mNormalMatrix.postScale(SCALE, SCALE);
		
		switch (mAnimationId) {
			case ANIMATION_DELETE_INK_ID : 		initDeleteInkAnimation(); break;
			case ANIMATION_ERASE_ALL_INK_ID : 	initEraseAllInkAnimation(); break;
			case ANIMATION_RETURN_INK_ID : break;
			case ANIMATION_TAB_INK_ID : break;
			case ANIMATION_DELETE_TYPESET_ID : break;
			case ANIMATION_INSERT_SPACE_TYPESET_ID : break;
			case ANIMATION_ERASE_ALL_TYPESET_ID : break;
			case ANIMATION_RETURN_TYPESER_ID : break;
			case ANIMATION_TAB_TYPESET_ID: break;
			case ANIMATION_SELECT_TEXT_TYPESET_ID: break;
			case ANIMATION_INSERT_TEXT_TYPESET_ID : break;
		}
	}
	
	private void initDeleteInkAnimation() {
		mGestureMatrix = new Matrix();
	}
	
	private void initEraseAllInkAnimation() {
		mGestureMatrix = new Matrix();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		Bitmap bitmap = mGesture = BitmapFactory.decodeResource(getResources(), R.drawable.normal);
		Matrix matrix = new Matrix();
		canvas.drawBitmap(bitmap, matrix, paint);
		switch (mAnimationId) {
			case ANIMATION_DELETE_INK_ID : 		drawDeleteInkAnimation(); break;
			case ANIMATION_ERASE_ALL_INK_ID : 	drawEraseAllInkAnimation(); break;
			case ANIMATION_RETURN_INK_ID : break;
			case ANIMATION_TAB_INK_ID : break;
			case ANIMATION_DELETE_TYPESET_ID : break;
			case ANIMATION_INSERT_SPACE_TYPESET_ID : break;
			case ANIMATION_ERASE_ALL_TYPESET_ID : break;
			case ANIMATION_RETURN_TYPESER_ID : break;
			case ANIMATION_TAB_TYPESET_ID: break;
			case ANIMATION_SELECT_TEXT_TYPESET_ID: break;
			case ANIMATION_INSERT_TEXT_TYPESET_ID : break;
		}
		//invalidate();
	}
	
	int width = 0;
	int velocity = 0;
	final int ACCELERATION = 1;
	final int DELAY_TIME = 1000;
	final int STOP_TIME = 1000;
	
	private void drawDeleteInkAnimation() {
		
	}
	
	private void drawEraseAllInkAnimation() {
		
	}
	
	private String findAnimationName(int id) {
		switch (id) {
			case DEFINED_VALUE : 
				throw new IllegalArgumentException("we don't really get animation id");
			case ANIMATION_DELETE_INK_ID : 				return ANIMATION_DELETE_INK;
			case ANIMATION_ERASE_ALL_INK_ID : 			return ANIMATION_ERASE_ALL_INK;
			case ANIMATION_RETURN_INK_ID : 				return ANIMATION_RETURN_INK;
			case ANIMATION_TAB_INK_ID : 				return ANIMATION_TAB_INK;
			case ANIMATION_DELETE_TYPESET_ID : 			return ANIMATION_DELETE_TYPESET;
			case ANIMATION_INSERT_SPACE_TYPESET_ID : 	return ANIMATION_INSERT_SPACE_TYPESET;
			case ANIMATION_ERASE_ALL_TYPESET_ID : 		return ANIMATION_ERASE_ALL_TYPESET;
			case ANIMATION_RETURN_TYPESER_ID : 			return ANIMATION_RETURN_TYPESER;
			case ANIMATION_TAB_TYPESET_ID : 			return ANIMATION_TAB_TYPESET;
			case ANIMATION_SELECT_TEXT_TYPESET_ID : 	return ANIMATION_SELECT_TEXT_TYPESET;
			case ANIMATION_INSERT_TEXT_TYPESET_ID : 	return ANIMATION_INSERT_TEXT_TYPESET;
			default: 
				throw new IllegalArgumentException("The animation name is illegal");
		
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.normal);
		setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
	}

}
