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
	public static final int ANIMATION_BASIC_FIRST = 1;
	public static final int ANIMATION_BASIC_SECOND = 2;
	public static final int ANIMATION_BASIC_THIRD = 3;
	public static final int ANIMATION_BASIC_FOURTH = 4;
	public static final int ANIMATION_DELETE = 5;
	public static final int ANIMATION_INSERT_SPACE = 6;
	public static final int ANIMATION_SELECT_TEXT = 7;
	public static final int ANIMATION_RETURN = 8;
	public static final int ANIMATION_BACKSPACE = 9;
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
		setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        setOnTouchListener(mPlay);
        seekTo(1);
	}
	
	private int getResourceId(int animation) {
		int id = 0;
		switch (animation) {
			case ANIMATION_BASIC_FIRST : 	id = R.raw.first; break;
			case ANIMATION_BASIC_SECOND :	id = R.raw.second; break;
			case ANIMATION_BASIC_THIRD :	id = R.raw.third; break;
			case ANIMATION_BASIC_FOURTH : 	id = R.raw.fourth; break;
			case ANIMATION_DELETE :			id = R.raw.delete; break;
			case ANIMATION_INSERT_SPACE :	id = R.raw.insert_space; break;
			default : id = R.raw.first; break;
		}
		return id;
	}
}
