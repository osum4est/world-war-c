package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;

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

    public static void clear() {
        showIndefinate("");
    }

    public static void draw(SpriteBatch batch) {

        batch.begin();
        batch.setProjectionMatrix(GameHandler.getInstance().cameraHandler.uiCamera.combined);
        font.draw(batch, text, -Gdx.graphics.getWidth() / 2 + 15, Gdx.graphics.getHeight() / 2 - 15);
        batch.end();
        stage.draw();
    }


    private static boolean inputOnScreen = false;
    public static String getInput(int x, int y) {
        if (inputOnScreen) {
            if (hitEnter) {
                hitEnter =false;
                textField.remove();
                inputOnScreen = false;
                return textField.getText();
            }
        }
        else {
            textField.setPosition(x, y);
            stage.addActor(textField);
            textField.setText("");
            stage.setKeyboardFocus(textField);
            inputOnScreen = true;
        }

        return "";
    }
}
