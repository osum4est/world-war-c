package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public class CameraHandler extends OrthographicCamera {

    public Box2DDebugRenderer debugRenderer;
    public OrthographicCamera uiCamera;

    public CameraHandler() {
        super(Gdx.graphics.getWidth() / Globals.PPM, Gdx.graphics.getHeight() / Globals.PPM);
        debugRenderer = new Box2DDebugRenderer();
        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void cameraRender(SpriteBatch batch) {
        if (Globals.DEBUG_MODE) {
            debugRenderer.render(WorldHandler.getWorld(), combined);
        }

        batch.begin();
        batch.setProjectionMatrix(combined);
        batch.end();
    }

    public void cameraUpdate() {
        update();
        uiCamera.update();
        position.set(Gdx.graphics.getWidth() / Globals.PPM / 2, Gdx.graphics.getHeight() / Globals.PPM / 2, 0);
    }

}
