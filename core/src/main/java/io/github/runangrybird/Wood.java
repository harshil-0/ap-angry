package io.github.runangrybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Wood {
    private String type;
    private Body body;
    private Texture texture;
    private float scale;
    private float x, y;
    private Sprite sprite; // Added Sprite field

    public Wood() {}

    public Wood(World world, float x, float y, Texture texture, float scale, String type) {
        this.texture = texture;
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.type = type;

        createDynamicBody(world);
    }

    private void createDynamicBody(World world) {
        // Define body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        // Define shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(texture.getWidth() * scale / 2, texture.getHeight() * scale / 2);

        // Define fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Higher density for wood
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f; // Moderate restitution for wood

        body.createFixture(fixtureDef).setUserData("wood");
        body.setUserData("wood");

        shape.dispose();
        createSprite(); // Initialize sprite
    }

    private void createSprite() {
        sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth() * scale, texture.getHeight() * scale);
        sprite.setOriginCenter();
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2); // Center sprite at body position
    }

    public Body getBody() {
        return body;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public Sprite getSprite() { // Getter for Sprite
        return sprite;
    }

    public SaveGameManager.WoodD toData() {
        SaveGameManager.WoodD data = new SaveGameManager.WoodD();
        data.type = this.type;
        data.x = this.x;
        data.y = this.y;
        data.scale = this.scale;
        return data;
    }

}
