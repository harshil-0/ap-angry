package io.github.runangrybird;

import java.util.LinkedHashMap;
import java.util.Map;

public class Levels {
    private Map<String, Boolean> levelList;

    public Levels() {
        levelList = new LinkedHashMap<>(); // Use LinkedHashMap to preserve insertion order
        levelList.put("Level 1", false); // false indicates unlocked
        levelList.put("Level 2", true);  // true indicates locked
        levelList.put("Level 3", true);  // Add more levels as needed
    }

    public Map<String, Boolean> getLevelList() {
        return levelList;
    }
    public void unlockLevel(String level) {
        if (levelList.containsKey(level)) {
            if (levelList.get(level)) { // If the level is locked
                levelList.put(level, false); // Unlock the level by setting to false
                System.out.println("[Info] " + level + " has been unlocked.");
            } else {
                System.out.println("[Info] " + level + " is already unlocked.");
            }
        } else {
            System.err.println("[Error] Level not found: " + level);
        }
    }
}
