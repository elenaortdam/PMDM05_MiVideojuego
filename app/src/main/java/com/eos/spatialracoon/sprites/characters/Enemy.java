package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.util.Size;

import com.eos.spatialracoon.Game;
import com.eos.spatialracoon.GameLoop;
import com.eos.spatialracoon.R;
import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.constants.ButtonName;
import com.eos.spatialracoon.constants.CharacterName;
import com.eos.spatialracoon.level.Level;
import com.eos.spatialracoon.level.LevelSetting;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends GameCharacter {

	private final int STROKE_WIDTH = 8;

	private final float speed;
	private final float x;
	private final float y;
	private final List<ButtonName> controlButtonNames = new ArrayList<>();
	private Bitmap image;
	//TODO: elena ver si dependiendo de las coordenadas sale por la derecha o la izquierda

	@Deprecated
	private int lifes;
	private final Screen screen;

	private Game game;
	private Context context;
	private final int level;

	//TODO: elena ¿hacer un zona excluida para los botones y la zona cerca del mapache?

	public Enemy(Game game, int level) {
		super(game.getContext(), BitmapFactory.decodeResource(game.getContext().getResources(),
															  R.drawable.meteroid),
			  new Size(200, 200));

//		this.screen = Utilities.calculateScreenSize(this.context);
		this.screen = super.getScreen();
		Point point = getRandomBorderPoint(this.screen);
		this.x = point.x;
		this.y = point.y;
		this.level = level;
		Level levelSetting = new Level();
		float ENEMY_SPEED = game.getHeight() / 20f / GameLoop.MAX_FPS;
		speed = level * ENEMY_SPEED;
		LevelSetting levelSettings = levelSetting.getLevelSettings(level);
		for (int i = 0; i < levelSettings.getMaxFigures(); i++) {
			this.controlButtonNames.add(getRandomFigure());
		}
		setName(CharacterName.METEOROID);
	}

	private Point getRandomBorderPoint(Screen screen) {
		final byte X = 0;
		final byte MIN = 0;
		Point point = new Point();
		int height = screen.getHeight() - 200;
		int width = screen.getWidth() - 200;
		//TODO: elena mirar esto porque siempre sale 0 con algo y nunca sale igual el if de dentro igual
		if (Math.random() == X) {
			if (Math.random() == MIN) {
				point.set(0, generateRandom(0, height));
			} else {
				point.set(width, generateRandom(0, height));
			}
		} else {
			if (Math.random() == MIN) {
				point.set(generateRandom(0, width), 0);
			} else {
				point.set(generateRandom(0, width), height);
			}
		}
		return point;
	}

	private ButtonName getRandomFigure() {
		int randomFigure = generateRandom(0, 3);
		return ButtonName.values()[1];
	}

	private int generateRandom(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);

	}

	public void moveEnemy() {
		List<GameCharacter> characters = game.getCharacters(CharacterName.RACCOON);
		if (characters.isEmpty()) {
			Log.e(getClass().getSimpleName(),
				  "No se ha encontrado al personaje principal");
		}
		GameCharacter raccoon = characters.get(0);

	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(super.getImage(), this.x, this.y, paint);
//		canvas.drawBitmap(super.getImage(), 500, 500, paint);
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
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(STROKE_WIDTH);
		canvas.drawLine(this.x, this.y, 100, 100, paint);
		canvas.drawLine(100, this.y, this.x, 100, paint);

	}

	private void drawCircle(Canvas canvas, Paint paint) {
		paint.setColor(Color.RED);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(this.x + 15, this.y + 40, 25, paint);
	}

}
