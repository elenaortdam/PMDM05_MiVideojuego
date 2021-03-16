package com.eos.spatialracoon.sprites.buttons;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.constants.Figure;

public class SquareButton extends ControlButton {

	public SquareButton(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.square_button));
		setX(super.getScreen().getWidth() / 1.25f);
		setY(super.getScreen().getHeight() / 1.5f);
		this.name = Figure.SQUARE;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(this.image, this.getX(), this.getY(), paint);
	}
}
