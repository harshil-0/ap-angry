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

import java.util.ArrayList;
import java.util.List;

public class Level2Screen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture background;
    private World world;  // Box2D world
    private Box2DDebugRenderer debugRenderer;  // Optional for debugging physics
    private Levels levels;
    public static Array<Sprite> sprites = new Array<>();  // For visual representation of Box2D bodies
    public static Array<Body> bodies = new Array<>();  // Box2D bodies
    private PauseScreen pauseScreen;
    private WinScreen winScreen;
    private LoseScreen loseScreen;
    private Stage stage;
    private Skin skin;
    private Slingshot slingshot;
    private List<Body> bodiesToRemove = new ArrayList<>();
    private List<Sprite> spritesToRemove = new ArrayList<>();
    private Array<Body> pigBodies = new Array<>();
    public static Array<Body> birdBodies = new Array<>();
    private Array<Wood> woodList= new Array<>();
    private Array<Pig> pigList= new Array<>();
//    private NewGameScreen newGameScreen;


    private Texture birdTexture, pigTexture, woodTexture;

    public Level2Screen(Levels levels) {
        this.levels = levels;// Initialize with the shared Levels instance
//        this.newGameScreen = newGameScreen;

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("level1bg.jpg"));
//        NewGameScreen newGameScreen = new NewGameScreen(levels);

        // Initialize Box2D world with gravity
        world = new World(new Vector2(0, -10f), true);  // Gravity pointing downward
        debugRenderer = new Box2DDebugRenderer();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);  // Set input processor to handle button clicks
        skin = new Skin(Gdx.files.internal("uiskin.json"));  // Assuming a default skin

        birdTexture = new Texture(Gdx.files.internal("ab3.png"));
        Bird bird = new Bird("bird", birdTexture, 500, 260);

        Texture slingshotTexture = new Texture(Gdx.files.internal("slingshot.png"));
        slingshot = new Slingshot(world, batch, slingshotTexture, bird, new Vector2(500, 210));

        // Load textures
//        birdTexture = new Texture(Gdx.files.internal("ab3.png"));
        pigTexture = new Texture(Gdx.files.internal("pig1.png"));
        woodTexture = new Texture(Gdx.files.internal("sqwood.png"));

//        sprites = new Array<>();
//        bodies = new Array<>();

        // Create ground
        createGround();

        // Create birds
//        createDynamicBody(150, 300, birdTexture, 0.2f);

        // Create pigs
//        createDynamicBody(1150, 400, pigTexture, 0.2f,"pig");
////        createDynamicBody(1300, 400, pigTexture, 0.2f,"pig");
//
//        // Create woods
//        createDynamicBody(1000, 300, woodTexture, 1f,"wood");
//        createDynamicBody(1300, 300, woodTexture, 1f,"wood");
//        createDynamicBody(1000, 400, woodTexture, 1f,"wood");
//        createDynamicBody(1300, 400, woodTexture, 1f,"wood");

        woodList.add(new Wood(world, 1000, 300, woodTexture, 1f,"wood"));
        woodList.add(new Wood(world, 1300, 300, woodTexture, 1f,"wood"));
        woodList.add(new Wood(world, 1000, 400, woodTexture, 1f,"wood"));
        woodList.add(new Wood(world, 1300, 400, woodTexture, 1f,"wood"));
        Pig p1 = new Pig(world, 1150, 400, pigTexture, 0.2f,"pig");
//        Pig p2 = new Pig(world, 1300, 400, pigTexture, 0.2f,"pig");
        pigList.add(p1);
//        pigList.add(p2);

        for (Wood wood : woodList) {
            sprites.add(wood.getSprite());
            bodies.add(wood.getBody());
        }
        for (Pig pig : pigList) {
            sprites.add(pig.getSprite());
            bodies.add(pig.getBody());
            pigBodies.add(pig.getBody());
        }

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
                levels.unlockLevel("Level 3"); // Unlock Level 2
