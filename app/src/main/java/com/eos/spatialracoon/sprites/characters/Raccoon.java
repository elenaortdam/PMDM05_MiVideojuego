package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Size;

import com.eos.spatialracoon.R;

public class Raccoon extends GameCharacter {

	public Raccoon(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.mapache), new Size(350, 500));
		setX(super.getScreen().getWidth() / 2f - this.getImage().getWidth() / 2f);
		setY(super.getScreen().getHeight() / 1.5f - this.getImage().getHeight() / 2.5f);
	}
}
