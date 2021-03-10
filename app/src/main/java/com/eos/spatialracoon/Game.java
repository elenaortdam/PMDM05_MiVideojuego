package com.eos.spatialracoon;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

	private SurfaceHolder surfaceHolder;
	private GameLoop gameLoop;
	private int score;

	public Game(Context context) {
		super(context);
//TODO: elena ¿poner sonidos de fondo?
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	private void startGame() {
		//Posición 0 del personaje
		//Cargar el sprite del marcador
		//Enemigos que vayan apareciendo
		//¿Nivel infinito? o ¿Dificultad del juego?
		//  Si es el segundo hacer en la pantalla principal escoger nivel
	}

	@Override
	public void surfaceCreated(@NonNull SurfaceHolder holder) {
		getHolder().addCallback(this);
		//Falta el getHolder() y el this
		gameLoop = new GameLoop(holder, this);
		setFocusable(true);
		gameLoop.start();
	}

	/**
	 * Contiene la lógica del juego
	 */
	public void update() {

	}

	public void render(Canvas canvas) {

	}

	@Override
	public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
		Log.d(Game.class.getSimpleName(), "Game over");
		//Cerrar el threar
		boolean retry = true;
		//TODO: elena revisar

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
