package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.eightbitforest.wwc.objects.Terrain;
import com.eightbitforest.wwc.objects.base.GameObject;
import com.eightbitforest.wwc.objects.base.GameObjectDynamic;

/**
 * Created by Forrest Jones on 5/10/2016.
 */
public class WorldHandler {

    private static World _world;
    public static World getWorld() { return _world; };
    public Array<GameObject> gameObjects;

    public Terrain terrain;

    public WorldHandler() {
        _world = new World(new Vector2(0, -9.81f), false);
        gameObjects = new Array<GameObject>();

        terrain = new Terrain("terrain.png");
        addGameObject(terrain);
    }

    public void render(SpriteBatch batch) {

        for (GameObject object : gameObjects)
            object.render(batch);
    }

    public void update(float deltatime) {
        getWorld().step(1 / 60f, 8, 3);
        for (GameObject object : gameObjects)
            object.update(deltatime);
    }

    public void dispose() {
        for (GameObject object : gameObjects)
            object.dispose();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
    public void addGameObject(GameObjectDynamic gameObject) {
        gameObjects.add(gameObject);
    }
}
