package com.eos.spatialracoon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.eos.spatialracoon.constants.CharacterName;
import com.eos.spatialracoon.level.Level;
import com.eos.spatialracoon.level.LevelSetting;
import com.eos.spatialracoon.sprites.buttons.CircleButton;
import com.eos.spatialracoon.sprites.buttons.ControlButton;
import com.eos.spatialracoon.sprites.buttons.SquareButton;
import com.eos.spatialracoon.sprites.buttons.TriangleButton;
import com.eos.spatialracoon.sprites.buttons.XButton;
import com.eos.spatialracoon.sprites.characters.Enemy;
import com.eos.spatialracoon.sprites.characters.GameCharacter;
import com.eos.spatialracoon.sprites.characters.Raccoon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

	private Bitmap bmp;
	private final SurfaceHolder holder;
	private GameLoop bucle;
	private final int[] availableBackgrounds = {R.drawable.bg1};
	private final Bitmap[] backgroundImages = new Bitmap[availableBackgrounds.length];
	private final List<ControlButton> buttons = new ArrayList<>();
	private final List<GameCharacter> characters = new ArrayList<>();
	private final LevelSetting level;

	private Bitmap background;

	private final int x = 0;
	private final int y = 0;

	private static final int rectInicialx = 450;
	private static final int rectInicialy = 450;
	private static final int arcoInicialx = 50;
	private static final int arcoInicialy = 20;
	private static final int textoInicialx = 50;
	private static final int textoInicialy = 20;

	private int contadorFrames = 0;
	private final boolean hacia_abajo = true;
	private static final String TAG = GameLoop.class.getSimpleName();
	private int touchX, touchY;
	List<Touch> touchs = new ArrayList<>();
	private boolean hasTouch;
	//	private final GestureDetector gestureDetector;
	private final Screen screen;

	public Game(Activity activity) {
		super(activity);
		holder = getHolder();
		holder.addCallback(this);

//		gestureDetector = new GestureDetector(getContext(), new Gesture());
//		this.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (gestureDetector.onTouchEvent(event)) return false;
//
//				return false;
//			}
//		});
		this.screen = Utilities.calculateScreenSize(activity);
		loadBackground();
		loadControlButtons();
		GameCharacter racoon = new Raccoon(this.getContext());
		characters.add(racoon);
		setOnTouchListener(this);
		Level level = new Level();
		this.level = level.getLevelSettings(1);
		createEnemy();
		System.out.println("Algo");
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

	public void loadBackground() {
		//cargamos todos los fondos en un array
		for (int i = 0; i < backgroundImages.length; i++) {
			background = BitmapFactory.decodeResource(getResources(),
													  availableBackgrounds[i]);
			if (backgroundImages[i] == null)
				backgroundImages[i] = Bitmap.createScaledBitmap(background,
																screen.getWidth(),
																screen.getHeight(),
																true);
			background.recycle();
		}
	}

	public void loadControlButtons() {
		ControlButton square = new SquareButton(this.getContext());
		ControlButton circle = new CircleButton(this.getContext());
		ControlButton x = new XButton(this.getContext());
		ControlButton triangle = new TriangleButton(this.getContext());
		this.buttons.add(square);
		this.buttons.add(circle);
		this.buttons.add(x);
		this.buttons.add(triangle);
	}

	public void createEnemy() {
		for (int i = 0; i < level.getInitialEnemies(); i++) {
			characters.add(new Enemy(this, level.getLevel()));
		}
	}

	/**
	 * Este método actualiza el estado del juego. Contiene la lógica del videojuego
	 * generando los nuevos estados y dejando listo el sistema para un repintado.
	 */
	public void update() {

//		createEnemy();
		contadorFrames++;
	}

	/**
	 * Este método dibuja el siguiente paso de la animación correspondiente
	 */
	public void render(Canvas canvas) {
		if (canvas != null) {
			Paint myPaint = new Paint();
			myPaint.setStyle(Paint.Style.STROKE);
			myPaint.setColor(Color.WHITE);
			canvas.drawBitmap(backgroundImages[0], 0, -1, null);
			for (ControlButton button : buttons) {
				button.draw(canvas, myPaint);
			}
			for (GameCharacter character : characters) {
				character.draw(canvas, myPaint);
			}

//			myPaint.setStyle(Paint.Style.STROKE);

			//Toda el canvas en rojo
//			canvas.drawColor(Color.RED);

			//Dibujar muñeco de android
//			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
//			canvas.drawBitmap(bmp, bmpInicialx + x, bmpInicialy + y, null);

			//Cambiar color y tamaño de brocha
//			myPaint.setStrokeWidth(10);
//			myPaint.setColor(Color.BLUE);

			//dibujar rectángulo de 300x300
//			canvas.drawRect(rectInicialx + x, rectInicialy + y, 300, 300, myPaint);

			//dibujar óvalo y arco
//			RectF rectF = new RectF(arcoInicialx + x, arcoInicialy + y, 200, 120);
//			canvas.drawOval(rectF, myPaint);
//			myPaint.setColor(Color.BLACK);
//			canvas.drawArc(rectF, 90, 45, true, myPaint);

			//Si ha ocurrido un toque en la pantalla "Touch", dibujar un círculo
			if (hasTouch) {
				synchronized (this) {
					for (Touch touch : touchs) {
//						canvas.drawCircle(touch.getX(), touch.getY(),
//										  100, myPaint);
//						canvas.drawText(touch.getIndex() + "",
//										touch.getX(), touch.getY(), myPaint);
					}
				}
//				canvas.drawCircle(touchX, touchY, 20, myPaint);
			}

			//dibujar un texto
//			myPaint.setStyle(Paint.Style.FILL);
//			myPaint.setTextSize(40);
//			canvas.drawText("Frames ejecutados:" + contadorFrames, textoInicialx, textoInicialy + y, myPaint);

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

		int index;

		float x, y;

		index = event.getActionIndex();

		x = event.getX();
		y = event.getY();

		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				hasTouch = true;
				x = (int) event.getX();
				y = (int) event.getY();
				synchronized (this) {
					touchs.add(new Touch((int) x, (int) y, event.getActionIndex()));
				}
				Log.i(Game.class.getSimpleName(), String.format("Pulsado dedo %s",
																event.getActionIndex()));
				break;
			case MotionEvent.ACTION_POINTER_UP:
				synchronized (this) {
					touchs.remove(index);
					for (int i = 0; i < 3; i++)
						buttons.get(i).checkButtonRelased(touchs);
				}
				break;
			case MotionEvent.ACTION_UP:
				synchronized (this) {
					touchs.remove(event.getActionIndex());
				}
				Log.i(Game.class.getSimpleName(), String.format("Soltado dedo %s ultimo",
																event.getActionIndex()));
				hasTouch = false;
				for (int i = 0; i < 3; i++)
					buttons.get(i).checkButtonRelased(touchs);
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

	public List<GameCharacter> getCharacters(CharacterName name) {
		return this.characters.stream()
							  .filter(character -> character.getName() != null)
							  .filter(character -> character.getName() == name)
							  .collect(Collectors.toList());

	}

	public int getScreenHeight() {
		return this.screen.getHeight();
	}

	public int getScreenWidth() {
		return this.screen.getWidth();
	}

}
