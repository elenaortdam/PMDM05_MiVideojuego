package com.eos.spatialracoon;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

	public final static int MAX_FPS = 20;
	public static final int MAX_SKIPPED_FRAMES = 5;
	public static final int FRAME_TIME = 1000 / MAX_FPS;

	private final Game game;
	private final SurfaceHolder surfaceHolder;

	public boolean isRunning = true;

	public void gameOver() {
		this.isRunning = false;
	}

	public static final String TAG = Game.class.getSimpleName();

	GameLoop(SurfaceHolder sh, Game game) {
		this.game = game;
		this.surfaceHolder = sh;

	}

	@Override
	public void run() {
		Canvas canvas;

		long startTime;        // Tiempo en el que el ciclo comenzó
		long differenceTime;        // Tiempo que duró el ciclo
		int sleepTime;        // Tiempo que el thread debe dormir (<0 si vamos mal de tiempo)
		int skipFrames;    // número de frames saltados

		sleepTime = 0;

		while (isRunning) {
			canvas = null;
			// bloquear el canvas para que nadie más escriba en el
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					startTime = System.currentTimeMillis();
					skipFrames = 0;    // resetear los frames saltados
					// Actualizar estado del juego
					game.update();
					// renderizar la imagen
					game.render(canvas);
					differenceTime = System.currentTimeMillis() - startTime;
					// Calcular cuánto debe dormir el thread antes de la siguiente iteración
					sleepTime = (int) (FRAME_TIME - differenceTime);

					if (sleepTime > 0) {
						// si sleepTime > 0 vamos bien de tiempo
						try {
							// Enviar el thread a dormir
							// Algo de batería ahorramos
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
						}
					}

					while (sleepTime < 0 && skipFrames < MAX_SKIPPED_FRAMES) {
						// Vamos mal de tiempo: Necesitamos ponernos al día
						game.update(); // actualizar si rendering
						sleepTime += FRAME_TIME;    // actualizar el tiempo de dormir
						skipFrames++;
					}

				}
			} finally {
				// si hay excepción desbloqueamos el canvas
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
