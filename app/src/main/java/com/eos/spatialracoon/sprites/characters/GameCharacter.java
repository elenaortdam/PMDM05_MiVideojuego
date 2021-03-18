package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.Utilities;
import com.eos.spatialracoon.constants.CharacterName;

public abstract class GameCharacter {

	//	private int lifes;
	private Bitmap image;
	private final Context context;
	private final Screen screen;
	private CharacterName name;

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

	public abstract void draw(Canvas canvas, Paint paint);

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	protected void setX(float x) {
		this.x = x;
	}

	protected void setY(float y) {
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

	public CharacterName getName() {
		return name;
	}

	protected void setName(CharacterName name) {
		this.name = name;
	}
}
