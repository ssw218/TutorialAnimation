package com.example.tutorialanimation;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
	private static final String ANIMATION_BASIC_FIRST = "";
	
	public static final int ANIMATION_NORMAL = 0;
	public static final int ANIMATION_DELETE_INK_ID = 1;
	public static final int ANIMATION_ERASE_ALL_INK_ID = 2;
	public static final int ANIMATION_RETURN_INK_ID = 3;
	public static final int ANIMATION_TAB_INK_ID = 4;
	public static final int ANIMATION_DELETE_TYPESET_ID = 5;
	public static final int ANIMATION_INSERT_SPACE_TYPESET_ID = 6;
	public static final int ANIMATION_ERASE_ALL_TYPESET_ID = 7;
	public static final int ANIMATION_RETURN_TYPESER_ID = 8;
	public static final int ANIMATION_TAB_TYPESET_ID = 9;
	public static final int ANIMATION_SELECT_TEXT_TYPESET_ID = 10;
	public static final int ANIMATION_INSERT_TEXT_TYPESET_ID = 11;
	public static final int ANIMATION_BASIC_FIRST_ID = 12;
	public static final int DEFINED_VALUE = -1;
	
	private Context mContext;
	
	private String mAnimationName;
	private int mAnimationId;
	private int mCurrentAnimation;
	
	private Bitmap mNormal;
	private Bitmap mBeforeGesture;
	private Bitmap mAfterGesture;
	private Bitmap mGesture;
	
	private Matrix mNormalMatrix;
	private Matrix mGestureMatrix;
	
	private Paint mPaint;
	
	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (DEBUG) Log.e(TAG, "Animation clicked");
			if (mCurrentAnimation == ANIMATION_NORMAL) {
				startAnimation();
			}
			
		}
		
	};
	
	public void startAnimation() {
		mCurrentAnimation = mAnimationId;
		initDraw();
		invalidate();
	}
	
	public InkGestureAnimation(Context context, int index) {
		super(context);
		mAnimationId = index;
		init(context);
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
	
	final float SCALE = 1;
	
	private void init(Context context) {
		mContext = context;
		mAnimationName = findAnimationName(mAnimationId);
		mCurrentAnimation = mAnimationId;
		initDraw();
		
		setOnClickListener(mOnClickListener);
		mNormal = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_normal);
		mGesture = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_handwriting_gesture);
		mNormalMatrix = new Matrix();
		mNormalMatrix.postScale(SCALE, SCALE);
		mPaint = new Paint();
		
	}
	
	private void initDraw() {
		switch (mCurrentAnimation) {
			case ANIMATION_DELETE_INK_ID : 		initDeleteInkAnimation(); break;
			case ANIMATION_ERASE_ALL_INK_ID : 	initEraseAllInkAnimation(); break;
			case ANIMATION_RETURN_INK_ID : break;
			case ANIMATION_TAB_INK_ID : break;
			case ANIMATION_DELETE_TYPESET_ID : break;
			case ANIMATION_INSERT_SPACE_TYPESET_ID : break;
			case ANIMATION_ERASE_ALL_TYPESET_ID : break;
			case ANIMATION_RETURN_TYPESER_ID : break;
			case ANIMATION_TAB_TYPESET_ID : break;
			case ANIMATION_SELECT_TEXT_TYPESET_ID : break;
			case ANIMATION_INSERT_TEXT_TYPESET_ID : break;
			case ANIMATION_BASIC_FIRST_ID : initBasicFirstAnimation(); break;
		}
	}
	
	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InkGestureAnimation);
