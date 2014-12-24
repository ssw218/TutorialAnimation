package com.example.tutorialanimation;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class IndexFragment extends Fragment {
	private static final String TAG = "IndexFragment";
	private static final boolean DEBUG = true;
	
	private ListLayout mOperationLayout;
	private ListLayout mGestureLayout;
	private LinearLayout mIndexLayout;
	private LinearLayout mIndexView;
	
	public IndexFragment() {
		super();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mOperationLayout = new ListLayout(getActivity(), R.array.operation);
		mGestureLayout = new ListLayout(getActivity(), R.array.gesture);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mIndexLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.index, null);
		mIndexView =  (LinearLayout) mIndexLayout.findViewById(R.id.index_view);
		mIndexView.addView(mOperationLayout, p);
		mIndexView.addView(mGestureLayout, p);
		return mIndexLayout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
}
