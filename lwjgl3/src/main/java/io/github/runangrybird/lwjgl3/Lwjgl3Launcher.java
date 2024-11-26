package io.github.runangrybird.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.runangrybird.Main2;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // macOS/Windows support
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main2(), getDefaultConfiguration());  // Launch Main2 instead of Start
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Angry Bird");
        configuration.useVsync(false);
        configuration.setForegroundFPS(120);  // FPS limited to 60
        configuration.setWindowedMode(1600, 940);
        configuration.setWindowIcon("angry.jpeg");  // Ensure this icon is in your assets
        return configuration;
    }
}
