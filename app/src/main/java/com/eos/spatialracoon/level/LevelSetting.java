package com.eos.spatialracoon.level;

public class LevelSetting {

	private final int level;
	private final int initialEnemies;
	private final int maxEnemies;
	private final int maxFigures;
	private final int playerPoints;

	public LevelSetting(int level, int initialEnemies, int maxEnemies,
						int maxFigures, int playerPoints) {
		this.level = level;
		this.initialEnemies = initialEnemies;
		this.maxEnemies = maxEnemies;
		this.maxFigures = maxFigures;
		this.playerPoints = playerPoints;
	}

	public int getInitialEnemies() {
		return initialEnemies;
	}

	public int getMaxEnemies() {
		return maxEnemies;
	}

	public int getMaxFigures() {
		return maxFigures;
	}

	public int getPlayerPoints() {
		return playerPoints;
	}

	public int getLevel() {
		return level;
	}
}
