package com.eightbitforest.wwc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.eightbitforest.wwc.handlers.Globals;
import com.eightbitforest.wwc.handlers.WorldHandler;
import com.eightbitforest.wwc.objects.base.GameObjectDynamic;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public class Terrain extends GameObjectDynamic {

    public static final int OBJECT_QUALITY = 5;
    public Pixmap pixmap;
    public Array<Vector2> bodyVertexList;
    public FixtureDef fixtureDef;

    public Terrain(String image) {

        super(new Sprite(new Texture(Gdx.files.internal("terrain.png"))));
        Pixmap.setBlending(Pixmap.Blending.None);
        pixmap = new Pixmap(Gdx.files.internal(image));
        regenTexture();

        bodyVertexList = new Array<Vector2>();
        regenBody();
    }

    @Override
    public Body getBody(BodyDef bdef, FixtureDef fdef) {
        bdef.position.set(0, 0);

        ChainShape shape = new ChainShape();
        Vector2[] verticies = { Vector2.Zero, new Vector2(1, 0) };
        shape.createChain(verticies);

        fdef.shape = shape;

        Body body = WorldHandler.getWorld().createBody(bdef);
        body.createFixture(fdef);

        shape.dispose();

        fixtureDef = fdef;
        return body;
    }

    public void regenTexture() {
        sprite.setTexture(new Texture(pixmap));
    }

    public void regenBody() {
        bodyVertexList.clear();

        for (int i = 0; i < sprite.getWidth() - 2; i+=OBJECT_QUALITY) {
            bodyVertexList.add(new Vector2(i / Globals.PPM, getYOfTerrain(i) / Globals.PPM));
        }
        bodyVertexList.add(new Vector2(sprite.getWidth() / Globals.PPM, getYOfTerrain((int)sprite.getWidth() - 1) / Globals.PPM));

        ChainShape chainShape = new ChainShape();
        chainShape.createChain((Vector2[]) bodyVertexList.toArray(Vector2.class));
        fixtureDef.shape = chainShape;

        body.destroyFixture(body.getFixtureList().get(0));
        body.createFixture(fixtureDef);

        chainShape.dispose();
    }

    public int getYOfTerrain(int x) {
        int y;
        for (y = 0; y < (int)sprite.getHeight(); y++) {
            if (new Color(pixmap.getPixel(x, y)).a != 0) {
                break;
            }
        }

        return (int)sprite.getHeight() - y;
    }

    public void explodeAtX(int x,  int radius) {
        pixmap.setColor(Color.CLEAR);
        pixmap.fillCircle(x, (int)sprite.getHeight() - getYOfTerrain(x), radius);

        regenTexture();
        regenBody();
    }

    boolean justPressed = false;
    @Override
    public void update(float deltaTime) {
        if (Gdx.input.isButtonPressed(0)) {
            if (!justPressed) {
                explodeAtX(Gdx.input.getX(), 25);
                justPressed = true;
            }
        }
        else
            justPressed = false;
    }

    @Override
    public void dispose() {
        pixmap.dispose();
    }
}
