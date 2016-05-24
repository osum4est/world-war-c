package com.eightbitforest.wwc.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.eightbitforest.wwc.handlers.Globals;
import com.eightbitforest.wwc.handlers.WorldHandler;
import com.eightbitforest.wwc.objects.base.GameObjectDynamic;
import com.eightbitforest.wwc.objects.missiles.PlainMissile;

public abstract class Tank extends GameObjectDynamic {

    public Sprite barrelSprite;
    public Sprite healthSprite;
    public Sprite fuelSprite;

    public final float maxHealth = 100f;
    public float currentHealth = maxHealth;
    public boolean isAlive() { return currentHealth > 0; }

    public final float maxVelocity = 7.5f;
    public float currentVelocity;
    public final float maxFuel = 250f;
    public float fuel;

    public String name;

    public RevoluteJoint wheelJoint1;
    public RevoluteJoint wheelJoint2;

    public static float width = .6f;

    public float barrelAngle = 90f;

    private boolean takingTurn = false;
    public boolean isTakingTurn() { return takingTurn; }


    public void startTurn() {
        takingTurn = true;
        fuel = maxFuel;
    }
    public abstract void takeTurn();

    public Tank(int id, String tankImage, String barrelImage, String name, Vector2 position) {
        super(id, tankImage, position);

        this.name = name;

        barrelSprite = new Sprite(new Texture(barrelImage));
        barrelSprite.setSize(width, .5f);
        barrelSprite.setOrigin(barrelSprite.getWidth(), barrelSprite.getHeight() / 2) ;

        healthSprite = new Sprite(new Texture("HealthBar.png"));
        healthSprite.setSize(width, .1f);
        healthSprite.setOrigin(0, 0) ;

        fuelSprite = new Sprite(new Texture("FuelBar.png"));
        fuelSprite.setSize(width, .1f);
        fuelSprite.setOrigin(0, 0) ;



    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 4);
        barrelSprite.setPosition(body.getPosition().x - barrelSprite.getWidth(),
                body.getPosition().y - barrelSprite.getHeight() / 2);
        barrelSprite.setRotation(-barrelAngle + sprite.getRotation());
        Vector2 barrelPos = new Vector2(barrelSprite.getX(), barrelSprite.getY());
        barrelPos = barrelPos.add(
                new Vector2(
                        -.25f * (float)Math.cos(Math.toRadians(barrelSprite.getRotation())),
                        -.25f * (float)Math.sin(Math.toRadians(barrelSprite.getRotation()))
                ));
        barrelSprite.setPosition(barrelPos.x, barrelPos.y);

        healthSprite.setPosition(body.getPosition().x - sprite.getWidth() / 4,
                body.getPosition().y - healthSprite.getHeight() * 4);
        fuelSprite.setPosition(body.getPosition().x - sprite.getWidth() / 4,
                body.getPosition().y - fuelSprite.getHeight() * 5 - 1 / Globals.PPM);

        healthSprite.setSize(width * currentHealth / maxHealth, healthSprite.getHeight());
        fuelSprite.setSize( width * fuel / maxFuel, fuelSprite.getHeight());

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        barrelSprite.draw(batch);
        healthSprite.draw(batch);
        fuelSprite.draw(batch);
        sprite.draw(batch);
        batch.end();
    }

    public void endTurn() {
        takingTurn = false;
    }

    @Override
    public Sprite getSprite(String image) {
        Sprite sprite = new Sprite(new Texture(image));
        sprite.setSize(width * 2, .6f);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 4);
        return sprite;
    }

    @Override
    public Body getBody(World world) {
        BodyDef bodyBodyDef = new BodyDef();
        bodyBodyDef.position.set(0, 0);
        bodyBodyDef.angle = 0f;
        bodyBodyDef.type = BodyDef.BodyType.DynamicBody;
        Body bodyBody = world.createBody(bodyBodyDef);

        FixtureDef bodyFixtureDef = new FixtureDef();
        PolygonShape shape = getPolygonShape();
        shape.setAsBox(width / 2, .05f);
        bodyFixtureDef.shape = shape;
        bodyFixtureDef.restitution = .1f;
        bodyFixtureDef.density = 200f;
        bodyFixtureDef.friction = .1f;
        bodyBody.createFixture(bodyFixtureDef);

        FixtureDef wheelDef = new FixtureDef();
        CircleShape wheelShape = getCircleShape();
        wheelShape.setRadius(.15f);
        wheelDef.shape = wheelShape;
        wheelDef.density = 20f;
        wheelDef.restitution = 0f;
        wheelDef.friction = 5000f;

        BodyDef wheelBodyDef = new BodyDef();
        wheelBodyDef.position.set(1, 2);
        wheelBodyDef.type = BodyDef.BodyType.DynamicBody;

        Body wheel1 = WorldHandler.getWorld().createBody(wheelBodyDef);
        Body wheel2 = WorldHandler.getWorld().createBody(wheelBodyDef);
        wheel1.createFixture(wheelDef);
        wheel2.createFixture(wheelDef);



        RevoluteJointDef wheelJointDef = new RevoluteJointDef();
        wheelJointDef.enableMotor = true;
        wheelJointDef.maxMotorTorque = 50;
        wheelJointDef.enableLimit = false;
        wheelJointDef.bodyA = bodyBody;
        wheelJointDef.bodyB = wheel1;
        wheelJointDef.localAnchorA.set(-.3f, 0f);
        wheelJointDef.localAnchorB.set(0, 0);
        wheelJointDef.collideConnected = false;
        wheelJoint1 = (RevoluteJoint)world.createJoint(wheelJointDef);

        RevoluteJointDef wheelJointDef1 = new RevoluteJointDef();
        wheelJointDef1.enableMotor = true;
        wheelJointDef1.enableLimit = false;
        wheelJointDef1.maxMotorTorque = 50;
        wheelJointDef1.bodyA = bodyBody;
        wheelJointDef1.bodyB = wheel2;
        wheelJointDef1.localAnchorA.set(.3f, 0f);
        wheelJointDef1.localAnchorB.set(0, 0);
        wheelJointDef1.collideConnected = false;
        wheelJoint2 = (RevoluteJoint)world.createJoint(wheelJointDef1);

        return bodyBody;
    }

    public void drive(float speed) {
        if (speed != 0) {
            if (fuel > 0) {
                fuel--;
            }
        }

        if (fuel > 0) {
            wheelJoint1.setMotorSpeed(-speed);
            wheelJoint2.setMotorSpeed(-speed);
        }
        else {
            wheelJoint1.setMotorSpeed(0);
            wheelJoint2.setMotorSpeed(0);
        }
    }

    public void takeDamage(float amount) {
        currentHealth-=amount;
    }

    public void fire(float degrees, float power) {
        WorldHandler.getInstance().addGameObject(
                new PlainMissile("missile.gif",
                        new Vector2(barrelSprite.getX() + barrelSprite.getWidth(),
                                barrelSprite.getY() + barrelSprite.getHeight() * 2),
                        degrees,
                        power,
                        this));
    }
}
