package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.R;

public class Star extends GameCharacter {

	private boolean touched;

	public Star(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(), R.drawable.golden_star),
			  new Size(100, 100));
		super.setX(200);
		super.setY(300);
	}

	public void isTouched(float x, float y) {
		if (x > super.getX() && x < super.getX() + getImageWidth()
				&& y > super.getY() && y < super.getY() + getImageHeight()) {
			touched = true;
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		//TODO: calcular coordenadas random
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
