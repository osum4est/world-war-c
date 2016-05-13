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

public class Terrain extends GameObjectDynamic {

    public static final int OBJECT_QUALITY = 5;
    public Pixmap pixmap;
    public Array<Vector2> bodyVertexList;
    public FixtureDef fixtureDef;

    public Terrain(String image) {
        super(image);
        Pixmap.setBlending(Pixmap.Blending.None);
        pixmap = new Pixmap(Gdx.files.internal(image));
        regenTexture();

        bodyVertexList = new Array<Vector2>();
        regenBody();
    }

    @Override
    public Sprite getSprite(String image) {
        Sprite sprite = new Sprite(new Texture(image));
        sprite.setOrigin(sprite.getWidth() / Globals.PPM, sprite.getHeight() / Globals.PPM);
        sprite.setSize(sprite.getWidth() / Globals.PPM, sprite.getHeight() / Globals.PPM);
        return sprite;
    }

    @Override
    public Body getBody(World world) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(0, 0);
        Body body = world.createBody(bdef);

        fixtureDef = new FixtureDef();
        ChainShape shape = new ChainShape();
        Vector2[] verticies = { Vector2.Zero, new Vector2(1, 0) };
        shape.createChain(verticies);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        return body;
    }

    public void regenTexture() {
        sprite.setTexture(new Texture(pixmap));
    }

    public void regenBody() {
        bodyVertexList.clear();

        for (int i = 0; i < sprite.getWidth() * Globals.PPM - 2; i+=OBJECT_QUALITY) {
            bodyVertexList.add(new Vector2(i / Globals.PPM, getYOfTerrain(i) / Globals.PPM));
        }
        bodyVertexList.add(new Vector2(sprite.getWidth() * Globals.PPM / Globals.PPM, getYOfTerrain((int)(sprite.getWidth() * Globals.PPM - 1)) / Globals.PPM));

        ChainShape chainShape = new ChainShape();
        chainShape.createChain((Vector2[]) bodyVertexList.toArray(Vector2.class));
        fixtureDef.shape = chainShape;

        body.destroyFixture(body.getFixtureList().get(0));
        body.createFixture(fixtureDef);

        chainShape.dispose();
    }

    public int getYOfTerrain(int x) {
        int y;
        for (y = 0; y < (int)sprite.getHeight() * Globals.PPM; y++) {
            if (new Color(pixmap.getPixel(x, y)).a != 0) {
                break;
            }
        }

        return (int)(sprite.getHeight() * Globals.PPM - y);
    }

    public void explodeAtX(int x,  int radius) {
        pixmap.setColor(Color.CLEAR);
        pixmap.fillCircle(x, (int)(sprite.getHeight() * Globals.PPM - getYOfTerrain(x)), radius);

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
