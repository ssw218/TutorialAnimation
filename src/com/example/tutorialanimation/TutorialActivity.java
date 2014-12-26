package com.example.tutorialanimation;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialActivity extends Activity {
	private static final String TAG = "TutorialActivity";
	private static final boolean DEBUG = true;
	
	private RelativeLayout mTutorialLayout;
	TextView textView;
	
	private FragmentManager mFragmentManager;
	
	private IndexFragment mIndexFragment;
	private ContentFragment mContentFragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTutorialLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mTutorialLayout);
       
        mFragmentManager = getFragmentManager();
        mIndexFragment = (IndexFragment) mFragmentManager.findFragmentById(R.id.index_fragment);
        mContentFragment = (ContentFragment) mFragmentManager.findFragmentById(R.id.content_fragment);
        
//        mIndexFragment = new IndexFragment();
//        mContentFragment = new ContentFragment();
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.index_fragment, mIndexFragment);
//        fragmentTransaction.add(R.id.content_fragment, mContentFragment);
//        fragmentTransaction.commit();
        
//        textView = (TextView) this.findViewById(R.id.textView);
//        textView.setOnClickListener(new OnClickListener() {
//        	boolean turn = true;
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(DEBUG) Log.e(TAG, "onClick");
//				
//				FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//				fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
//				if (turn) {
//					fragmentTransaction.hide(mIndexFragment);
//					fragmentTransaction.commit();
//					turn = false;
//				}
//				else {
//					fragmentTransaction.show(mIndexFragment);
//					fragmentTransaction.commit();
//					turn = true;
//				}
//			}
//        	
//        });
    }
    
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	TutorialManager layoutManager = TutorialManager.getInstance(this);
//    	layoutManager.updateScreenOrientation();
    	if (DEBUG) Log.v(TAG, "(" + mIndexFragment.getView().getLeft() + ", " + mIndexFragment.getView().getTop() + 
    			") (" + mIndexFragment.getView().getRight() + ", " + mIndexFragment.getView().getBottom() + ")");
    	if (DEBUG) Log.v(TAG, "(" + mContentFragment.getView().getLeft() + ", " + mContentFragment.getView().getTop() + 
    			") (" + mContentFragment.getView().getRight() + ", " + mContentFragment.getView().getBottom() + ")");
    	
//    	FragmentTransaction f = mFragmentManager.beginTransaction();
//        for(int next : ((RelativeLayout.LayoutParams) (mContentFragment.getView().getLayoutParams())).getRules())
//        	Log.e(TAG, "mContentFragment rule[]: " + next);
//        for(int next : ((RelativeLayout.LayoutParams) (mIndexFragment.getView().getLayoutParams())).getRules())
//        	Log.e(TAG, "mIndexFragment rule[]: " + next);
//        f.commit();
    	
//        if(layoutManager.getPortrait()) {
//    		textView.setText("Portrait");
//    	} else {
//    		textView.setText("Not Portrait");
//    	}
        TutorialManager sizeManager = TutorialManager.getInstance(this);
//        textView.setTextSize(sizeManager.getTextSize());
    }
    
}
