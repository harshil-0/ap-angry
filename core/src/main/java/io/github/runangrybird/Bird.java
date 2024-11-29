package io.github.runangrybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Bird {
    private String type;
    private Texture texture;
    private float x, y;
    private Body birdBody;

    public Bird(String type, Texture texture, float x, float y) {
        this.type = type;
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void createDynamicBody(World world, float scale) {
        // Define body properties
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        birdBody = world.createBody(bodyDef);

        // Define shape
        CircleShape shape = new CircleShape();
        shape.setRadius(texture.getWidth() * scale / 2);

        // Define fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.5f;

        Fixture fixture = birdBody.createFixture(fixtureDef);
        fixture.setUserData("bird");  // Use the bird's type as user data

        birdBody.setUserData("bird");
        shape.dispose();
    }

    public Body getBirdBody() {
        return birdBody;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    public void resetPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        birdBody.setTransform(newX, newY, 0);
        birdBody.setLinearVelocity(0, 0);
        birdBody.setAngularVelocity(0);
    }
}
