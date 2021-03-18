package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.constants.CharacterName;

public class Raccoon extends GameCharacter {

	public Raccoon(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.mapache), new Size(350, 500));
		setX(super.getScreen().getWidth() / 2f - this.getImage().getWidth() / 2f);
		setY(super.getScreen().getHeight() / 1.5f - this.getImage().getHeight() / 2.5f);
		setName(CharacterName.RACCOON);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(super.getImage(), this.getX(), this.getY(), paint);
	}

}
