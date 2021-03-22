package com.eos.spatialracoon;

public class Touch {

	private float x;
	private float y;
	private int index;

	public Touch(float x, float y, int index) {
		this.x = x;
		this.y = y;
		this.index = index;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
