package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

public class WinScreen {

    private Stage stage;
    private Skin skin;
    private Dialog winDialog;
    private Levels levels;
    private String levelName;

    public WinScreen(Levels levels, Stage stage, Skin skin, String levelName) {
        this.levels = levels;
        this.stage = stage;
        this.skin = skin;
        this.levelName = levelName;

        createWinDialog();
    }

    private void createWinDialog() {
        winDialog = new Dialog("Congratulations!", skin);
        winDialog.setModal(true);
        winDialog.setMovable(false);
        winDialog.setResizable(false);

        Table table = winDialog.getContentTable();

        // Load textures for buttons
        Texture nextLevelTexture = new Texture(Gdx.files.internal("nextlevel.png"));
        Texture returnLevelsTexture = new Texture(Gdx.files.internal("returnlevels.png"));
        Texture restartTexture = new Texture(Gdx.files.internal("restart.png"));

        // Next Level button
        TextureRegion nextRegion = new TextureRegion(nextLevelTexture);
        Drawable nextDrawable = new TextureRegionDrawable(nextRegion);
        ImageButton nextButton = new ImageButton(nextDrawable);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levels.unlockLevel("Level 2");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen(levels));
                winDialog.hide();
            }
        });
        table.add(nextButton).padBottom(20).fillX().height(60).row();

        // Return to Levels button
        TextureRegion returnRegion = new TextureRegion(returnLevelsTexture);
        Drawable returnDrawable = new TextureRegionDrawable(returnRegion);
        ImageButton returnButton = new ImageButton(returnDrawable);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new NewGameScreen(levels));
                winDialog.hide();
            }
        });
        table.add(returnButton).padBottom(20).fillX().height(60).row();

        // Restart button
        TextureRegion restartRegion = new TextureRegion(restartTexture);
        Drawable restartDrawable = new TextureRegionDrawable(restartRegion);
        ImageButton restartButton = new ImageButton(restartDrawable);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (levelName.equals("Level 1")){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(levels));
                winDialog.hide();}
                else if (levelName.equals("Level 2")){
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(levels));
                winDialog.hide();}
                else{
//                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(levels));
                    winDialog.hide();
                }
            }
        });
        table.add(restartButton).padBottom(20).fillX().height(60).row();
    }

    public void show() {
        winDialog.show(stage);
    }

    public void dispose() {


    }
}
