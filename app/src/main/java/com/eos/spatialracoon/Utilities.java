package com.eos.spatialracoon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

public class Utilities {

	@Deprecated
	public static Screen calculateScreenSize(Activity activity) {

		Screen screen;
		if (Build.VERSION.SDK_INT > 13) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			screen = new Screen(size);
		} else {
			Display display = activity.getWindowManager().getDefaultDisplay();
			screen = new Screen(display.getWidth(), display.getHeight());
		}
		return screen;
	}

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
