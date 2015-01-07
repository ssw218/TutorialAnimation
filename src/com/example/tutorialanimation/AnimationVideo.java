package com.example.tutorialanimation;

import java.io.IOException;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

public class AnimationVideo extends VideoView {
	private static final String TAG = "AnimationVideo";	
	private static final boolean DEBUG = true;
	
	public static final int ANIMATION_NORMAL = 0;
	public static final int ANIMATION_DELETE_INK = 1;
	public static final int ANIMATION_ERASE_ALL_INK = 2;
	public static final int ANIMATION_RETURN_INK = 3;
	public static final int ANIMATION_TAB_INK = 4;
	public static final int ANIMATION_DELETE_TYPESET = 5;
	public static final int ANIMATION_INSERT_SPACE_TYPESET = 6;
	public static final int ANIMATION_ERASE_ALL_TYPESET = 7;
	public static final int ANIMATION_RETURN_TYPESER = 8;
	public static final int ANIMATION_TAB_TYPESET = 9;
	public static final int ANIMATION_SELECT_TEXT_TYPESET = 10;
	public static final int ANIMATION_INSERT_TEXT_TYPESET = 11;
	public static final int ANIMATION_BASIC_FIRST = 12;
	public static final int ANIMATION_BASIC_SECOND = 13;
	public static final int ANIMATION_BASIC_THIRD = 14;
	public static final int ANIMATION_BASIC_FOURTH = 15;
	public static final int DEFINED_VALUE = -1;
	
	private static final String URL = "android.resource://";
	private Context mContext;
	private int mAnimation;
	private String mUrl;
	
	private OnTouchListener mPlay = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			start();
			return false;
		}
	};
	
	public AnimationVideo(Context context, int id) {
		super(context);
		mContext = context;
		mAnimation = id;
		mUrl = URL + mContext.getPackageName() + "/" + getResourceId(mAnimation);
		if (DEBUG) Log.v(TAG, "package name: " + mContext.getPackageName());
		final Uri uri = Uri.parse(mUrl);
		setVideoURI(uri);
		setZOrderOnTop(true);
		setBackground(mContext.getResources().getDrawable(R.drawable.play));
        setOnTouchListener(mPlay);
	}
	
	private int getResourceId(int animation) {
		int id = 0;
		switch (animation) {
			case ANIMATION_BASIC_FIRST : 	id = R.raw.first; break;
			case ANIMATION_BASIC_SECOND :	id = R.raw.second; break;
			case ANIMATION_BASIC_THIRD :	id = R.raw.third; break;
			case ANIMATION_BASIC_FOURTH : 	id = R.raw.fourth; break;
			default : id = R.raw.first; break;
		}
		return id;
	}
}
