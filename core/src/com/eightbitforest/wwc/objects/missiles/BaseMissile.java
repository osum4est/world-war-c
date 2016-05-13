package com.eightbitforest.wwc.objects.missiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.eightbitforest.wwc.handlers.Globals;
import com.eightbitforest.wwc.objects.base.GameObjectDynamic;

/**
 * Created by Forrest Jones on 5/12/2016.
 */
public abstract class BaseMissile extends GameObjectDynamic {

    public BaseMissile(String image, Vector2 position, float degrees, float power) {
        super(image);
        body.setTransform(position, degrees);
        body.applyLinearImpulse(new Vector2((float)Math.cos(Math.toRadians(degrees)) * power, (float)Math.sin(Math.toRadians(degrees)) * power), body.getWorldCenter(), true);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setRotation(degrees);
    }

    @Override
    public Body getBody(World world) {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.position.set(3, 2);
        bdef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = getPolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        fdef.shape = shape;
        fdef.density = 25f;
        fdef.friction = 1f;
        fdef.restitution = .5f;
        fdef.isSensor = true;

        Body body = world.createBody(bdef);
        body.createFixture(fdef);
        body.setActive(true);
        body.setAwake(true);

        return body;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        body.setTransform(body.getPosition(), (float)Math.toRadians(body.getLinearVelocity().angle()));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    @Override
    public Sprite getSprite(String image) {
        Sprite sprite = new Sprite(new Texture(image));
        sprite.setSize(64 / Globals.PPM, 32 / Globals.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        return sprite;
    }
}
