package com.eightbitforest.wwc.objects.base;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
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

    public GameObjectDynamic(Sprite sprite) {
        super(sprite);
        shapes = new Array<Shape>();
        body = getBody(WorldHandler.getWorld());
    }
    public GameObjectDynamic(String image) {
        super(image);
        shapes = new Array<Shape>();
        body = getBody(WorldHandler.getWorld());
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
