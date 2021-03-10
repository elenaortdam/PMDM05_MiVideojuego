package com.eos.spatialracoon;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

	private final static int MAX_FPS = 30;
	public static final int MAX_SKIPPED_FRAMES = 5;
	public static final int FRAME_TIME = 1000 / MAX_FPS;

	private Game game;
	private SurfaceHolder surfaceHolder;

	public boolean isRunning = true;
	public static final String TAG = Game.class.getSimpleName();

	GameLoop(SurfaceHolder sh, Game game) {
		this.game = game;
		this.surfaceHolder = sh;

	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Comienza el game loop");

		long tiempoComienzo;        // Tiempo en el que el ciclo comenzó
		long tiempoDiferencia;        // Tiempo que duró el ciclo
		int tiempoDormir;        // Tiempo que el thread debe dormir (<0 si vamos mal de tiempo)
		int framesASaltar;    // número de frames saltados

		tiempoDormir = 0;

		while (isRunning) {
			canvas = null;
			// bloquear el canvas para que nadie más escriba en el
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					tiempoComienzo = System.currentTimeMillis();
					framesASaltar = 0;    // resetear los frames saltados
					// Actualizar estado del juego
					game.update();
					// renderizar la imagen
					game.render(canvas);
					// Calcular cuánto tardó el ciclo
					tiempoDiferencia = System.currentTimeMillis() - tiempoComienzo;
					// Calcular cuánto debe dormir el thread antes de la siguiente iteración
					tiempoDormir = (int) (FRAME_TIME - tiempoDiferencia);

					if (tiempoDormir > 0) {
						// si sleepTime > 0 vamos bien de tiempo
						try {
							// Enviar el thread a dormir
							// Algo de batería ahorramos
							Thread.sleep(tiempoDormir);
						} catch (InterruptedException e) {
						}
					}

					while (tiempoDormir < 0 && framesASaltar < MAX_SKIPPED_FRAMES) {
						// Vamos mal de tiempo: Necesitamos ponernos al día
						game.update(); // actualizar si rendering
						tiempoDormir += FRAME_TIME;    // actualizar el tiempo de dormir
						framesASaltar++;
					}

				}
			} finally {
				// si hay excepción desbloqueamos el canvas
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			Log.d(TAG, "Nueva iteración!");
		}
		/*
		Canvas canvas;
		Log.d(TAG, "Comienza el juego");
		while (isRunning) {
			try {
				synchronized (surfaceHolder) {

				}
			} catch (Exception e) {

			}
		}

		 */
	}
}
