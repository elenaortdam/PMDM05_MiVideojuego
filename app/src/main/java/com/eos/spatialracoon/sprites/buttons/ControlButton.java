package com.eos.spatialracoon.sprites.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.Touch;
import com.eos.spatialracoon.Utilities;
import com.eos.spatialracoon.enums.ButtonName;

import java.util.List;

public abstract class ControlButton {

	private float x;
	private float y;
	protected Bitmap image;
	protected ButtonName name;
	private boolean touched;
	private final Context context;
	private final int DIMENSION = 150;
	private final Screen screen;

	public ControlButton(Context context) {
		this.context = context;
		this.screen = Utilities.calculateScreenSize(context);
	}

	public ControlButton(Context context, Bitmap image) {
		this(context);
		this.image = Bitmap.createScaledBitmap(image, DIMENSION, DIMENSION, false);
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(this.image, this.getX(), this.getY(), paint);

	}

	public boolean isTouched(float x, float y) {
		if (x > this.x && x < this.x + getButtonWidth()
				&& y > this.y && y < this.y + getButtonHeight()) {
			touched = true;
		}
		return touched;
	}

	@Deprecated
	public void checkButtonRelased(List<Touch> touchs) {
		boolean aux = false;
		for (Touch touch : touchs) {
			if (isTouched(touch.getX(), touch.getY())) {
				aux = true;
			}
		}
		if (!aux) {
			this.touched = false;
		}
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

	public int getButtonWidth() {
		return image.getWidth();
	}

	public int getButtonHeight() {
		return image.getHeight();
	}

	public Screen getScreen() {
		return screen;
	}

	public boolean isTouched() {
		return touched;
	}

	public void removeTouch() {
		this.touched = false;
	}

	public ButtonName getName() {
		return name;
	}
}
