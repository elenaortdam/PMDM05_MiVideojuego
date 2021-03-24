package com.eos.spatialracoon.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.Touch;
import com.eos.spatialracoon.Utilities;
import com.eos.spatialracoon.activities.GameOverActivity;
import com.eos.spatialracoon.enums.ButtonName;
import com.eos.spatialracoon.enums.CharacterName;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressLint("ViewConstructor")
public class Game extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

	//TODO: elena final limpiar clase
	private Bitmap bmp;
	private final SurfaceHolder holder;
	private GameLoop gameLoop;
	private final int[] availableBackgrounds = {R.drawable.background};
	private final Bitmap[] backgroundImages = new Bitmap[availableBackgrounds.length];
	private final List<ControlButton> buttons = new ArrayList<>();
	private final List<GameCharacter> characters = new ArrayList<>();
	private final LevelSetting levelSetting;
	private boolean lose = false;
	private final HashMap<ButtonName, Integer> buttonsPressed = new HashMap<>();

	//TODO: elena pasarlo a una clase
	private int topScore;
	private int score;
	private final String SCORE = "SCORE";

	private Bitmap background;
	private final Raccoon raccoon;

	private final int x = 0;
	private final int y = 0;

	private int contadorFrames = 0;
	//TODO: elena lógica nuevos enemigos -> iteracción menos que el máximo
	private final int newEnemyFrames = 0; //frames que restan hasta generar nuevo enemigo

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

		this.screen = Utilities.calculateScreenSize(getContext());
		loadBackground();
		loadControlButtons();
		this.raccoon = new Raccoon(this.getContext());
		Log.d("POSICION MAPACHE", "(" + raccoon.getX() + "," + raccoon.getY() + ")");
		characters.add(this.raccoon);
		setOnTouchListener(this);
		Level level = new Level();
		this.levelSetting = level.getLevelSettings(1);
		createEnemy();

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
		gameLoop = new GameLoop(getHolder(), this);

		// Hacer la Vista focusable para que pueda capturar eventos
		setFocusable(true);

		//comenzar el bucle
		gameLoop.start();

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
		for (int i = 0; i < levelSetting.getInitialEnemies(); i++) {
			characters.add(new Enemy(this, levelSetting.getLevel()));
		}
	}

	/**
	 * Este método actualiza el estado del juego. Contiene la lógica del videojuego
	 * generando los nuevos estados y dejando listo el sistema para un repintado.
	 */
	public void update() {

		if (lose) {
			gameLoop.gameOver();
			//TODO: elena musica morision
			Intent intent = new Intent().setClass(getContext(), GameOverActivity.class);
			updateScore(getContext().getSharedPreferences(getResources().getString(R.string.app_name),
														  Context.MODE_PRIVATE));
			getContext().startActivity(intent);
		}

		for (ControlButton button : this.buttons) {
			//TODO: elena test NPE
			if (button.isTouched()) {
				Integer pressedQuantity =
						this.buttonsPressed.get(button);
				if (pressedQuantity == null) {
					this.buttonsPressed.put(button.getName(), 1);
				} else {
					this.buttonsPressed.put(button.getName(), pressedQuantity + 1);
				}
			}
		}

		for (GameCharacter character : characters) {
			if (character.getName().equals(CharacterName.METEOROID)) {
				Enemy enemy = (Enemy) character;
				enemy.moveEnemy(levelSetting);
			}
		}
		//Eliminamos las figuras de los enemigos
		killEnemies(this.buttonsPressed);

		//TODO: elena createNewEnemies()

		levelUp();

		lose = gameOver(characters);

		//TODO: elena comprobación de si tiene que aumentar de nivel
//		createEnemy();
		contadorFrames++;
	}

	public void createNewEnemy() {

	}

	public void levelUp() {
/*
//cada PUNTOS_CAMBIO_NIVEL puntos se incrementa la dificultad
		if (Nivel != Puntos / PUNTOS_CAMBIO_NIVEL) {
			Nivel = Puntos / PUNTOS_CAMBIO_NIVEL;
			enemigos_minuto += (20 * Nivel);
		}
 */
	}

	public void updateScore(SharedPreferences prefs) {
		topScore = prefs.getInt(SCORE, 0);
		if (topScore < score) {
			prefs.edit().putInt(SCORE, score).apply();
			topScore = score;
		}

	}

	//TODO: elena hacer bien la colisión con un círculo y una recta
	public boolean gameOver(List<GameCharacter> characters) {

		for (GameCharacter character : characters) {
			if (character.getName().equals(CharacterName.METEOROID)) {
				Enemy enemy = (Enemy) character;
				int maxHeight = Math.max(enemy.getImageWidth(), raccoon.getImageHeight());
				int maxWidth = Math.max(enemy.getImageHeight(), raccoon.getImageWidth());
				float xDifference = Math.abs(enemy.getX() - raccoon.getX());
				float yDifference = Math.abs(enemy.getY() - raccoon.getY());
				return xDifference < maxWidth && yDifference < maxHeight;
			}
		}
		return false;
	}

	private void killEnemies(Map<ButtonName, Integer> buttonsPressedByUser) {
		if (buttonsPressedByUser.isEmpty()) {
			return;
		}
		List<Enemy> alive = new ArrayList<>();
		List<Enemy> killed = new ArrayList<>();
		for (GameCharacter character : characters) {
			if (character.getName().equals(CharacterName.METEOROID)) {
				Enemy enemy = (Enemy) character;
				enemy.removeFigures(buttonsPressedByUser);
				if (enemy.isAlive()) {
					alive.add(enemy);
				} else {
					killed.add(enemy);
				}
			}
		}
		//TODO: elena poner una animación o algo para cada enemigo matado?
		//TODO: sumar puntos por cada enemigo matado
		this.characters.removeAll(killed);
	}

	/**
	 * Este método dibuja el siguiente paso de la animación correspondiente
	 */
	public void render(Canvas canvas) {
		if (canvas != null) {
			Paint myPaint = new Paint();
			myPaint.setStyle(Paint.Style.STROKE);
			myPaint.setColor(Color.WHITE);
			//TODO: elena ver si por el -1 es por lo que sale hacia la izquierda
			canvas.drawBitmap(backgroundImages[0], 0, -1, null);
			for (ControlButton button : buttons) {
				button.draw(canvas, myPaint);
			}
			for (GameCharacter character : characters) {
				character.draw(canvas, myPaint);
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		boolean retry = true;
		while (retry) {
			try {
//				gameLoop.isRunning(false);
				gameLoop.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			retry = false;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int index;
		float x, y;
		index = event.getActionIndex();

		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				hasTouch = true;
				x = event.getX();
				y = event.getY();
				synchronized (this) {
					touchs.add(index, new Touch(x, y, index));
					for (ControlButton button : buttons) {
						button.isTouched(x, y);
					}
				}
				break;
		}
		return true;
	}

	public List<GameCharacter> getCharacters(CharacterName name) {
		return this.characters.stream()
							  .filter(character -> character.getName() != null)
							  .filter(character -> character.getName() == name)
							  .collect(Collectors.toList());

	}

}
