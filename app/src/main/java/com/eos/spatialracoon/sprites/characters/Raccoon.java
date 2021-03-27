package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.enums.CharacterName;

public class Raccoon extends GameCharacter {

	private final float middleImageHeight;
	private final float middleImageWidth;

	public Raccoon(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.mapache), new Size(300, 400));

		super.setX(super.getScreen().getWidth() / 2f);
		super.setY(super.getScreen().getHeight() / 2f);
		middleImageHeight = (float) super.getImageHeight() / 2;
		middleImageWidth = (float) super.getImageWidth() / 2;
		setName(CharacterName.RACCOON);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {

		canvas.drawBitmap(super.getImage(), super.getX() - middleImageWidth, super.getY() - middleImageHeight, paint);
		float cx = super.getScreen().getWidth() / 2f;
		float cy = super.getScreen().getHeight() / 2f;
		canvas.drawRect(cx - (float) super.getImageWidth() / 2, cy - (float) super.getImageHeight() / 2,
						cx + (float) super.getImageWidth() / 2, cy + (float) super.getImageHeight() / 2, paint);
	}

}
