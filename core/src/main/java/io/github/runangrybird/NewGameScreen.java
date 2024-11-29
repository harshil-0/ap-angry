package io.github.runangrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Map;

public class NewGameScreen extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private Map<String, Boolean> levelList;
    private Texture background;
    private Texture backButtonImage;
    private Texture levelImage1;
    private Texture levelImage2;
    private Texture levelImage3;
    private Texture levelImage2gr;
    private Texture levelImage3gr;
    private Image backButtonImageWidget;
    private Levels levels;  // Shared Levels object

    public NewGameScreen(Levels levels) {
        this.levels = levels;  // Initialize with the shared Levels instance
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        background = new Texture(Gdx.files.internal("levels.jpg"));
        backButtonImage = new Texture(Gdx.files.internal("backbutton.png"));
        levelImage1 = new Texture(Gdx.files.internal("level1.png"));
        levelImage2 = new Texture(Gdx.files.internal("level2.png"));
        levelImage3 = new Texture(Gdx.files.internal("level3.png"));
        levelImage2gr = new Texture(Gdx.files.internal("2lvlgr.PNG"));
        levelImage3gr = new Texture(Gdx.files.internal("level3gr.PNG"));

        levels = new Levels();  // Retrieve level status from Levels
        levelList = levels.getLevelList();

        float buttonWidth = 220f;
        float buttonHeight = 216f;

        int i = 0;
        for (Map.Entry<String, Boolean> entry : levelList.entrySet()) {
            String level = entry.getKey();
            boolean isLocked = entry.getValue();

            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();

            switch (level) {
                case "Level 1":
                    buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelImage1));
                    break;
                case "Level 2":
                    if (!isLocked) {
                        buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelImage2)); // Check lock status here
                    }
                    else{
                        buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelImage2));
                    }
                    break;
                case "Level 3":
                    buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(isLocked ? levelImage3 : levelImage3)); // Check lock status here
                    break;
            }

            ImageButton levelButton = new ImageButton(buttonStyle);
            levelButton.setSize(buttonWidth, buttonHeight);
            levelButton.setPosition(200 + (i * (buttonWidth + 50)), Gdx.graphics.getHeight() / 2);
            i++;

            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Selected level: " + level);

                        switch (level) {
                            case "Level 1":
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(levels));
                                break;
                            case "Level 2":
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(levels));
                                break;
                            case "Level 3":
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(levels));
                                break;
                            default:
                                System.err.println("[Error] No screen defined for this level");
                                break;
                        }

                }
            });

            stage.addActor(levelButton);
        }

        // Back button configuration remains unchanged
        backButtonImageWidget = new Image(backButtonImage);
        backButtonImageWidget.setSize(100, 100);
        backButtonImageWidget.setPosition(100, Gdx.graphics.getHeight() - 200);
        backButtonImageWidget.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                } catch (Exception e) {
                    System.err.println("[Error] Failed to transition to MenuScreen: " + e.getMessage());
                }
            }
        });
        stage.addActor(backButtonImageWidget);
    }
//    public void refreshLevelButtons(Stage stage) {
//        stage.clear(); // Remove all existing actors
//        float buttonWidth = 220f;
//        float buttonHeight = 216f;
//
//        int i = 0;
//        for (Map.Entry<String, Boolean> entry : levels.getLevelList().entrySet()) {
//            String level = entry.getKey();
//            boolean isLocked = entry.getValue();
//
//            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
//
//            // Assign appropriate image
//            switch (level) {
//                case "Level 1":
//                    buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(levelImage1));
//                    break;
//                case "Level 2":
//                    buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(isLocked ? levelImage2gr : levelImage2));
//                    break;
//                case "Level 3":
//                    buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(isLocked ? levelImage3gr : levelImage3));
//                    break;
//            }
//
//            ImageButton levelButton = new ImageButton(buttonStyle);
//            levelButton.setSize(buttonWidth, buttonHeight);
//            levelButton.setPosition(200 + (i * (buttonWidth + 50)), Gdx.graphics.getHeight() / 2);
//            i++;
//
//            levelButton.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    if (isLocked) {
//                        System.out.println("[Info] " + level + " is locked.");
//                        return;
//                    }
//
//                    System.out.println("Selected level: " + level);
//                    switch (level) {
//                        case "Level 1":
//                            ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(levels));
//                            break;
//                        case "Level 2":
//                            ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen());
//                            break;
//                        case "Level 3":
//                            ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen());
//                            break;
//                        default:
//                            System.err.println("[Error] No screen defined for this level");
//                            break;
//                    }
//                }
//            });
//
//            stage.addActor(levelButton); // Add button to stage
//        }
//    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void hide() {
        stage.dispose();
        batch.dispose();
        background.dispose();
        backButtonImage.dispose();
        levelImage1.dispose();
        levelImage2.dispose();
        levelImage3.dispose();
        levelImage2gr.dispose();
        levelImage3gr.dispose();
    }
}
