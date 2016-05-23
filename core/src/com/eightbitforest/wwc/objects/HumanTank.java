package com.eightbitforest.wwc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class HumanTank extends Tank {

    public HumanTank(int id, String tankImage, String barrelImage, String name, Vector2 position) {
        super(id, tankImage, barrelImage, name, position);
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
        barrelAngle = barrelAngle >= 180 ? 180 : barrelAngle;
        barrelAngle = barrelAngle <= 0 ? 0 : barrelAngle;


        drive(currentVelocity);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire(180-barrelAngle + sprite.getRotation(), 25);
            endTurn();
        }
    }
}
