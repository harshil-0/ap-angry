package io.github.runangrybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Slingshot {
    private World world;
    private SpriteBatch batch;
    private Texture slingshotTexture, birdTexture;
    private Vector2 position;  // Position of the slingshot
    private Body birdBody;  // Box2D body for the bird
    private float scale = 0.15f;  // Scaling factor for size reduction


    public Slingshot(World world, SpriteBatch batch, Texture slingshotTexture, Texture birdTexture, Vector2 position) {
        this.world = world;
        this.batch = batch;
        this.slingshotTexture = slingshotTexture;
        this.birdTexture = birdTexture;
        this.position = position;

        createBirdBody();
    }

    private void createBirdBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x , position.y+50);  // Adjust the position relative to slingshot

        birdBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(birdTexture.getWidth() * scale / 2);  // Reduce radius based on scale

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
//        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        Fixture f = birdBody.createFixture(fixtureDef);
        f.setUserData("bird'");
        birdBody.setUserData("bird");
        shape.dispose();


//        Sprite sprite = new Sprite(birdTexture);
//        sprite.setSize(birdTexture.getWidth() * scale, birdTexture.getHeight() * scale);
//        sprite.setOriginCenter();

//        Level1Screen.bodies.add(birdBody);
//        Level1Screen.sprites.add(sprite);
        Level1Screen.birdBodies.add(birdBody);
    }

    public void launchBird(Vector2 force) {
        System.out.println("Launching bird with force: " + force);
        birdBody.applyLinearImpulse(force, birdBody.getWorldCenter(), true);
    }

    public void resetBird() {
        birdBody.setTransform(position.x , position.y +50, 0);
        birdBody.setLinearVelocity(0, 0);
        birdBody.setAngularVelocity(0);
    }

    public void render() {
        // Draw slingshot
        batch.draw(
            slingshotTexture,
            position.x,
            position.y,
            slingshotTexture.getWidth() * scale,
            slingshotTexture.getHeight() * scale
        );

        // Draw bird
        batch.draw(
            birdTexture,
            birdBody.getPosition().x - birdTexture.getWidth() * scale / 2,
            birdBody.getPosition().y - birdTexture.getHeight() * scale / 2,
            birdTexture.getWidth() * scale,
            birdTexture.getHeight() * scale
        );
    }
}



