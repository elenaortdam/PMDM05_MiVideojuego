package com.eos.spatialracoon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

	private Bitmap bmp;
	private final SurfaceHolder holder;
	private GameLoop bucle;

	private int x = 0, y = 0; //Coordenadas x e y para desplazar

	private static final int bmpInicialx = 500;
	private static final int bmpInicialy = 500;
	private static final int rectInicialx = 450;
	private static final int rectInicialy = 450;
	private static final int arcoInicialx = 50;
	private static final int arcoInicialy = 20;
	private static final int textoInicialx = 50;
	private static final int textoInicialy = 20;

	private int maxX = 0;
	private int maxY = 0;
	private int contadorFrames = 0;
	private boolean hacia_abajo = true;
	private static final String TAG = GameLoop.class.getSimpleName();
	private int touchX, touchY;
	List<Touch> touchs = new ArrayList<>();
	private boolean hasTouch;

	public Game(Activity context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);
		Display mdisp = context.getWindowManager().getDefaultDisplay();
		Point mdispSize = new Point();
		mdisp.getSize(mdispSize);
		maxX = mdispSize.x;
		maxY = mdispSize.y;
		setOnTouchListener(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// se crea la superficie, creamos el game loop

		// Para interceptar los eventos de la SurfaceView
		getHolder().addCallback(this);

		// creamos el game loop
		bucle = new GameLoop(getHolder(), this);

		// Hacer la Vista focusable para que pueda capturar eventos
		setFocusable(true);

		//comenzar el bucle
		bucle.start();

	}

	/**
	 * Este método actualiza el estado del juego. Contiene la lógica del videojuego
	 * generando los nuevos estados y dejando listo el sistema para un repintado.
	 */
	public void actualizar() {
		if (x > maxX)
			hacia_abajo = false;

		if (x == 0)
			hacia_abajo = true;

		if (hacia_abajo) {
			x = x + 1;
			y = y + 1;
		} else {
			x = x - 1;
			y = y - 1;
		}
		contadorFrames++;
	}

	/**
	 * Este método dibuja el siguiente paso de la animación correspondiente
	 */
	public void renderizar(Canvas canvas) {
		if (canvas != null) {
			Paint myPaint = new Paint();
			myPaint.setStyle(Paint.Style.STROKE);

			//Toda el canvas en rojo
			canvas.drawColor(Color.RED);

			//Dibujar muñeco de android
//			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
//			canvas.drawBitmap(bmp, bmpInicialx + x, bmpInicialy + y, null);

			//Cambiar color y tamaño de brocha
			myPaint.setStrokeWidth(10);
			myPaint.setColor(Color.BLUE);

			//dibujar rectángulo de 300x300
			canvas.drawRect(rectInicialx + x, rectInicialy + y, 300, 300, myPaint);

			//dibujar óvalo y arco
			RectF rectF = new RectF(arcoInicialx + x, arcoInicialy + y, 200, 120);
			canvas.drawOval(rectF, myPaint);
			myPaint.setColor(Color.BLACK);
			canvas.drawArc(rectF, 90, 45, true, myPaint);

			//Si ha ocurrido un toque en la pantalla "Touch", dibujar un círculo
			if (hasTouch) {
				synchronized (this) {
					for (Touch touch : touchs) {
						canvas.drawCircle(touch.getX(), touch.getY(),
										  100, myPaint);
						canvas.drawText(touch.getIndex() + "",
										touch.getX(), touch.getY(), myPaint);
					}
				}
//				canvas.drawCircle(touchX, touchY, 20, myPaint);
			}

			//dibujar un texto
			myPaint.setStyle(Paint.Style.FILL);
			myPaint.setTextSize(40);
			canvas.drawText("Frames ejecutados:" + contadorFrames, textoInicialx, textoInicialy + y, myPaint);

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "GameLoop destruido!");
		// cerrar el thread y esperar que acabe
		boolean retry = true;
		while (retry) {
			try {
				//bucle.fin();
				bucle.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				hasTouch = true;
				x = (int) event.getX();
				y = (int) event.getY();
				synchronized (this) {
					touchs.add(new Touch(x, y, event.getActionIndex()));
				}
				Log.i(Game.class.getSimpleName(), String.format("Pulsado dedo %s",
																event.getActionIndex()));
				break;
			case MotionEvent.ACTION_POINTER_UP:
				synchronized (this) {
					touchs.remove(event.getActionIndex());
				}
				break;
			case MotionEvent.ACTION_UP:
				synchronized (this) {
					touchs.remove(event.getActionIndex());
				}
				Log.i(Game.class.getSimpleName(), String.format("Soltado dedo %s ultimo",
																event.getActionIndex()));
				hasTouch = false;

				break;
		}
		return true;
	}

	/*
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

	 */

}
