package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

public class LoseScreen {

    private Stage stage;
    private Skin skin;
    private Dialog loseDialog;
    private Levels levels;
    private String levelName;// Shared Levels instance

    public LoseScreen(Levels levels, Stage stage, Skin skin, String levelName) {
        this.levels = levels;
        this.stage = stage;
        this.skin = skin;
        this.levelName = levelName;

        createLoseDialog();
    }

    private void createLoseDialog() {
        loseDialog = new Dialog("You Lost!", skin);
        loseDialog.setModal(true);
        loseDialog.setMovable(false);
        loseDialog.setResizable(false);

        Table table = loseDialog.getContentTable();

        // Load textures for buttons
        Texture returnLevelsTexture = new Texture(Gdx.files.internal("returnlevels.png"));
        Texture retryTexture = new Texture(Gdx.files.internal("restart.png"));

        // Return to Levels button
        TextureRegion returnRegion = new TextureRegion(returnLevelsTexture);
        Drawable returnDrawable = new TextureRegionDrawable(returnRegion);
        ImageButton returnButton = new ImageButton(returnDrawable);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen(levels));
                loseDialog.hide();
            }
        });
        table.add(returnButton).padBottom(20).fillX().height(60).row();

        // Retry button
        TextureRegion retryRegion = new TextureRegion(retryTexture);
        Drawable retryDrawable = new TextureRegionDrawable(retryRegion);
        ImageButton retryButton = new ImageButton(retryDrawable);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (levelName.equals("Level 1")){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(levels));
                    loseDialog.hide();}
                else if (levelName.equals("Level 2")){
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(levels));
                    loseDialog.hide();}
                else{
//                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(levels));
                    loseDialog.hide();
                }
            }
        });
        table.add(retryButton).padBottom(20).fillX().height(60).row();
    }

    public void show() {
        loseDialog.show(stage);
    }

    public void dispose() {
        // Dispose of resources if needed
    }
}
