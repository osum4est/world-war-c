package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;

/**
 * Created by osum4est on 5/17/16.
 */
public class TextHandler {

    static String text = "";
    static BitmapFont font;

    static Timer timer;
    static float time;

    static TextField textField;
    static boolean hitEnter = false;

    static Stage stage;

    public static void init() {
        font = new BitmapFont(Gdx.files.internal("Droid.fnt"), Gdx.files.internal("Droid.png"), false);
        timer = new Timer();

        VisUI.load();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        textField = new TextField("test", VisUI.getSkin());
        textField.setPosition(0, 0);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {

                if (event.getKeyCode() == Input.Keys.ENTER) {
                    TextHandler.hitEnter = true;
                }

                return true;
            }
        });
    }



    public static void showIndefinate(String text) {
        timer.clear();
        TextHandler.text = text;
    }

    public static void show(String text, float time) {
        TextHandler.text = text;
        timer.clear();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                TextHandler.showIndefinate("");
            }
        }, time);
    }

    public static void draw(SpriteBatch batch) {

        batch.begin();
        batch.setProjectionMatrix(GameHandler.getInstance().cameraHandler.uiCamera.combined);
        font.draw(batch, text, -Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();
        stage.draw();
    }

    public static String getInput(int x, int y) {
        if (stage.getActors().contains(textField, true)) {
            if (hitEnter) {
                textField.remove();
                return textField.getText();
            }
        }
        else {
            textField.setPosition(x, y);
            stage.addActor(textField);
            textField.setText("");
            stage.setKeyboardFocus(textField);
        }

        return "";
    }
}
