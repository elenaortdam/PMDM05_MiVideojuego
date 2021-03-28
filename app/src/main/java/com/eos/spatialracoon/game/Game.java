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
import android.graphics.Rect;
import android.media.MediaPlayer;
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
import com.eos.spatialracoon.sprites.characters.Star;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class Game extends SurfaceView implements SurfaceHolder.Callback, SurfaceView.OnTouchListener {

	private final SurfaceHolder holder;
	private GameLoop gameLoop;
	private final List<ControlButton> buttons = new ArrayList<>();
	private List<GameCharacter> enemies = new ArrayList<>();
	private LevelSetting levelSetting;
	private boolean lose = false;

	//TODO: pasarlo a una clase (?)
	private static int topScore;
	private static int score;
	private final String SCORE = "SCORE";

	private Bitmap background;
	private final Raccoon raccoon;

	private int newEnemyFrames = 500; //frames que restan hasta generar nuevo enemigo
	private int newSpecialStarAppear;

	private final List<Touch> touchs = new ArrayList<>();
	private final Screen screen;

	private final SharedPreferences sharedPreferences;

	private MediaPlayer mediaPlayer; //para reproducir la música de fondo
	private boolean killSongPlayed;

	private Star star;

	public Game(Activity activity) {
		super(activity);
		holder = getHolder();
		holder.addCallback(this);

		this.screen = Utilities.calculateScreenSize(getContext());
		loadBackground();
		loadControlButtons();
		this.raccoon = new Raccoon(this.getContext());
		Log.d("Posicion mapache", "(" + raccoon.getX() + ", " + raccoon.getY() + ")");
		setOnTouchListener(this);
		this.levelSetting = Level.getLevelSettings(1);
		score = 0;
		int DEFAULT_ENEMIES = 5;
		for (int i = 0; i < DEFAULT_ENEMIES; i++) {
			createEnemy();
		}
		sharedPreferences = getContext().getSharedPreferences(getResources().getString(R.string.app_name),
															  Context.MODE_PRIVATE);
		playBackgroundMusic();
		killSongPlayed = false;
		newSpecialStarAppear = Utilities.generateRandom(500, 1000);
	}

	private void playBackgroundMusic() {
		mediaPlayer = MediaPlayer.create(getContext(), R.raw.epic_music);
		mediaPlayer.setOnCompletionListener(MediaPlayer::start);
		mediaPlayer.start();
		mediaPlayer.setLooping(true);
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
		background = BitmapFactory.decodeResource(getResources(),
												  R.drawable.background);
		this.background = Bitmap.createScaledBitmap(background,
													screen.getWidth(),
													screen.getHeight(),
													false);
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
		enemies.add(new Enemy(this, levelSetting.getLevel()));
	}

	/**
	 * Este método actualiza el estado del juego. Contiene la lógica del videojuego
	 * generando los nuevos estados y dejando listo el sistema para un repintado.
	 */
	public void update() {

		if (newSpecialStarAppear == 0) {
			star = new Star(getContext());
			newSpecialStarAppear = Utilities.generateRandom(1000, 2000);
		}
		newSpecialStarAppear--;

		if (lose) {
			gameLoop.gameOver();
			this.mediaPlayer.stop();
			killMusic();
			Intent intent = new Intent().setClass(getContext(), GameOverActivity.class);
			getContext().startActivity(intent);
		}

		for (GameCharacter character : enemies) {
			Enemy enemy = (Enemy) character;
			enemy.moveEnemy(levelSetting);
		}
		List<GameCharacter> enemiesKilled;

		if (star != null && star.touched()) {
			star.removeTouch();
			star = null;
			enemiesKilled = new ArrayList<>(this.enemies);
			this.enemies = new ArrayList<>();
			createNewEnemies();
		} else {
			//Eliminamos las figuras de los enemigos
			enemiesKilled = killEnemies();
			if (enemiesKilled.size() > 0) {
				createNewEnemies();
			}

		}

		updateScore(enemiesKilled);

		levelUp();

		lose = gameOver();

		if (newEnemyFrames == 0) {
			createNewEnemies();
			//nuevo ciclo de enemigos
			newEnemyFrames = GameLoop.MAX_FPS * 60 / levelSetting.getMaxNewEnemies();
		}
		newEnemyFrames--;
	}

	public void createNewEnemies() {

		int maxEnemies = this.levelSetting.getMaxEnemies();
		int actualEnemies = this.enemies.size();
		if (actualEnemies < maxEnemies) {
			int newEnemiesCreated = 0;
			while (newEnemiesCreated < levelSetting.getMaxNewEnemies()) {
				createEnemy();
				newEnemiesCreated++;
			}
		}
	}

	public void levelUp() {

		if (score >= levelSetting.getPlayerPoints()) {
			int actualLevel = levelSetting.getLevel();
			this.levelSetting = Level.getLevelSettings(actualLevel + 1);
		}
	}

	public void updateScore(List<GameCharacter> killedEnemies) {
		for (GameCharacter gameCharacter : killedEnemies) {
			Enemy enemy = (Enemy) gameCharacter;
			score += enemy.getEnemyPoints();
		}
		topScore = this.sharedPreferences.getInt(SCORE, 0);
		if (topScore < score) {
			this.sharedPreferences.edit().putInt(SCORE, score).apply();
			topScore = score;
		}
	}

	public boolean gameOver() {

		Rect raccoonCollision = raccoon.getCollision();
		for (GameCharacter character : enemies) {
			Enemy enemy = (Enemy) character;

			// temporary variables to set edges for testing
			float testX = enemy.getX();
			float testY = enemy.getY();

			//Vemos cual está más cerca
			if (enemy.getX() < raccoonCollision.left) { //izquierda
				testX = raccoonCollision.left;
			}
			if (enemy.getX() > raccoonCollision.right) {
				testX = raccoonCollision.right; //derecha
			}
			if (enemy.getY() < raccoonCollision.top) { //arriba
				testY = raccoonCollision.top;
			}
			if (enemy.getY() > raccoonCollision.bottom) { //abajo
				testY = raccoonCollision.bottom;
			}

			// cogemos la distancia de los extremos más cercanos
			float distX = enemy.getX() - testX;
			float distY = enemy.getY() - testY;
			double distance = Math.sqrt((distX * distX) + (distY * distY));

			// Si la distancia es menor que el radio hay colisión
			boolean test = distance <= enemy.getImageHeight() / 2f;

			if (test) {
				Log.wtf("Colisión en: ", "(" + enemy.getX() + ", " + enemy.getY() + ")");
			}
			return test;
		}
		return false;
	}

	private List<GameCharacter> killEnemies() {

		List<GameCharacter> killed = new ArrayList<>();
		for (ControlButton button : buttons) {
			if (button.isTouched()) {
				for (GameCharacter character : enemies) {
					Enemy enemy = (Enemy) character;
					enemy.removeFigures(button.getName());
					button.removeTouch();
					if (!enemy.isAlive()) {
						killed.add(enemy);
					}
				}
			}
		}
		this.enemies.removeAll(killed);
		return killed;
	}

	/**
	 * Este método dibuja el siguiente paso de la animación correspondiente
	 */
	public void render(Canvas canvas) {
		if (canvas != null) {
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.WHITE);
			//TODO: ver como hacer para que ocupe toda la pantalla
			canvas.drawBitmap(background, 0, -1, null);
			for (ControlButton button : buttons) {
				button.draw(canvas, paint);
			}
			for (GameCharacter enemy : enemies) {
				enemy.draw(canvas, paint);
			}
			paint.setTextSize((float) (this.screen.getWidth() / 30));
			paint.setColor(Color.WHITE);
			canvas.drawText("PUNTOS: " + score + " - Nivel " + levelSetting.getLevel(),
							75, 75, paint);

			raccoon.draw(canvas, paint);
			if (star != null) {
				star.draw(canvas, paint);
			}
		}
	}

	private void killMusic() {
//		mediaPlayer = new MediaPlayer();
		mediaPlayer = MediaPlayer.create(getContext(), R.raw.quack);
		if (!killSongPlayed) {
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
			killSongPlayed = true;
		}
	}

	//TODO: liberar recursos al destruir
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		boolean retry = true;
		while (retry) {
			try {
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
				x = event.getX();
				y = event.getY();
				synchronized (this) {
					touchs.add(index, new Touch(x, y, index));
					for (ControlButton button : buttons) {
						button.isTouched(x, y);
					}
					if (star != null) {
						star.isTouched(x, y);
					}
				}
				break;
		}
		return true;
	}

	//TODO: quitar todos los deprecated

	public Raccoon getRaccoon() {
		return this.raccoon;
	}

	public static int getTopScore() {
		return topScore;
	}

	public static int getScore() {
		return score;
	}
}
