package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.Utilities;

public class GameCharacter {

	//	private int lifes;
	private Bitmap image;
	private final Context context;
	private final Screen screen;

	private float x, y;

	public GameCharacter(Context context) {
		this.context = context;
		this.screen = Utilities.calculateScreenSize(context);
	}

	public GameCharacter(Context context, Bitmap image) {
		this(context);
		this.image = image;
	}

	public GameCharacter(Context context, Bitmap image, Size size) {
		this(context);
		this.image = Bitmap.createScaledBitmap(image, size.getWidth(), size.getHeight(),
											   false);
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(this.image, this.getX(), this.getY(), paint);

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public Bitmap getImage() {
		return image;
	}

	public Screen getScreen() {
		return screen;
	}
}
