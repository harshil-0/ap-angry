package io.github.runangrybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Slingshot {
    private World world;
    private SpriteBatch batch;
    private Texture slingshotTexture;
    private Vector2 position; // Position of the slingshot
    private Bird bird;
    private float scale = 0.15f;

    public Slingshot(World world, SpriteBatch batch, Texture slingshotTexture, Bird bird, Vector2 position) {

        this.world = world;
        this.batch = batch;
        this.slingshotTexture = slingshotTexture;
        this.bird = bird;
        this.position = position;

        bird.createDynamicBody(world, scale);
        Level1Screen.birdBodies.add(bird.getBirdBody());
    }

    public void launchBird(Vector2 force) {
        System.out.println("Launching bird with force: " + force);
        bird.getBirdBody().applyLinearImpulse(force, bird.getBirdBody().getWorldCenter(), true);
    }

    public void resetBird() {
        bird.resetPosition(position.x, position.y + 50);
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
        Texture birdTexture = bird.getTexture();
        Vector2 birdPos = bird.getBirdBody().getPosition();
        batch.draw(
            birdTexture,
            birdPos.x - birdTexture.getWidth() * scale / 2,
            birdPos.y - birdTexture.getHeight() * scale / 2,
            birdTexture.getWidth() * scale,
            birdTexture.getHeight() * scale
        );
    }
}
