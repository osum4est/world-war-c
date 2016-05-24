package com.eightbitforest.wwc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.eightbitforest.wwc.handlers.Globals;

public class HumanTank extends Tank {

    public float maxPower = 60f;
    public float power;
    public float powerSpeed = 1f;
    public boolean firing = false;

    private Sprite powerSprite;
    private float powerWidth = 150 / Globals.PPM;
    private Sprite powerBackSprite;

    public HumanTank(int id, String tankImage, String barrelImage, String name, Vector2 position) {
        super(id, tankImage, barrelImage, name, position);
        powerSprite = new Sprite(new Texture(Gdx.files.internal("PowerBarFore.png")));
        powerSprite.setSize(0,  20 / Globals.PPM);
        powerSprite.setPosition(.25f, .25f);

        powerBackSprite = new Sprite(new Texture(Gdx.files.internal("PowerBar.png")));
        powerBackSprite.setSize((powerWidth * Globals.PPM + 10) / Globals.PPM,  30 / Globals.PPM);
        powerBackSprite.setPosition(.25f - 5 / Globals.PPM, .25f - 5 / Globals.PPM);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.begin();
        if (isTakingTurn()) {
            powerBackSprite.draw(batch);
            powerSprite.draw(batch);
        }
        batch.end();
    }

    @Override
    public void takeTurn() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            currentVelocity = -maxVelocity;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            currentVelocity = maxVelocity;
        else
            currentVelocity = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            barrelAngle--;
        else if (Gdx.input.isKeyPressed(Input.Keys.UP))
            barrelAngle++;
        barrelAngle = barrelAngle >= 150 ? 150 : barrelAngle;
        barrelAngle = barrelAngle <= 30 ? 30 : barrelAngle;


        drive(currentVelocity);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (firing) {
                if (power >= maxPower)
                    return;
                power += powerSpeed;
                powerSprite.setSize(powerWidth * power / maxPower, powerSprite.getHeight());
            }
            else {
                firing = true;
            }
        }
        else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (firing) {
                fire(180-barrelAngle + sprite.getRotation(), power);
                firing = false;
                power = 0;
                powerSprite.setSize(powerWidth * power / maxPower, powerSprite.getHeight());
                endTurn();
            }
        }

//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            fire(180-barrelAngle + sprite.getRotation(), 50);
//            endTurn();
//        }
    }
}
