package com.eos.spatialracoon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
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
		Log.i(Utilities.class.getSimpleName(), String.format("Pantalla: alto %d, ancho %d",
															 screen.getWidth(), screen.getHeight()));

		return screen;
	}

	public static Screen calculateScreenSize(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		Screen screen = new Screen(width, height);
//		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

		Log.i(Utilities.class.getSimpleName(), String.format("Pantalla: alto %d, ancho %d",
															 screen.getWidth(), screen.getHeight()));

		return screen;
	}
}
