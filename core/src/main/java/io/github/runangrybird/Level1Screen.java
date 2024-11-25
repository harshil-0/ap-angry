package io.github.runangrybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Level1Screen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private World world;  // Box2D world
    private Box2DDebugRenderer debugRenderer;  // Optional for debugging physics
    private Levels levels;
    private Array<Sprite> sprites;  // For visual representation of Box2D bodies
    private Array<Body> bodies;  // Box2D bodies
    private PauseScreen pauseScreen;
    private WinScreen winScreen;
    private LoseScreen loseScreen;
    private Stage stage;
    private Skin skin;
    private Slingshot slingshot;

    private Texture birdTexture, pigTexture, woodTexture;

    public Level1Screen(Levels levels) {
        this.levels = levels;  // Initialize with the shared Levels instance
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("level1bg.jpg"));

        // Initialize Box2D world with gravity
        world = new World(new Vector2(0, -100f), true);  // Gravity pointing downward
        debugRenderer = new Box2DDebugRenderer();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);  // Set input processor to handle button clicks
        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Assuming a default skin

        Texture slingshotTexture = new Texture(Gdx.files.internal("slingshot.png"));
        birdTexture = new Texture(Gdx.files.internal("ab3.png"));
        slingshot = new Slingshot(world, batch, slingshotTexture, birdTexture, new Vector2(200, 210));
        // Load textures
//        birdTexture = new Texture(Gdx.files.internal("ab3.png"));
        pigTexture = new Texture(Gdx.files.internal("pig1.png"));
        woodTexture = new Texture(Gdx.files.internal("sqwood.png"));

        sprites = new Array<>();
        bodies = new Array<>();

        // Create ground
        createGround();

        // Create birds
//        createDynamicBody(150, 300, birdTexture, 0.2f);

        // Create pigs
        createDynamicBody(1200, 400, pigTexture, 0.2f);
        createDynamicBody(1280, 400, pigTexture, 0.2f);

        // Create woods
        createDynamicBody(1200, 300, woodTexture, 1f);
        createDynamicBody(1300, 300, woodTexture, 1f);

//         Load the pause button texture and create the button
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
                if (pauseScreen.isMusicOn) {
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
                if (pauseScreen.isMusicOn) {
                    pauseScreen.isMusicOn = false;
                    pauseScreen.backgroundMusic.pause();
                }
                loseScreen.show();
            }
        });
        stage.addActor(LoseButton);

        pauseScreen = new PauseScreen(stage, skin);
        winScreen = new WinScreen(levels, stage, skin);
        loseScreen = new LoseScreen(levels, stage, skin);

        stage.addListener(new InputListener() {
            private Vector2 dragStart = new Vector2();
            private Vector2 dragEnd = new Vector2();
            private boolean isDragging = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dragStart.set(x, y);
                isDragging = true;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                dragEnd.set(x, y);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isDragging) {
                    dragEnd.set(x, y);
                    Vector2 force = dragStart.sub(dragEnd).scl(100000000f);
                    force.y += Math.abs(force.x) * 0.01f;
                    slingshot.launchBird(force);
                    isDragging = false;
                }
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.R) {
                    slingshot.resetBird(); // Reset bird on pressing 'R'
                }
                return true;
            }
        });
    }

        private void createGround() {
        // Define a static body for the ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, 190);  // Y position is 100px above the bottom
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        // Define a box shape for the ground
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(Gdx.graphics.getWidth(), 10);  // Wide box for the ground

        // Create a fixture for the ground
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.density = 0;
        fixtureDef.friction = 1;

        groundBody.createFixture(fixtureDef);
        groundShape.dispose();
    }

    private void createDynamicBody(float x, float y, Texture texture, float scale) {
        // Define a dynamic body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        // Define a circular shape for birds/pigs or a box shape for woods
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() * scale / 2, texture.getHeight() * scale / 2);

        // Create a fixture for the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;  // Bounciness

        body.createFixture(fixtureDef);
        shape.dispose();

        // Link sprite with body
        Sprite sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth() * scale, texture.getHeight() * scale);
        sprite.setOriginCenter();
        sprites.add(sprite);
        bodies.add(body);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Step the physics simulation
        world.step(1 / 60f, 6, 2);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Update and render sprites based on Box2D bodies
        for (int i = 0; i < bodies.size; i++) {
            Body body = bodies.get(i);
            Sprite sprite = sprites.get(i);
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
            sprite.draw(batch);
        }
        slingshot.render();
        batch.end();

        // Optionally draw the physics debug renderer
//        debugRenderer.render(world, batch.getProjectionMatrix());

        stage.act(delta);  // Update stage
        stage.draw();  // Render stage elements
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
//        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void hide() {
        batch.dispose();
        background.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
        woodTexture.dispose();
        world.dispose();
        debugRenderer.dispose();
        stage.dispose();
        skin.dispose();
        if (pauseScreen != null) {
         pauseScreen.dispose();}
        if (winScreen != null) {
             winScreen.dispose();}
        }

    }




