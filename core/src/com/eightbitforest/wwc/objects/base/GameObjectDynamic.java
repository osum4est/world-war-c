package com.eightbitforest.wwc.objects.base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.eightbitforest.wwc.handlers.BodyData;
import com.eightbitforest.wwc.handlers.WorldHandler;

public abstract class GameObjectDynamic extends GameObject {
    public Body body;
    private Array<Shape> shapes;

    public ChainShape getChainShape() {
        shapes.add(new ChainShape());
        return (ChainShape) shapes.get(shapes.size - 1);
    }
    public CircleShape getCircleShape() {
        shapes.add(new CircleShape());
        return (CircleShape) shapes.get(shapes.size - 1);
    }
    public PolygonShape getPolygonShape() {
        shapes.add(new PolygonShape());
        return (PolygonShape) shapes.get(shapes.size - 1);
    }

    public abstract Body getBody(World world);
    public void onCollideEnter(GameObjectDynamic other, Contact contact) {}
    public void onCollideExit(GameObjectDynamic other, Contact contact) {}

    public GameObjectDynamic(int id, Sprite sprite) {
        super(id, sprite);
        shapes = new Array<Shape>();
        body = getBody(WorldHandler.getWorld());
        body.setUserData(new BodyData(this, id));
    }
    public GameObjectDynamic(int id, String image) {
        super(id, image);
        shapes = new Array<Shape>();
        body = getBody(WorldHandler.getWorld());
        body.setUserData(new BodyData(this, id));
    }

    public GameObjectDynamic(int id, String image, Vector2 position) {
        super(id, image);
        shapes = new Array<Shape>();
        body = getBody(WorldHandler.getWorld());
        body.setTransform(position, body.getAngle());
        body.setUserData(new BodyData(this, id));
    }

    @Override
    public void update(float deltaTime) {
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setRotation((float)Math.toDegrees(body.getTransform().getRotation()));
    }

    @Override
    public void dispose() {
        super.dispose();

        for (Shape shape : shapes) {
            shape.dispose();
        }
    }
}
