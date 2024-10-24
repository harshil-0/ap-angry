package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private ImageButton newGameButton, continueGameButton, exitButton;
    private Levels level;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        // Load background image
        backgroundTexture = new Texture(Gdx.files.internal("menupage.jpeg"));

        // Create buttons with the images
        newGameButton = createButton("newgamebutton.png");
        continueGameButton = createButton("continuebutton.png");
        exitButton = createButton("exitbutton.png");

        // New Game button click listener
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    // Transition to the NewGameScreen
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen(level));
                } catch (Exception e) {
                    // Handle exceptions and print error message
                    Gdx.app.log("Error", "An error occurred while transitioning to NewGameScreen: " + e.getMessage());
                    e.printStackTrace();  // Optional: Print the stack trace for debugging
                }
            }
        });


        // Exit button click listener
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Exit the game
            }
        });

        // Layout buttons using Table
        Table table = new Table();
//        table.center();
            table.bottom();
        table.setFillParent(true);

        table.add(newGameButton).padBottom(10).fillX().height(160).row();
        table.add(continueGameButton).padBottom(10).fillX().height(100).row();
        table.add(exitButton).padBottom(10).fillX().height(120).row();

        stage.addActor(table);
    }

    private ImageButton createButton(String imagePath) {
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal(imagePath)));
        return new ImageButton(buttonDrawable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void hide() {
        stage.dispose();
        backgroundTexture.dispose();
        batch.dispose();
    }
}
