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
    public int id;

    public GameObject(int id, Sprite sprite) {
        this.id = id;
        this.sprite = sprite;
    }
    public GameObject(int id, String image) {
        this.id = id;
        this.sprite = getSprite(image);
    }

    public Sprite getSprite(String image) {
        return new Sprite(new Texture(image));
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
//        if (renderInMiddle)
//            batch.draw(sprite,
//                    sprite.getX() - sprite.getWidth() / 2 / Globals.PPM, sprite.getY() - sprite.getHeight() / 2 / Globals.PPM,
//                    sprite.getWidth() / 2 / Globals.PPM, sprite.getHeight() / 4 / Globals.PPM,
//                    sprite.getWidth() / Globals.PPM, sprite.getHeight() / Globals.PPM,
//                    1, 1,
//                    sprite.getRotation());
//        else
//            batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth() / Globals.PPM, sprite.getHeight() / Globals.PPM, , 1, 1, sprite.getRotation());

        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
