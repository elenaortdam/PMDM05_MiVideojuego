package com.eos.spatialracoon;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtility {

	public static Screen calculateScreenSize(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		return new Screen(width, height);
	}

	public static int generateRandom(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}
}
