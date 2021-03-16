package com.eos.spatialracoon.sprites.buttons;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.constants.Figure;

public class TriangleButton extends ControlButton {

	public TriangleButton(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.triangle_button));
		setX(super.getScreen().getWidth() / 1.175f);
		setY(super.getScreen().getHeight() / 1.90f);
		this.name = Figure.TRIANGLE;
	}
}
