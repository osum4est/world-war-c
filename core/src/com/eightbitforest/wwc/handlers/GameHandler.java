package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eightbitforest.wwc.objects.base.GameObject;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public class GameHandler {

    private static GameHandler _gameHandler;
    public static GameHandler getInstance() { return _gameHandler; }

    public SpriteBatch batch;
    public WorldHandler worldHandler;

    public CameraHandler cameraHandler;

    public GameHandler() {
        _gameHandler = this;

        batch = new SpriteBatch();
        worldHandler = new WorldHandler();
        cameraHandler = new CameraHandler();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraHandler.cameraRender(batch);
        worldHandler.render(batch);

    }

    public void update(float deltatime) {
        cameraHandler.cameraUpdate();
        worldHandler.update(deltatime);


    }

    public void dispose() {
        worldHandler.dispose();
    }
}
