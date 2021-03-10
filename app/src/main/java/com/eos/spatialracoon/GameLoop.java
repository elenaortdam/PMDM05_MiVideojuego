package com.eos.spatialracoon;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

	private final static int MAX_FPS = 30;
	public static final int MAX_SKIPPED_FRAMES = 5;
	public static final int FRAME_TIME = 1000 / MAX_FPS;

	private final Game game;
	private final SurfaceHolder surfaceHolder;

	public boolean isRunning = true;
	public static final String TAG = Game.class.getSimpleName();

	GameLoop(SurfaceHolder sh, Game game) {
		this.game = game;
		this.surfaceHolder = sh;

	}

	@Override
	public void run() {

		Canvas canvas;
		Log.d(TAG, "Comienza el juego");
		while (isRunning) {
			try {
				synchronized (surfaceHolder) {

				}
			} catch (Exception e) {

			}
		}
	}
}
