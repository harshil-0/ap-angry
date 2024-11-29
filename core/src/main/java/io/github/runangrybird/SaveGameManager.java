package io.github.runangrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SaveGameManager {
    private static final String SAVE_FILE = "savegame.txt";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Save Game State
    public static void saveGame(int birdResetCount, String currentLevel, List<Pig> pigList, List<Wood> woodList) {
        FileHandle file = Gdx.files.local(SAVE_FILE);

        // Serialize game state
        SaveData saveData = new SaveData();
        saveData.birdResetCount = birdResetCount;
        saveData.currentLevel = currentLevel;

        for (Pig pig : pigList) {
            saveData.pigs.add(pig.toData()); // Convert Pig to PigD
        }

        for (Wood wood : woodList) {
            saveData.woods.add(wood.toData()); // Convert Wood to WoodD
        }

        // Convert to JSON
        String json = GSON.toJson(saveData);
        file.writeString(json, false);

        Gdx.app.log("SaveGameManager", "Game saved successfully.");
    }

    // Load Game State
    public static SaveData loadGame(World world, Texture pigTexture, Texture woodTexture,
                                    List<Pig> pigList, List<Wood> woodList) {
        FileHandle file = Gdx.files.local(SAVE_FILE);

        if (!file.exists()) {
            Gdx.app.log("SaveGameManager", "No save file found. Starting new game.");
            return null; // No save data available
        }

        String json = file.readString();

        // Deserialize game state
        SaveData saveData = GSON.fromJson(json, SaveData.class);

        // Recreate pigs
        pigList.clear();
        for (PigD pigData : saveData.pigs) {
            pigList.add(new Pig(world, pigData.x, pigData.y, pigTexture,
                pigData.scale, pigData.type));
        }

        // Recreate woods
        woodList.clear();
        for (WoodD woodData : saveData.woods) {
            woodList.add(new Wood(world, woodData.x, woodData.y, woodTexture,
                woodData.scale, woodData.type));
        }

        Gdx.app.log("SaveGameManager", "Game loaded successfully: Level = " + saveData.currentLevel);
        return saveData;
    }

    // Inner classes for saving structured data
    static class SaveData {
        int birdResetCount;
        String currentLevel;
        List<PigD> pigs = new ArrayList<>();
        List<WoodD> woods = new ArrayList<>();
    }

    static class PigD {
        String type;
        float x;
        float y;
        float scale;
    }

    static class WoodD {
        String type;
        float x;
        float y;
        float scale;
    }
}
