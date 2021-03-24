package com.eos.spatialracoon.level;

public class LevelSetting {

	private final int level;
	private final int initialEnemies;
	private final int maxEnemies;
	private final int maxFigures;
	private final int playerPoints;
	private final int enemySpeed;
	private final int maxNewEnemies;

	public LevelSetting(int level, int minEnemies, int maxEnemies,
						int maxFigures, int playerPoints, int enemySpeed, int maxNewEnemies) {
		this.level = level;
		this.initialEnemies = minEnemies;
		this.maxEnemies = maxEnemies;
		this.maxFigures = maxFigures;
		this.playerPoints = playerPoints;
		this.enemySpeed = enemySpeed;
		this.maxNewEnemies = maxNewEnemies;
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

	public int getEnemySpeed() {
		return enemySpeed;
	}

	public int getMaxNewEnemies() {
		return maxNewEnemies;
	}
}
