package com.eos.spatialracoon.sprites.buttons;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.constants.ButtonName;

public class XButton extends ControlButton {

	public XButton(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.x_button));
		setX(super.getScreen().getWidth() / 1.175f);
		setY(super.getScreen().getHeight() / 1.235f);
		this.name = ButtonName.X;
	}
}
