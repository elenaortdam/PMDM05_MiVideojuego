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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Enemy extends GameCharacter {

	private final int STROKE_WIDTH = 8;

	private final float speed;
	private float x;
	private final float y;
	private final List<ButtonName> controlButtonNames = new ArrayList<>();
	private Bitmap image;

	//TODO: elena ver si dependiendo de las coordenadas sale por la derecha o la izquierda
	private final Screen screen;
	private int points;
	//TODO: elena poner la puntuación de cada enemigo
	private int lifes;

	private Game game;
	private Context context;
	private final int level;
	private boolean alive = true;

	public Enemy(Game game, int level) {
		super(game.getContext(), BitmapFactory.decodeResource(game.getContext().getResources(),
															  R.drawable.meteroid),
			  new Size(200, 200));

		this.screen = super.getScreen();
		Point point = getRandomBorderPoint(this.screen);
		this.x = point.x;
		this.y = point.y;
		if (x <= x / 2) {
			super.flipImage();
		}
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
		final float X = 0.5f;
		final float MIN = 0.5f;
		Point point = new Point();
		int height = screen.getHeight() - super.getImageHeight();
		int width = screen.getWidth() - super.getImageWidth();
		//Que aparezcan solo por la parte de arriba de la pantalla
		int middleScreen = height / 2;
		if (Math.random() <= X) {
			if (Math.random() <= MIN) {
				point.set(0, generateRandom(0, middleScreen));
			} else {
				point.set(width, generateRandom(0, middleScreen));
			}
		} else {
			if (Math.random() <= MIN) {
				point.set(generateRandom(0, width), 0);
			} else {
				point.set(generateRandom(0, width), middleScreen);
			}
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
		List<GameCharacter> characters = game.getCharacters(CharacterName.RACCOON);
		if (characters.isEmpty()) {
			Log.e(getClass().getSimpleName(),
				  "No se ha encontrado al personaje principal");
		}
		GameCharacter raccoon = characters.get(0);
		if (raccoon.getX() > this.x) {
			this.x += levelSetting.getEnemySpeed();
		} else {
			this.x -= levelSetting.getEnemySpeed();
		}
/*


 if(Math.abs(coordenada_x-juego.xNave)<VELOCIDAD_ENEMIGO_INTELIGENTE)
 coordenada_x=juego.xNave; //si está muy cerca se pone a su altura
 if( coordenada_y>=juego.AltoPantalla-juego.enemigo_listo.getHeight()
 && direccion_vertical==1)
 direccion_vertical=-1;
 if(coordenada_y<=0 && direccion_vertical ==-1)
 direccion_vertical=1;
 coordenada_y+=direccion_vertical*VELOCIDAD_ENEMIGO_INTELIGENTE;
 */
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

	public void removeFigures(Map<ButtonName, Integer> buttonsPressed) {
		for (Map.Entry<ButtonName, Integer> button : buttonsPressed.entrySet()) {
			Iterator<ButtonName> iterator = this.controlButtonNames.iterator();
			while (iterator.hasNext()) {
				if (iterator.next().equals(button.getKey())) {
					iterator.remove();
					if (button.getValue() > 1) {
						break;
					}
				}
			}
		}
		if (this.controlButtonNames.isEmpty()) {
			this.alive = false;
		}
	}

	public boolean isAlive() {
		return alive;
	}
}
