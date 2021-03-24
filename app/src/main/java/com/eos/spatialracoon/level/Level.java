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
		levelSettings.put(1, new LevelSetting(1, 5, 10, 1,
											  100, 5, 2));
		return levelSettings;
	}

	public LevelSetting getLevelSettings(int level) {
		Integer maxValue = Collections.max(this.levelSettings.entrySet(),
										   Comparator.comparingInt(Map.Entry::getKey))
									  .getKey();

		return this.levelSettings.getOrDefault(level, this.levelSettings.get(maxValue));
	}

}
