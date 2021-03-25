package com.eos.spatialracoon.level;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Level {

	private final HashMap<Integer, LevelSetting> levelSettings = createLevel();

	//TODO: elena probar nuevos niveles
	private HashMap<Integer, LevelSetting> createLevel() {
		HashMap<Integer, LevelSetting> levelSettings = new HashMap<>();
		levelSettings.put(1, new LevelSetting(1, 10, 1,
											  100, 5, 2,
											  10));
		levelSettings.put(2, new LevelSetting(2, 20, 2,
											  300, 7, 4,
											  20));
		levelSettings.put(3, new LevelSetting(3, 30, 3,
											  Integer.MAX_VALUE, 10, 7,
											  30));
		return levelSettings;
	}

	public LevelSetting getLevelSettings(int level) {
		Integer maxValue = Collections.max(this.levelSettings.entrySet(),
										   Comparator.comparingInt(Map.Entry::getKey))
									  .getKey();

		return this.levelSettings.getOrDefault(level, this.levelSettings.get(maxValue));
	}

}
