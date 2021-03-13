package com.eos.spatialracoon;

import android.util.Log;
import android.view.MotionEvent;

import static android.content.ContentValues.TAG;

public class Gesture extends android.view.GestureDetector.SimpleOnGestureListener {

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.i(TAG, "onSingleTapUp");
		return super.onSingleTapUp(e);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.i(TAG, "onLongPress");
		super.onLongPress(e);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.i(TAG, "onScroll");
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.i(TAG, "onFling");
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.i(TAG, "onShowPress");
		super.onShowPress(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.i(TAG, "onDown");
		return super.onDown(e);
	}
}
