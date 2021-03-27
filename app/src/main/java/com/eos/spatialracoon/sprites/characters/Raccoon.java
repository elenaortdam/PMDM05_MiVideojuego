package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.enums.CharacterName;

public class Raccoon extends GameCharacter {

	private float middleImageHeight;
	private float middleImageWidth;

	public Raccoon(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.mapache), new Size(300, 400));

		float x = super.getScreen().getWidth() / 2f - (float) super.getImageWidth() / 2;
		float y = super.getScreen().getHeight() / 2f - (float) super.getImageHeight() / 2;
		setX(x);
		setY(y);

		setName(CharacterName.RACCOON);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {

		canvas.drawBitmap(super.getImage(), super.getX(), super.getY(), paint);
		float cx = super.getScreen().getWidth() / 2f;
		float cy = super.getScreen().getHeight() / 2f;
		canvas.drawRect(cx - (float) super.getImageWidth() / 2, cy - (float) super.getImageHeight() / 2,
						cx + (float) super.getImageWidth() / 2, cy + (float) super.getImageHeight() / 2, paint);
//		canvas.drawCircle(cx, cy, (float) super.getImageWidth() / 2, paint);
	}

}
