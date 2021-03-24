package com.eos.spatialracoon.sprites.buttons;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.enums.ButtonName;

public class CircleButton extends ControlButton {

	public CircleButton(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.circle_button));
		setX(super.getScreen().getWidth() / 1.1f);
		setY(super.getScreen().getHeight() / 1.5f);
		this.name = ButtonName.CIRCLE;
	}
}