//                newGameScreen.refreshLevelButtons(stage);
                System.out.println("Level 3 has been unlocked!");
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

        pauseScreen = new PauseScreen(stage, skin,"Level 2");
        winScreen = new WinScreen(levels, stage, skin,"Level 2");
        loseScreen = new LoseScreen(levels, stage, skin,"Level 2");
        setupCollisionDetection();

        stage.addListener(new InputListener() {
            private Vector2 dragStart = new Vector2();
            private Vector2 dragEnd = new Vector2();
            private boolean isDragging = false;
            private boolean birdLaunched = false; // Tracks if the bird has been launched
            private int resetCount = 2;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (birdLaunched) {
                    // Prevent further dragging if bird is already launched
                    System.out.println("Bird already launched!");
                    return false;
                }
                dragStart.set(x, y);
                isDragging = true;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (!birdLaunched) {
                    dragEnd.set(x, y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isDragging && !birdLaunched) {
                    dragEnd.set(x, y);
                    Vector2 force = dragStart.sub(dragEnd).scl(100000000000000f);
//                    force.y += Math.abs(force.x); // Adjust force for a parabolic trajectory
                    slingshot.launchBird(force); // Launch the bird with calculated force
                    isDragging = false;
                    birdLaunched = true; // Set the flag to true after launching
                    System.out.println("Bird launched!");
                }
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.R) {
                    if (birdLaunched && resetCount > 0) {
                        slingshot.resetBird(); // Reset the bird
                        resetCount--;          // Decrement the reset counter
                        birdLaunched = false;  // Allow the bird to be launched again
                        System.out.println("Bird reset! Remaining resets: " + resetCount);
                    } else if (resetCount <= 0) {
                        // Handle when no resets are left
                        System.out.println("No resets remaining!");
                        if (pauseScreen.isMusicOn) {
                            pauseScreen.isMusicOn = false;
                            pauseScreen.backgroundMusic.pause();
                        }
                        loseScreen.show();
                    } else {
                        // Handle case where bird has not yet been launched
                        System.out.println("Cannot reset before launching!");
                    }
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

//    private void createDynamicBody(float x, float y, Texture texture, float scale, String userData) {
//        // Define a dynamic body
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(x, y);
//
//        Body body = world.createBody(bodyDef);
//
//        // Define a box shape
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(texture.getWidth() * scale / 2, texture.getHeight() * scale / 2);
//
//        // Create a fixture
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.density = 1f;
//        fixtureDef.friction = 0.5f;
//        fixtureDef.restitution = 0.3f;
//
//        Fixture fixture = body.createFixture(fixtureDef);
//        fixture.setUserData(userData); // Set user data (e.g., "bird" or "pig")
//        body.setUserData(userData);
//        shape.dispose();
//
//        // Add sprite
//        Sprite sprite = new Sprite(texture);
//        sprite.setSize(texture.getWidth() * scale, texture.getHeight() * scale);
//        sprite.setOriginCenter();
//        sprites.add(sprite);
//        bodies.add(body);
//        if (body.getUserData().equals("pig")){
//            pigBodies.add(body);
//        }
//    }


    private void setupCollisionDetection() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Get the two bodies involved in the collision
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Object userDataA = fixtureA.getBody().getUserData();
                Object userDataB = fixtureB.getBody().getUserData();
                if (userDataA == null || userDataB == null) {
                    System.out.println("Collision ignored due to null UserData.");
                    return; // Avoid further processing
                }
                System.out.println("UserData A: " + userDataA + ", UserData B: " + userDataB);
                // Check if one of the bodies is a bird and the other is a pig
                if ("bird".equals(bodyA.getUserData()) && "pig".equals(bodyB.getUserData())) {
                    // Handle bird hitting pig
                    handleBirdHitsPig(bodyB);  // Swap order to ensure correct removal
                } else if ("pig".equals(bodyA.getUserData()) && "bird".equals(bodyB.getUserData())) {
                    // Handle pig hitting bird
                    handleBirdHitsPig(bodyA);  // Swap order to ensure correct removal
                }
            }

            @Override
            public void endContact(Contact contact) {
                // Optional: Handle when contact ends
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                // Optional: Modify contact behavior
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                // Optional: Handle post-collision effects
            }
        });
    }

    private void handleBirdHitsPig(Body pigBody) {
        System.out.println("Bird hit pig!");

        // Remove the pig body and its corresponding sprite
        int pigIndex = bodies.indexOf(pigBody, true); // Find the index of the pig body
        if (pigIndex != -1) {
            bodiesToRemove.add(pigBody);          // Mark the pig body for removal
            spritesToRemove.add(sprites.get(pigIndex)); // Mark the corresponding sprite for removal
            pigBodies.removeValue(pigBody, true);  // Mark the pig body for removal
        }

    }

    private boolean allPigsRemoved = false;
    private boolean allBirdsRemoved = false;

    private void removeBodies() {
        // Remove bodies and their corresponding sprites in reverse order to avoid index issues
        for (int i = bodiesToRemove.size() - 1; i >= 0; i--) {
            Body body = bodiesToRemove.get(i);
            int index = bodies.indexOf(body, true); // Find the index of the body
            if (index != -1) {
                world.destroyBody(body);           // Destroy the body in the world
                bodies.removeIndex(index);        // Remove the body from the list
                sprites.removeIndex(index);       // Remove the sprite from the list
            }
        }
        bodiesToRemove.clear();
        spritesToRemove.clear();

        // Only call onAllPigsRemoved once when pigs are removed
        if (!allPigsRemoved && pigBodies.isEmpty()) {
            allPigsRemoved = true; // Set flag to true after calling the method
            onAllPigsRemoved();    // Call a method to handle the event when all pigs are removed
        }

        // Only call onAllBirdsRemoved once when birds are removed
        if (!allBirdsRemoved && birdBodies.isEmpty()) {
            allBirdsRemoved = true; // Set flag to true after calling the method
            // onAllBirdsRemoved();    // Call a method to handle the event when all birds are removed
        }
    }




    private void onAllPigsRemoved() {
        levels.unlockLevel("Level 3"); // Unlock Level 2
        System.out.println("Level 3 has been unlocked!");

        // Stop music if playing
        if (pauseScreen.isMusicOn) {
            pauseScreen.isMusicOn = false;
            pauseScreen.backgroundMusic.pause();
        }

        // Show WinScreen
        winScreen.show();
    }





//    private void handleBirdHitsPig(Body pigBody) {
//        System.out.println("Bird hit pig!");
//        bodiesToRemove.add(pigBody); // Mark the pig for removal
//        for (int i = 0; i < bodies.size; i++) {
//            if (bodies.get(i) == pigBody) {
//                spritesToRemove.add(sprites.get(i));
//                break;
//            }
//        }
//    }
//
//    private void removeBodies() {
//        for (Body body : bodiesToRemove) {
//            world.destroyBody(body);
//        }
//        bodiesToRemove.clear();
//        for (Sprite sprite : spritesToRemove) {
//            sprites.removeValue(sprite, true);
//        }
//        spritesToRemove.clear();
//    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        float timeScale = 2.0f; // Speed multiplier (2x faster)
        float scaledDelta = delta * timeScale;
        // Step the physics simulation
        world.step(scaledDelta, 10, 2);

        removeBodies();


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
