package com.eightbitforest.wwc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Forrest Jones on 5/12/2016.
 */
public class HumanTank extends Tank {
    public HumanTank(String image) {
        super(image);
    }

    @Override
    public void takeTurn() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            currentVelocity = -maxVelocity;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            currentVelocity = maxVelocity;
        else
            currentVelocity = 0;

        drive(currentVelocity);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire(45, 35);
        }

        if (fuel <= 0)
            endTurn();
    }
}