//		Log.e(TAG, "length : " + a.length() + " " + a.hasValue(R.styleable.InkGestureAnimation_animation));
		mAnimationId = a.getInt(R.styleable.InkGestureAnimation_animation, DEFINED_VALUE);
		if(DEBUG) Log.v(TAG, "InkGestureAnimation_animation: " + mAnimationId);
		a.recycle();
		init(context);
	}
	
	private void initBasicFirstAnimation() {
		mAfterGesture = BitmapFactory.decodeResource(getResources(), R.drawable.handwriting_first_after);
		skip = 1;
	}
	
	private void initDeleteInkAnimation() {
		mBeforeGesture = BitmapFactory.decodeResource(getResources(), R.drawable.handwriting_normal);
		mAfterGesture = BitmapFactory.decodeResource(getResources(), R.drawable.handwriting_delete_after);
		mGestureMatrix = new Matrix();
		float dx = mBeforeGesture.getWidth() * 3 / 4;
		float dy = mBeforeGesture.getHeight() * 3 / 4;
//		if (DEBUG) Log.e(TAG, "width: " + width + " height: " + height); width: 668 height: 402  500 300
		mGestureMatrix.postTranslate(dx, dy);
	}
	
	private void initEraseAllInkAnimation() {
		mGestureMatrix = new Matrix();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		switch (mCurrentAnimation) {
			case ANIMATION_NORMAL:				drawNormalAnimation(canvas); break;
			case ANIMATION_DELETE_INK_ID : 		drawDeleteInkAnimation(canvas); break;
			case ANIMATION_ERASE_ALL_INK_ID : 	drawEraseAllInkAnimation(canvas); break;
			case ANIMATION_RETURN_INK_ID : break;
			case ANIMATION_TAB_INK_ID : break;
			case ANIMATION_DELETE_TYPESET_ID : break;
			case ANIMATION_INSERT_SPACE_TYPESET_ID : break;
			case ANIMATION_ERASE_ALL_TYPESET_ID : break;
			case ANIMATION_RETURN_TYPESER_ID : break;
			case ANIMATION_TAB_TYPESET_ID: break;
			case ANIMATION_SELECT_TEXT_TYPESET_ID: break;
			case ANIMATION_INSERT_TEXT_TYPESET_ID : break;
			case ANIMATION_BASIC_FIRST_ID : drawBasicFirstAnimation(canvas); break;
		}
		//invalidate();
	}
	
	Paint circle = new Paint();
	int skip = 1;
	private void drawBasicFirstAnimation(Canvas canvas) {
		if (skip >= 5) {
			canvas.drawBitmap(mAfterGesture, mNormalMatrix, null);
			skip += 1;
			doDelay(2);
		}
		
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(2);
		canvas.drawLine(30, 335, 620, 335, mPaint);
		if (DRAW_DEBUG) Log.v(TAG, "skip: " + skip);
		if (skip == 1) {	
			skip = 2;
			doDelay(2);
			invalidate();
		}
		
		if (skip == 2 || skip == 4) {
			circle.setColor(0x55888888);
			canvas.drawCircle(100, 310, 30, circle);
			skip += 1;
			doDelay(2);
			invalidate();
		}
		
		if (skip == 3) {
			circle.setColor(0xff888888);
			canvas.drawCircle(100, 310, 40, circle);
			skip = 4;
			doDelay(2);
		}
		
		if (skip == 6) {
			stopTime = System.currentTimeMillis();
		} else if (skip > 6) {
			while(System.currentTimeMillis() - stopTime < STOP_TIME);
			doAnimationEnd();
			skip = 1;
		}
		invalidate();
	}
	
	private void doDelay(int time) {
		long i = System.currentTimeMillis(); 
		while(System.currentTimeMillis() - i < DELAY_TIME * time);
	}
	
	int width = 1;
	int velocity = 0;
	long stopTime = 0;
	final int ACCELERATION = 1;
	final int DELAY_TIME = 40;
	final int STOP_TIME = 1000;
	
	private void drawDeleteInkAnimation(Canvas canvas) {
		if(width < mGesture.getWidth()) {
			canvas.drawBitmap(mBeforeGesture, mNormalMatrix, null);
			Bitmap bitmap = Bitmap.createBitmap(mGesture, 0, 0, width, mGesture.getHeight());
			canvas.drawBitmap(bitmap, mGestureMatrix, null);
			
			doDelay(1);

		} else {
			canvas.drawBitmap(mAfterGesture, mNormalMatrix, null);
			if (width < mGesture.getWidth() + velocity) {
				stopTime = System.currentTimeMillis();
			} else {
				while(System.currentTimeMillis() - stopTime < STOP_TIME);
				width = 1;
				velocity = 0;
				doAnimationEnd();
			}
		}
//		if (!bitmap.isRecycled()) {
//			bitmap.recycle();
//			bitmap = null;
//		}
		velocity += ACCELERATION;
		width += velocity;
		invalidate();
	}
	
	private void drawEraseAllInkAnimation(Canvas canvas) {
		
	}
	
	private void drawNormalAnimation(Canvas canvas) {
		canvas.drawBitmap(mNormal, mNormalMatrix, null);
	}
	
	public void setAnimationName(String name) {
		int id;
		id = ANIMATION_DELETE_INK_ID;
		setAnimationId(id);
	}
	
	// called by ContentLayout
	public void setAnimationId(int id) {
		mAnimationId = id;
		// animation stop
		onAnimationChanged();
		initDraw();
		invalidate();
	}
	
	// called when scroll up or scroll down or choose the other third directory
	private void onAnimationChanged() {
		doAnimationEnd();
	}
	
	private void doAnimationEnd() {
		mCurrentAnimation = ANIMATION_NORMAL;
		
		Bitmap bitmap1 = mBeforeGesture;
		mBeforeGesture = null;
		if (bitmap1 != null && !bitmap1.isRecycled()) {
			bitmap1.recycle();
			bitmap1 = null;
		}
		Bitmap bitmap2 = mAfterGesture;
		mAfterGesture = null;
		if (bitmap2 != null && !bitmap2.isRecycled()) {
			bitmap2.recycle();
			bitmap2 = null;
		}
	}
	
	private String findAnimationName(int id) {
		switch (id) {
			case DEFINED_VALUE : 
				throw new IllegalArgumentException("we don't really get animation id");
			case ANIMATION_BASIC_FIRST_ID :				return ANIMATION_BASIC_FIRST;
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
		setMeasuredDimension(mNormal.getWidth(), mNormal.getHeight());
	}

}
