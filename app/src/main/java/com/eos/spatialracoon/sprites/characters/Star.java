package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.Utilities;

public class Star extends GameCharacter {

	private boolean touched;

	public Star(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.golden_star),
			  new Size(150, 150));
		super.setX(Utilities.generateRandom((int) (super.getImageWidth() / 2f), getScreen().getWidth()));
		super.setY(Utilities.generateRandom((int) (super.getImageHeight() / 2f), getScreen().getHeight()));
	}

	public void isTouched(float x, float y) {
		if (x > super.getX() && x < super.getX() + getImageWidth()
				&& y > super.getY() && y < super.getY() + getImageHeight()) {
			touched = true;
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(super.getImage(),
						  super.getX() - super.getImageWidth() / 2f,
						  super.getY() - super.getImageHeight() / 2f, paint);
	}

	public boolean touched() {
		return touched;
	}

	public void removeTouch() {
		this.touched = false;
	}
}
