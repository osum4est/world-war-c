package com.eightbitforest.wwc.objects.base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public abstract class GameObjectDynamic extends GameObject {
    public Body body;

    public abstract Body getBody(BodyDef bdef, FixtureDef fdef);

    public GameObjectDynamic(Sprite sprite) {
        super(sprite);
        body = getBody(new BodyDef(), new FixtureDef());
    }
}
