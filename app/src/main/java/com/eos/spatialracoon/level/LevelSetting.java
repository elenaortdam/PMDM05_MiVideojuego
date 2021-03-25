package com.eos.spatialracoon.level;

public class LevelSetting {

	private final int level;
	private final int maxEnemies;
	private final int maxFigures;
	private final int playerPoints;
	private final int enemySpeed;
	private final int maxNewEnemies;
	private final int killedEnemiesPoint;

	public LevelSetting(int level, int maxEnemies,
						int maxFigures, int playerPoints, int enemySpeed, int maxNewEnemies,
						int killedEnemiesPoint) {
		this.level = level;
		this.maxEnemies = maxEnemies;
		this.maxFigures = maxFigures;
		this.playerPoints = playerPoints;
		this.enemySpeed = enemySpeed;
		this.maxNewEnemies = maxNewEnemies;
		this.killedEnemiesPoint = killedEnemiesPoint;
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

	public int getKilledEnemiesPoint() {
		return killedEnemiesPoint;
	}
}
