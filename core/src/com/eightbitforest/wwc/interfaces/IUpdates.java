package com.eightbitforest.wwc.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public interface IUpdates {

    void render(SpriteBatch batch);
    void update(float deltaTime);
    void dispose();

}
