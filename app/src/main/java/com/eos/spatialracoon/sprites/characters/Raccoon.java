package com.eos.spatialracoon.sprites.characters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Size;

import com.eos.spatialracoon.R;
import com.eos.spatialracoon.enums.CharacterName;

public class Raccoon extends GameCharacter {

	private final float middleImageHeight;
	private final float middleImageWidth;
	private final Rect collision;

	public Raccoon(Context context) {
		super(context, BitmapFactory.decodeResource(context.getResources(),
													R.drawable.mapache), new Size(250, 350));

		//En medio abajo
		super.setX(super.getScreen().getWidth() / 2f);
		super.setY(super.getScreen().getHeight() / 1.5f);
		middleImageHeight = (float) super.getImageHeight() / 2;
		middleImageWidth = (float) super.getImageWidth() / 2;
		//Metemos un poco dentro del mapache la colisión para que quede "más natural"
		int size = 50;
		this.collision = new Rect((int) (super.getX() - middleImageWidth + size),
								  (int) (super.getY() - middleImageHeight + size),
								  (int) (super.getX() + middleImageWidth - size),
								  (int) (super.getY() + middleImageHeight - size));
		setName(CharacterName.RACCOON);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(super.getImage(),
						  super.getX() - middleImageWidth,
						  super.getY() - middleImageHeight, paint);
//		canvas.drawRect(collision, paint);
	}

	public Rect getCollision() {
		return collision;
	}
}
