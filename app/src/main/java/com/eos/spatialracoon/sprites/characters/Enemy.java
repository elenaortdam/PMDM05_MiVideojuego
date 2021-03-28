package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.enums.ButtonName;
import com.eos.spatialracoon.enums.CharacterName;
import com.eos.spatialracoon.game.Game;
import com.eos.spatialracoon.level.Level;
import com.eos.spatialracoon.level.LevelSetting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.eos.spatialracoon.Utilities.generateRandom;

public class Enemy extends GameCharacter {

	private final int STROKE_WIDTH = 8;

	private float x;
	private float y;
	//TODO: quitar la lista
	private final List<ButtonName> controlButtonNames = new ArrayList<>();

	private final Screen screen;
	private final int enemyPoints;

	private final Game game;
	@Deprecated
	private Context context;
	private final int level;
	private boolean alive = true;
	private final Rect collision;

	public Enemy(Game game, int level) {
		super(game.getContext(),
			  BitmapFactory.decodeResource(game.getContext().getResources(),
										   R.drawable.asteroid),
			  new Size(100, 100));
		this.game = game;
		this.screen = super.getScreen();
		Point point = getRandomBorderPoint(this.screen);
		this.x = point.x;
		this.y = point.y;
		this.level = level;
		LevelSetting levelSettings = Level.getLevelSettings(level);
		for (int i = 0; i < levelSettings.getMaxFigures(); i++) {
			this.controlButtonNames.add(getRandomFigure());
		}
		this.enemyPoints = levelSettings.getKilledEnemiesPoint() * this.controlButtonNames.size();

		Log.wtf("Posicion puta colision de mierda", "(" + x + ", " + y + ")");
		this.collision = new Rect((int) (this.x),
								  (int) (this.y),
								  (int) (this.x + super.getImageWidth()),
								  (int) (this.y + super.getImageHeight()));
		setName(CharacterName.METEOROID);
	}

	private Point getRandomBorderPoint(Screen screen) {
		Point point = new Point();
		point.set(generateRandom(0, screen.getWidth()), 0);

		final float X = 0.5f;

		//TODO: quitar el hardcoded
		int height = screen.getHeight() - super.getImageHeight();
		int outScreen = -height / 4;
		if (Math.random() <= X) {
			point.set(generateRandom(0, 700), outScreen);
		} else {
			point.set(generateRandom(1300, screen.getWidth()), outScreen);
		}
		return point;
	}

	private ButtonName getRandomFigure() {
		int randomFigure = generateRandom(0, 3);
		return ButtonName.values()[randomFigure];
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

		this.collision.set((int) (this.x),
						   (int) (this.y),
						   (int) (this.x + super.getImageWidth()),
						   (int) (this.y + super.getImageHeight()));

		Log.d("Posicion enemigo", "(" + x + ", " + y + ")");
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(super.getImage(), this.x, this.y, paint);
		paint.setColor(Color.WHITE);
		canvas.drawRect(collision, paint);
//		canvas.drawCircle(this.x + super.getImageWidth() / 2f, this.y + super.getImageHeight() / 2f,
//						  super.getImageWidth() / 2f, paint);
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
		//TODO: hacer más hacia arriba
		float triangleX = x + 35;
		float triangleY = y + 35;
		path.moveTo(triangleX, triangleY - size); // arriba
		path.lineTo(triangleX - size, triangleY + size); // abajo izquierda
		path.lineTo(triangleX + size, triangleY + size); // abajo derecha
		path.lineTo(triangleX, triangleY - size); // Cerrar el triángulo
		path.close();

		canvas.drawPath(path, paint);

	}

	private void drawSquare(Canvas canvas, Paint paint) {
		paint.setColor(Color.parseColor("#ff69f8"));
		paint.setStrokeWidth(STROKE_WIDTH);
		int size = 20;
		float x = this.x + getImageWidth() / 2f;
		float y = this.y - getImageHeight() / 2f + 10;
		canvas.drawRect(x - size, y - size,
						x + size, y + size, paint);
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

	public Rect getCollision() {
		return collision;
	}
}
