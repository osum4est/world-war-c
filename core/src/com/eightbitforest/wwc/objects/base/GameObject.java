package com.eightbitforest.wwc.objects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eightbitforest.wwc.handlers.Globals;
import com.eightbitforest.wwc.interfaces.IUpdates;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public abstract class GameObject implements IUpdates {
    public Sprite sprite;

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
//        this.sprite.setScale(Globals.PPM);
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
//        batch.draw(sprite, sprite.getX() - sprite.getWidth() / 2 / Globals.PPM, sprite.getY() - sprite.getHeight() / 2 / Globals.PPM, sprite.getWidth() / Globals.PPM, sprite.getHeight() / Globals.PPM);
        batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth() / Globals.PPM, sprite.getHeight() / Globals.PPM);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {

    }
}
