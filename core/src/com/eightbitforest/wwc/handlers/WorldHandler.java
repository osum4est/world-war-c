package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.eightbitforest.wwc.objects.CPUTank;
import com.eightbitforest.wwc.objects.HumanTank;
import com.eightbitforest.wwc.objects.Tank;
import com.eightbitforest.wwc.objects.Terrain;
import com.eightbitforest.wwc.objects.base.GameObject;
import com.eightbitforest.wwc.objects.base.GameObjectDynamic;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public class WorldHandler {

    private static World _world;
    public static World getWorld() { return _world; }
    private static WorldHandler _instance;
    public static WorldHandler getInstance() { return _instance; }
    public Array<GameObject> gameObjects;
    public Array<Body> removeThese;

    public CollisionHandler collisionHandler;

    public Terrain terrain;

    private Array<Tank> tanks;
    public Array<Tank> getTanks() { return tanks; }

    private Sprite background;
    private Sprite clouds;
    private float cloudSpeed = .25f;

    public WorldHandler() {
        _world = new World(new Vector2(0, -9.81f), false);
        _instance = this;
        gameObjects = new Array<GameObject>();
        collisionHandler = new CollisionHandler();
        getWorld().setContactListener(collisionHandler);

        removeThese = new Array<Body>();

        terrain = new Terrain(ID.TERRAIN, "terrain.png");
        addGameObject(terrain);

        tanks = new Array<Tank>();
        tanks.add(new HumanTank(ID.TANK0, "tank.png", "barrel.png", "Player 1", new Vector2(1, 3.5f)));
        tanks.add(new HumanTank(ID.TANK1, "tankBody.png", "tankGun.png", "Player 2", new Vector2(12, 3.65f)));

        background = new Sprite(new Texture(Gdx.files.internal("backdrop.png")));
        background.setSize(Gdx.graphics.getWidth() / Globals.PPM, Gdx.graphics.getHeight() / Globals.PPM);

        clouds = new Sprite(new Texture(Gdx.files.internal("clouds.png")));
        clouds.setSize(Gdx.graphics.getWidth() / Globals.PPM * 2, Gdx.graphics.getHeight() / Globals.PPM / 2);
        clouds.setPosition(0, Gdx.graphics.getHeight() / Globals.PPM / 2);


        for (Tank tank : tanks) {
            addGameObject(tank);
        }

        TextHandler.init();
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        background.draw(batch);
        clouds.draw(batch);
        batch.end();

        for (GameObject object : gameObjects)
            object.render(batch);

        TextHandler.draw(batch);
    }

    public void update(float deltatime) {
        getWorld().step(1 / 60f, 8, 3);

        clouds.setPosition(clouds.getX() - cloudSpeed * deltatime, clouds.getY());
        if (clouds.getX() <= -Gdx.graphics.getWidth() / Globals.PPM)
            clouds.setX(0f);

        for (GameObject object : gameObjects)
            object.update(deltatime);

        for (int i = 0; i < removeThese.size; i++) {
            getWorld().destroyBody(removeThese.get(i));
            removeThese.removeIndex(i);
            break;
        }
    }

    public void dispose() {
        for (GameObject object : gameObjects)
            object.dispose();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
    public void removeGameObject(GameObject gameObject, Body body) {
        gameObjects.removeValue(gameObject, true);
        removeThese.add(body);
    }
}
