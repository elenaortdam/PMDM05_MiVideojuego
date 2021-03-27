package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.enums.ButtonName;
import com.eos.spatialracoon.enums.CharacterName;
import com.eos.spatialracoon.game.Game;
import com.eos.spatialracoon.game.GameLoop;
import com.eos.spatialracoon.level.Level;
import com.eos.spatialracoon.level.LevelSetting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Enemy extends GameCharacter {

	private final int STROKE_WIDTH = 8;

	private float x;
	private float y;
	private final List<ButtonName> controlButtonNames = new ArrayList<>();

	private final Screen screen;
	private final int enemyPoints;

	private final Game game;
	@Deprecated
	private Context context;
	private final int level;
	private boolean alive = true;

	public Enemy(Game game, int level) {
		super(game.getContext(),
			  BitmapFactory.decodeResource(game.getContext().getResources(),
										   R.drawable.asteroid),
			  new Size(150, 150));
		this.game = game;
		this.screen = super.getScreen();
		Point point = getRandomBorderPoint(this.screen);
		this.x = point.x;
		this.y = point.y;
		this.level = level;
		Level levelSetting = new Level();
		float ENEMY_SPEED = game.getHeight() / 20f / GameLoop.MAX_FPS;
		LevelSetting levelSettings = Level.getLevelSettings(level);
		for (int i = 0; i < levelSettings.getMaxFigures(); i++) {
			this.controlButtonNames.add(getRandomFigure());
		}
		this.enemyPoints = levelSettings.getKilledEnemiesPoint() * this.controlButtonNames.size();
		setName(CharacterName.METEOROID);
	}

	private Point getRandomBorderPoint(Screen screen) {
		Point point = new Point();
		point.set(generateRandom(0, screen.getWidth()), 0);

		final float X = 0.5f;

		int height = screen.getHeight() - super.getImageHeight();
		int outScreen = -height / 4;
		if (Math.random() <= X) {
			point.set(generateRandom(0, (int) (screen.getWidth() / 3.5f)), outScreen);
		} else {
			point.set(generateRandom((int) (screen.getWidth() / 2f), screen.getWidth()), outScreen);
		}
		return point;
	}

	private ButtonName getRandomFigure() {
		int randomFigure = generateRandom(0, 3);
		return ButtonName.values()[randomFigure];
	}

	private int generateRandom(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);

	}

	public void moveEnemy(LevelSetting levelSetting) {

		GameCharacter raccoon = game.getRaccoon();
		float proportion = Math.abs(raccoon.getX() - this.x) / Math.abs(raccoon.getY() - this.y);

		if (raccoon.getX() > this.x) {
			this.x += levelSetting.getEnemySpeed() * proportion;
		} else {
			this.x -= levelSetting.getEnemySpeed();
		}
		if (raccoon.getY() > this.y) {
			this.y += levelSetting.getEnemySpeed() / proportion;
		} else {
			this.y -= levelSetting.getEnemySpeed();
		}
//		Log.d("POSICION ENEMIGO", "(" + this.x + "," + this.y + ")");
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(super.getImage(), this.x, this.y, paint);
		for (ButtonName controlButtonName : this.controlButtonNames) {
			switch (controlButtonName) {
				case X:
					drawX(canvas, paint);
					break;
				case CIRCLE:
					drawCircle(canvas, paint);
					break;
				case SQUARE:
					drawSquare(canvas, paint);
					break;
				case TRIANGLE:
					drawTriangle(canvas, paint);
					break;
			}
		}
	}

	private void drawTriangle(Canvas canvas, Paint paint) {
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(STROKE_WIDTH);
		int size = 25;
		Path path = new Path();
		//TODO: elena con las nuevas figuras hacer más hacia arriba
		//TODO: elena dependiendo de la cantidad de figuras desplazar más a la derecha
		float triangleX = x + 35;
		float triangleY = y + 35;
		path.moveTo(triangleX, triangleY - size); // Top
		path.lineTo(triangleX - size, triangleY + size); // Bottom left
		path.lineTo(triangleX + size, triangleY + size); // Bottom right
		path.lineTo(triangleX, triangleY - size); // Back to Top
		path.close();

		canvas.drawPath(path, paint);

	}

	private void drawSquare(Canvas canvas, Paint paint) {
		paint.setColor(Color.parseColor("#ff69f8"));
		paint.setStrokeWidth(STROKE_WIDTH);
		int halfWidth = 30;

		float xSquare = x;
		float ySquare = y;
		Path path = new Path();

		path.moveTo(xSquare, ySquare + halfWidth);
		path.lineTo(xSquare, ySquare - halfWidth);
		path.lineTo(xSquare + (halfWidth * 2), ySquare - halfWidth);
		path.lineTo(xSquare + (halfWidth * 2), ySquare + halfWidth);
		path.lineTo(xSquare, ySquare + halfWidth);

		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawX(Canvas canvas, Paint paint) {
		paint.setColor(Color.parseColor("#2E6DB4")); //Celtic blue "#2E6DB4"
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setShadowLayer(4, 2, 5, Color.WHITE);

		int size = 50;

		float newY = this.y + 15;
		float stopX = x + size;
		float stopY = (stopX - x) + newY;

		canvas.drawLine(x, newY, stopX, stopY, paint);
		canvas.drawLine(x, stopY, stopX, newY, paint);

	}

	private void drawCircle(Canvas canvas, Paint paint) {
		paint.setColor(Color.RED);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(this.x + 15, this.y + 40, 25, paint);
	}

	public void removeFigures(ButtonName buttonName) {
		Iterator<ButtonName> iterator = this.controlButtonNames.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(buttonName)) {
				iterator.remove();
				//Solo quitamos 1 figura por botón pulsado
				break;
			}
		}

		if (this.controlButtonNames.isEmpty()) {
			this.alive = false;
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public List<ButtonName> getControlButtonNames() {
		return controlButtonNames;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	public int getLevel() {
		return level;
	}

	public int getEnemyPoints() {
		return enemyPoints;
	}
}
