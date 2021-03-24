package com.eos.spatialracoon;

public class Touch {

	private final float x;
	private final float y;
	private final int index;

	public Touch(float x, float y, int index) {
		this.x = x;
		this.y = y;
		this.index = index;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
