//package io.github.runangrybird;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//
//public class Level1Screen extends ScreenAdapter {
//    private SpriteBatch batch;
//    private Texture background;  // Background image for Level 1
//    private Stage stage;
//    private Skin skin;  // For button styling
//    private String currentScreen = "Level 1";  // Track the current screen
//
//    @Override
//    public void show() {
//        batch = new SpriteBatch();
//        // Load the background image for Level 1 (ensure 'level1background.jpg' is in the assets folder)
//        background = new Texture(Gdx.files.internal("level1bg.jpg"));
//
//        // Initialize Stage and Skin
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Ensure the skin is correctly set up
//
//        // Load the pause button image texture
//        Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
//        TextureRegion pauseRegion = new TextureRegion(pauseTexture);
//        Drawable pauseDrawable = new TextureRegionDrawable(pauseRegion);
//
//        // Create ImageButton using the pauseDrawable
//        ImageButton pauseButton = new ImageButton(pauseDrawable);
//
//        // Set the position for the pause button
//        pauseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
//
//        // Add click listener to the pause button
//        pauseButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Show the pause screen when the pause button is clicked
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen(currentScreen));
//            }
//        });
//
//        // Add the image button to the stage
//        stage.addActor(pauseButton);
//    }
//
//    @Override
//    public void render(float delta) {
//        // Clear the screen
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//
//        // Begin drawing the batch to render the background
//        batch.begin();
//        // Draw the background image across the entire screen (using the screen width and height)
//        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//
//        batch.end();
//
//        // Draw the stage with the pause button
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//
//    @Override
//    public void hide() {
//        // Dispose of resources when the screen is no longer visible
//        batch.dispose();
//        background.dispose();
//        stage.dispose();
//        skin.dispose();
//    }
//}
package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

//public class Level1Screen extends ScreenAdapter {
//    private SpriteBatch batch;
//    private Texture background;  // Background image for Level 1
//    private Stage stage;
//    private Skin skin;  // For button styling
//    private PauseScreen pauseScreen;  // To show pause menu

//    public class Level1Screen extends ScreenAdapter {
//        private SpriteBatch batch;
//        private Texture background;
//        private Stage stage;
//        private Skin skin;
//        private Levels levels;
//        private PauseScreen pauseScreen;
//
//        @Override
//        public void show() {
//            batch = new SpriteBatch();
//            background = new Texture(Gdx.files.internal("level1bg.jpg"));
//            stage = new Stage();
//            Gdx.input.setInputProcessor(stage);
//            skin = new Skin(Gdx.files.internal("uiskin.json"));
//
//            levels = new Levels(); // Initialize levels object to access and update level status
//
//            // Load the pause button texture and create the button
//            Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
//            ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
//            pauseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
//            pauseButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    pauseScreen.show();
//                }
//            });
//            stage.addActor(pauseButton);
//
//            // Add a button to unlock the next level (e.g., Level 2)
//            Texture unlockTexture = new Texture(Gdx.files.internal("resume.png"));
//            ImageButton unlockButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(unlockTexture)));
//            unlockButton.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100);
//            unlockButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    levels.unlockLevel("Level 2"); // Unlock Level 2
//                    System.out.println("Level 2 has been unlocked!");
//
//                    ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen());
//                }
//            });
//            stage.addActor(unlockButton);
//
//            pauseScreen = new PauseScreen(stage, skin);
//        }
//
//        @Override
//        public void render(float delta) {
//            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//            batch.begin();
//            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//            batch.end();
//            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//            stage.draw();
//        }
//
//        @Override
//        public void hide() {
//            batch.dispose();
//            background.dispose();
//            stage.dispose();
//            skin.dispose();
//        }
//    }

public class Level1Screen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Skin skin;
    private Levels levels;
    private PauseScreen pauseScreen;

    public Level1Screen(Levels levels) {
        this.levels = levels;  // Initialize with the shared Levels instance
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("level1bg.jpg"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Load the pause button texture and create the button
        Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
        pauseButton.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseScreen.show();
            }
        });
        stage.addActor(pauseButton);

        // Add a button to unlock the next level (e.g., Level 2)
        Texture unlockTexture = new Texture(Gdx.files.internal("resume.png"));
        ImageButton unlockButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(unlockTexture)));
        unlockButton.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100);
        unlockButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levels.unlockLevel("Level 2"); // Unlock Level 2
                System.out.println("Level 2 has been unlocked!");

                ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen(levels));  // Pass levels object
            }
        });
        stage.addActor(unlockButton);

        pauseScreen = new PauseScreen(stage, skin);
    }

    @Override
        public void render(float delta) {
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            batch.begin();
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        @Override
        public void hide() {
            batch.dispose();
            background.dispose();
            stage.dispose();
            skin.dispose();
        }
}
