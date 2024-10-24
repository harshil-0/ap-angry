package io.github.runangrybird;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;  // Import the Game class

public class Start extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture image;
    private ShapeRenderer shapeRenderer;
    private float timedone;

    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture(Gdx.files.internal("angry.birds.jpg"));  // Ensure this image is in your assets folder
        shapeRenderer = new ShapeRenderer();
        timedone = 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        timedone += delta;

        batch.begin();
        batch.draw(image, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        float progress = Math.min(timedone / 2.0f, 1.0f);
        drawProgressBar(progress);

        if (timedone >= 2.0f) {
            // Transition to the MenuScreen after 2 seconds
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
        }
    }

    private void drawProgressBar(float progress) {
        float barWidth = Gdx.graphics.getWidth() * 0.5f;
        float barHeight = 30;
        float barX = (Gdx.graphics.getWidth() - barWidth) / 2;
        float barY = Gdx.graphics.getHeight() * 0.05f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(barX, barY, barWidth * progress, barHeight);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shapeRenderer.dispose();
    }
}
