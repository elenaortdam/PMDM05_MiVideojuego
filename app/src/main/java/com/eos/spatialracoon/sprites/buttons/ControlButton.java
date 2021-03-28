package com.eos.spatialracoon.sprites.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.Screen;
import com.eos.spatialracoon.Utilities;
import com.eos.spatialracoon.enums.ButtonName;

public abstract class ControlButton {

	private float x;
	private float y;
	protected Bitmap image;
	protected ButtonName name;
	private boolean touched;
	private final Context context;
	private final int DIMENSION = 150;
	private final Screen screen;
	private MediaPlayer mediaPlayer;

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

	public void isTouched(float x, float y) {
		if (x > this.x && x < this.x + getButtonWidth()
				&& y > this.y && y < this.y + getButtonHeight()) {
			touched = true;
			mediaPlayer = MediaPlayer.create(this.context, R.raw.blaster_sound);
			mediaPlayer.seekTo(500);
			mediaPlayer.setVolume(1, 1);
			mediaPlayer.start();
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
