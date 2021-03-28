package com.eos.spatialracoon.level;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Level {

	private static final HashMap<Integer, LevelSetting> levelSettings = createLevel();

	private static HashMap<Integer, LevelSetting> createLevel() {
		HashMap<Integer, LevelSetting> levelSettings = new HashMap<>();
		levelSettings.put(1, new LevelSetting(1, 10, 1, 500,
											  2, 2, 10));
		levelSettings.put(2, new LevelSetting(2, 15, 2,
											  1500, 3, 3,
											  20));
		levelSettings.put(3, new LevelSetting(3, 17, 3,
											  5000, 4, 4,
											  30));
		levelSettings.put(4, new LevelSetting(4, 20, 3,
											  Integer.MAX_VALUE, 5, 5,
											  40));
		return levelSettings;
	}

	public static LevelSetting getLevelSettings(int level) {
		Integer maxValue = Collections.max(levelSettings.entrySet(),
										   Comparator.comparingInt(Map.Entry::getKey))
									  .getKey();

		return levelSettings.getOrDefault(level, levelSettings.get(maxValue));
	}

}