//package io.github.runangrybird;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ScreenUtils;
//
//public class Level1Screen extends ScreenAdapter {
//    private SpriteBatch batch;
//    private Texture background;
//    private Stage stage;
//    private Skin skin;
//    private Levels levels;
//    private PauseScreen pauseScreen;
//    private WinScreen winScreen;
//    private LoseScreen loseScreen;
//
//    // Textures for birds and pigs
//    private Texture birdTexture;
//    private Texture pigTexture;
//
//    // Arrays to store multiple birds and pigs
//    private Array<Sprite> birds;
//    private Array<Sprite> pigs;
//
//    private Texture woodTexture;
//    private Array<Sprite> woods;
//
//    private Slingshot slingshot;
////    private Sprite currentBird;
////    private int currentBirdIndex;
//
//    public Level1Screen(Levels levels) {
//        this.levels = levels;  // Initialize with the shared Levels instance
//    }
//
//    @Override
//    public void show() {
//        batch = new SpriteBatch();
//        background = new Texture(Gdx.files.internal("level1bg.jpg"));
//        stage = new Stage();
//        Gdx.input.setInputProcessor(stage);
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//
//        // Load bird and pig textures
//        birdTexture = new Texture(Gdx.files.internal("ab3.png"));
//        pigTexture = new Texture(Gdx.files.internal("pig1.png"));
//        woodTexture = new Texture(Gdx.files.internal("sqwood.png"));
//        slingshot = new Slingshot("slingshot.png", 220, -120);
//
//
//        // Create arrays for bird and pig sprites
//        birds = new Array<>();
//        pigs = new Array<>();
//        woods = new Array<>();
////        currentBirdIndex = 0;
//
//        // Initialize birds at specific coordinates
//        Sprite bird1 = new Sprite(birdTexture);
//        bird1.setPosition(50, 10);// First bird's position
//        bird1.setScale(0.2f);
//        birds.add(bird1);
//
//        Sprite bird2 = new Sprite(birdTexture);
//        bird2.setPosition(150, 10);  // Second bird's position
//        bird2.setScale(0.2f);
//        birds.add(bird2);
//
//        Sprite bird3 = new Sprite(birdTexture);
//        bird3.setPosition(250, 10);  // Third bird's position
//        bird3.setScale(0.2f);
//        birds.add(bird3);
//
//        // Initialize pigs at specific coordinates
//        Sprite pig1 = new Sprite(pigTexture);
//        pig1.setPosition(980, 100);  // First pig's position
//        pig1.setScale(0.2f);
//        pigs.add(pig1);
//
//        Sprite pig2 = new Sprite(pigTexture);
//        pig2.setPosition(1080, 100);  // Second pig's position
//        pig2.setScale(0.2f);
//        pigs.add(pig2);
//
//        Sprite pig3 = new Sprite(pigTexture);
//        pig3.setPosition(1180, 100);  // Third pig's position
//        pig3.setScale(0.2f);
//        pigs.add(pig3);
//
//        Sprite wood1 = new Sprite(woodTexture);
//        wood1.setPosition(1200, 200);  // Position for first wood piece
//        wood1.setScale(1.3f);
//        woods.add(wood1);
//
//        Sprite wood2 = new Sprite(woodTexture);
//        wood2.setPosition(1300, 200);  // Position for second wood piece
//        wood2.setScale(1.3f);
//        woods.add(wood2);
//
//        Sprite wood3 = new Sprite(woodTexture);
//        wood3.setPosition(1400, 200);  // Position for third wood piece
//        wood3.setScale(1.3f);
//        woods.add(wood3);
//
//        // Load the pause button texture and create the button
//        Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
//        ImageButton pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
//        pauseButton.setPosition(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 175);
//        pauseButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                pauseScreen.show();
//            }
//        });
//        stage.addActor(pauseButton);
//
//        // Add a button to unlock the next level (e.g., Level 2)
//        Texture unlockTexture = new Texture(Gdx.files.internal("winbutton.png"));
//        ImageButton unlockButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(unlockTexture)));
//        unlockButton.setPosition(Gdx.graphics.getWidth() - 200, 30);
//        unlockButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                levels.unlockLevel("Level 2"); // Unlock Level 2
//                System.out.println("Level 2 has been unlocked!");
////                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(levels,stage,skin));  // Pass levels object
//                if (pauseScreen.isMusicOn){
//                    pauseScreen.isMusicOn = false;
//                    pauseScreen.backgroundMusic.pause();
//                }
//                winScreen.show();
//            }
//        });
//        stage.addActor(unlockButton);
//
//        Texture Lose = new Texture(Gdx.files.internal("losebutton.png"));
//        ImageButton LoseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(Lose)));
//        LoseButton.setPosition(Gdx.graphics.getWidth() - 300, 30);
//        LoseButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                levels.unlockLevel("Level 2"); // Unlock Level 2
////                System.out.println("Level 2 has been unlocked!");
////                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(levels,stage,skin));  // Pass levels object
//                if (pauseScreen.isMusicOn){
//                    pauseScreen.isMusicOn = false;
//                    pauseScreen.backgroundMusic.pause();
//                }
//                loseScreen.show();
//            }
//        });
//        stage.addActor(LoseButton);
//
//        pauseScreen = new PauseScreen(stage, skin);
//        winScreen = new WinScreen(levels,stage,skin);
//        loseScreen = new LoseScreen(levels,stage,skin);
//    }
//
//    @Override
//    public void render(float delta) {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//
//        // Begin the sprite batch
//        batch.begin();
//        // Draw the background
//        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        slingshot.render(batch);
//
//        // Draw all bird sprites
//        for (Sprite bird : birds) {
//            bird.draw(batch);
//        }
//
//        // Draw all pig sprites
//        for (Sprite pig : pigs) {
//            pig.draw(batch);
//        }
//
//        for (Sprite wood : woods) wood.draw(batch);
//
//        // End the sprite batch
//        batch.end();
//
//        // Handle the stage (UI elements)
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//
//    @Override
//    public void hide() {
//        batch.dispose();
//        background.dispose();
//        stage.dispose();
//        skin.dispose();
//        birdTexture.dispose();
//        pigTexture.dispose();
//        woodTexture.dispose();
//        if (pauseScreen != null) {
//            pauseScreen.dispose();  // Dispose of PauseScreen resources
//         if (winScreen != null) {
//             winScreen.dispose();}
//        }
//    }
//}

