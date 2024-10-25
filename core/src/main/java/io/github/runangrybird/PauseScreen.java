//package io.github.runangrybird;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//
//public class PauseScreen extends ScreenAdapter {
//    private Stage stage;
//    private BitmapFont font;
//    private Skin skin;  // Skin for button styling
//
//    private String currentScreen; // To store which screen is currently active
//
//    public PauseScreen(String currentScreen) {
//        this.currentScreen = currentScreen; // To know the screen we need to return to
//    }
//
//    @Override
//    public void show() {
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//
//        // Load skin
//        try {
//            skin = new Skin(Gdx.files.internal("uiskin.json"));
//        } catch (Exception e) {
//            System.err.println("[Error] Failed to load skin: " + e.getMessage());
//            return;
//        }
//
//        // Create font
//        font = new BitmapFont();
//
//        // Create table to layout pause menu
//        Table table = new Table();
//        table.top();
//        table.setFillParent(true);
//
//        // Create title label
//        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
//        Label titleLabel = new Label("Pause Menu", labelStyle);
//        titleLabel.setFontScale(2);
//        table.add(titleLabel).padBottom(50).colspan(2).center().row();
//
//        // Load images for buttons
//        Texture resumeTexture = new Texture(Gdx.files.internal("resume.png"));
//        Texture restartTexture = new Texture(Gdx.files.internal("restart.png"));
//        Texture returnTexture = new Texture(Gdx.files.internal("returnlevels.png"));
//
//        // Create image buttons using the textures
//        TextureRegion resumeRegion = new TextureRegion(resumeTexture);
//        Drawable resumeDrawable = new TextureRegionDrawable(resumeRegion);
//        ImageButton resumeButton = new ImageButton(resumeDrawable);
//        resumeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Close the pause screen and resume the game
//                switch (currentScreen) {
//                    case "Level 1":
//                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen());
//                        break;
//                    case "Level 2":
//                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen());
//                        break;
//                    case "Level 3":
//                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen());
//                        break;
//                }
//            }
//        });
//        table.add(resumeButton).padBottom(20).fillX().height(60).row();
//
//        TextureRegion restartRegion = new TextureRegion(restartTexture);
//        Drawable restartDrawable = new TextureRegionDrawable(restartRegion);
//        ImageButton restartButton = new ImageButton(restartDrawable);
//        restartButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Restart the current level
//                switch (currentScreen) {
//                    case "Level 1":
//                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen());
//                        break;
//                    case "Level 2":
//                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen());
//                        break;
//                    case "Level 3":
//                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen());
//                        break;
//                }
//            }
//        });
//        table.add(restartButton).padBottom(20).fillX().height(60).row();
//
//        TextureRegion returnRegion = new TextureRegion(returnTexture);
//        Drawable returnDrawable = new TextureRegionDrawable(returnRegion);
//        ImageButton returnButton = new ImageButton(returnDrawable);
//        returnButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Return to the level selection screen
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen());
//            }
//        });
//        table.add(returnButton).padBottom(20).fillX().height(60).row();
//
//        // Add the table to the stage
//        stage.addActor(table);
//    }
//
//    @Override
//    public void render(float delta) {
//        // Clear the screen
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//
//        // Draw the stage with buttons
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//
//    @Override
//    public void hide() {
//        stage.dispose();
//        font.dispose();
//        skin.dispose();
//    }
//}
package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseScreen {

    private Stage stage;
    private Skin skin;  // Skin for button styling
    private Dialog pauseDialog;
    private Levels levels;  // Shared Levels object
    public Music backgroundMusic;  // For controlling the music
    public boolean isMusicOn = true;

    public PauseScreen(Levels levels) {
        this.levels = levels;  // Initialize with the shared Levels instance
    }

    public PauseScreen(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
        // Load the music file here (using a hardcoded path for now)
        try {
            this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio_episode_intro.mp3"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading music: " + e.getMessage());
        }
        this.backgroundMusic.setLooping(true);  // Loop the music
        this.backgroundMusic.play();  // Start playing the music
        createPauseDialog();
    }

    private void createPauseDialog() {
        pauseDialog = new Dialog("Pause Menu", skin);
        pauseDialog.setModal(true);
        pauseDialog.setMovable(false);
        pauseDialog.setResizable(false);

        Table table = pauseDialog.getContentTable();

        // Load images for buttons
        Texture resumeTexture = new Texture(Gdx.files.internal("resume.png"));
        Texture restartTexture = new Texture(Gdx.files.internal("restart.png"));
        Texture returnTexture = new Texture(Gdx.files.internal("returnlevels.png"));
        Texture musicOnTexture = new Texture(Gdx.files.internal("music_off.png"));
        Texture musicOffTexture = new Texture(Gdx.files.internal("music_on.png"));

        // Resume button
        TextureRegion resumeRegion = new TextureRegion(resumeTexture);
        Drawable resumeDrawable = new TextureRegionDrawable(resumeRegion);
        ImageButton resumeButton = new ImageButton(resumeDrawable);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseDialog.hide();  // Close the dialog and resume the game
            }
        });
        table.add(resumeButton).padBottom(20).fillX().height(60).row();

        // Restart button
        TextureRegion restartRegion = new TextureRegion(restartTexture);
        Drawable restartDrawable = new TextureRegionDrawable(restartRegion);
        ImageButton restartButton = new ImageButton(restartDrawable);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Restart the current level
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(levels));
            }
        });
        table.add(restartButton).padBottom(20).fillX().height(60).row();

        // Return to levels button
        TextureRegion returnRegion = new TextureRegion(returnTexture);
        Drawable returnDrawable = new TextureRegionDrawable(returnRegion);
        ImageButton returnButton = new ImageButton(returnDrawable);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Return to level selection screen
                ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen(levels));
            }
        });
        table.add(returnButton).padBottom(20).fillX().height(60).row();

        // Music toggle button
        TextureRegion musicOnRegion = new TextureRegion(musicOnTexture);
        TextureRegion musicOffRegion = new TextureRegion(musicOffTexture);
        Drawable musicOnDrawable = new TextureRegionDrawable(musicOnRegion);
        Drawable musicOffDrawable = new TextureRegionDrawable(musicOffRegion);

        final ImageButton musicButton = new ImageButton(musicOnDrawable);
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isMusicOn) {
                    backgroundMusic.pause();  // Pause the music
                    musicButton.getStyle().imageUp = musicOffDrawable;  // Switch to 'off' icon
                } else {
                    backgroundMusic.play();  // Play the music
                    musicButton.getStyle().imageUp = musicOnDrawable;  // Switch to 'on' icon
                }
                isMusicOn = !isMusicOn;  // Toggle the state
            }
        });
        table.add(musicButton).padBottom(20).fillX().height(60).row();
    }

    public void show() {
        pauseDialog.show(stage);  // Display the dialog
    }

    public void dispose() {
        backgroundMusic.dispose();  // Clean up the music resource
    }
}


