package com.eos.spatialracoon;

import android.graphics.Point;

public class Screen {

	private final int width;
	private final int height;

	private Point point;

	public Screen(Point point) {
		this.width = point.x;
		this.height = point.y;
	}

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
