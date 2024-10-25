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

package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Level1Screen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Skin skin;
    private Levels levels;
    private PauseScreen pauseScreen;
    private WinScreen winScreen;
    private LoseScreen loseScreen;

    // Textures for birds and pigs
    private Texture birdTexture;
    private Texture pigTexture;

    // Arrays to store multiple birds and pigs
    private Array<Sprite> birds;
    private Array<Sprite> pigs;

    private Texture woodTexture;
    private Array<Sprite> woods;

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

        // Load bird and pig textures
        birdTexture = new Texture(Gdx.files.internal("ab3.png"));
        pigTexture = new Texture(Gdx.files.internal("pig1.png"));
        woodTexture = new Texture(Gdx.files.internal("sqwood.png"));


        // Create arrays for bird and pig sprites
        birds = new Array<>();
        pigs = new Array<>();
        woods = new Array<>();

        // Initialize birds at specific coordinates
        Sprite bird1 = new Sprite(birdTexture);
        bird1.setPosition(50, 10);// First bird's position
        bird1.setScale(0.2f);
        birds.add(bird1);

        Sprite bird2 = new Sprite(birdTexture);
        bird2.setPosition(150, 10);  // Second bird's position
        bird2.setScale(0.2f);
        birds.add(bird2);

        Sprite bird3 = new Sprite(birdTexture);
        bird3.setPosition(250, 10);  // Third bird's position
        bird3.setScale(0.2f);
        birds.add(bird3);

        // Initialize pigs at specific coordinates
        Sprite pig1 = new Sprite(pigTexture);
        pig1.setPosition(980, 100);  // First pig's position
        pig1.setScale(0.2f);
        pigs.add(pig1);

        Sprite pig2 = new Sprite(pigTexture);
        pig2.setPosition(1080, 100);  // Second pig's position
        pig2.setScale(0.2f);
        pigs.add(pig2);

        Sprite pig3 = new Sprite(pigTexture);
        pig3.setPosition(1180, 100);  // Third pig's position
        pig3.setScale(0.2f);
        pigs.add(pig3);

        Sprite wood1 = new Sprite(woodTexture);
        wood1.setPosition(1200, 200);  // Position for first wood piece
        wood1.setScale(1.3f);
        woods.add(wood1);

        Sprite wood2 = new Sprite(woodTexture);
        wood2.setPosition(1300, 200);  // Position for second wood piece
        wood2.setScale(1.3f);
        woods.add(wood2);

        Sprite wood3 = new Sprite(woodTexture);
        wood3.setPosition(1400, 200);  // Position for third wood piece
        wood3.setScale(1.3f);
        woods.add(wood3);

        // Load the pause button texture and create the button
        Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
        pauseButton.setPosition(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 175);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseScreen.show();
            }
        });
        stage.addActor(pauseButton);

        // Add a button to unlock the next level (e.g., Level 2)
        Texture unlockTexture = new Texture(Gdx.files.internal("winbutton.png"));
        ImageButton unlockButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(unlockTexture)));
        unlockButton.setPosition(Gdx.graphics.getWidth() - 200, 30);
        unlockButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levels.unlockLevel("Level 2"); // Unlock Level 2
                System.out.println("Level 2 has been unlocked!");
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(levels,stage,skin));  // Pass levels object
                if (pauseScreen.isMusicOn){
                    pauseScreen.isMusicOn = false;
                    pauseScreen.backgroundMusic.pause();
                }
                winScreen.show();
            }
        });
        stage.addActor(unlockButton);

        Texture Lose = new Texture(Gdx.files.internal("losebutton.png"));
        ImageButton LoseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(Lose)));
        LoseButton.setPosition(Gdx.graphics.getWidth() - 300, 30);
        LoseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                levels.unlockLevel("Level 2"); // Unlock Level 2
//                System.out.println("Level 2 has been unlocked!");
//                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(levels,stage,skin));  // Pass levels object
                if (pauseScreen.isMusicOn){
                    pauseScreen.isMusicOn = false;
                    pauseScreen.backgroundMusic.pause();
                }
                loseScreen.show();
            }
        });
        stage.addActor(LoseButton);

        pauseScreen = new PauseScreen(stage, skin);
        winScreen = new WinScreen(levels,stage,skin);
        loseScreen = new LoseScreen(levels,stage,skin);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Begin the sprite batch
        batch.begin();
        // Draw the background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw all bird sprites
        for (Sprite bird : birds) {
            bird.draw(batch);
        }

        // Draw all pig sprites
        for (Sprite pig : pigs) {
            pig.draw(batch);
        }

        for (Sprite wood : woods) wood.draw(batch);

        // End the sprite batch
        batch.end();

        // Handle the stage (UI elements)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void hide() {
        batch.dispose();
        background.dispose();
        stage.dispose();
        skin.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
        woodTexture.dispose();
        if (pauseScreen != null) {
            pauseScreen.dispose();  // Dispose of PauseScreen resources
         if (winScreen != null) {
             winScreen.dispose();}
        }
    }
}

