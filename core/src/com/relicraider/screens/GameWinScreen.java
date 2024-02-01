/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Creates a game win story screen
 */
package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.characters.Player;
import com.relicraider.screens.gamescreens.*;
import com.relicraider.screens.utilities.Button;

public class GameWinScreen implements Screen {
    private final RelicRaider game;
    public Stage stage;
    private Texture backdrop1;
    private Texture backdrop2;
    private Texture backdrop3;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Button continueButton;

    private int backdrop;

    /**
     * Primary Constructor for Game win Screen
     * @param game - The Game Object
     */
    public GameWinScreen(final RelicRaider game) {
        this.game = game;

        resetGame();

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage = new Stage(viewport, game.spriteBatch);

        backdrop1 = new Texture(Gdx.files.internal("GameWin/endStory1.png"));
        backdrop1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backdrop2 = new Texture(Gdx.files.internal("GameWin/endStory2.png"));
        backdrop2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backdrop3 = new Texture(Gdx.files.internal("GameWin/endStory3.png"));
        backdrop3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        continueButton = new Button("CONTINUE", SetupVariables.WIDTH / 2 - Button.width / 2, 50, stage, 24);

        //click listener to find when the user wants to switch to the next page
        continueButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backdrop++;
                RelicRaider.soundPlayer.getPageFlip().play();

                if (backdrop == 3) {
                    continueButton.setText("BACK TO MENU");
                } if (backdrop == 4) {
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                }
            }
        });

        backdrop = 1;

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    /**
     * method for updating the game win screen
     */
    public void update() {
        if (backdrop == 1) {
            RelicRaider.spriteBatch.draw(backdrop1, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
        } else if (backdrop == 2) {
            RelicRaider.spriteBatch.draw(backdrop2, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
        } else {
            RelicRaider.spriteBatch.draw(backdrop3, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
        }
    }

    /**
     * render method to draw the screen
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //screen render functions
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        RelicRaider.spriteBatch.setProjectionMatrix(camera.combined);
        RelicRaider.spriteBatch.begin();

        update();

        RelicRaider.spriteBatch.end();
        stage.act();
        stage.draw();
    }

    /**
     * method used for resetting the game in order to let the game be run again
     */
    public void resetGame() {
        RelicRaider.soundPlayer.stopMusic();
        Player.playerHealth = 100;
        Player.resetRelicsCollected();

        Room1.relicIsFound = false;
        Room1.potionIsUsed = false;

        Room2.relicIsFound = false;
        Room2.potionIsUsed = false;

        Room3.relicIsFound = false;
        Room3.potionIsUsed = false;

        Room4.relicIsFound = false;
        Room4.potionIsUsed = false;

        Room5.relicIsFound = false;
        Room5.potionIsUsed = false;

        Room6.relicIsFound = false;
        Room6.potionIsUsed = false;
    }

    /**
     * Method to resize the screen of the win screen
     * @param width - New Width of Screen
     * @param height - New Height of Screen
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Method to dispose assets used
     */
    @Override
    public void dispose() {
        stage.dispose();
        backdrop1.dispose();
        backdrop2.dispose();
        backdrop3.dispose();
    }
}

