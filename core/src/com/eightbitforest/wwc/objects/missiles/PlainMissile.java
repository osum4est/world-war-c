package com.eightbitforest.wwc.objects.missiles;

import com.badlogic.gdx.math.Vector2;
import com.eightbitforest.wwc.handlers.ID;
import com.eightbitforest.wwc.objects.Tank;

/**
 * Created by Forrest Jones on 5/12/2016.
 */
public class PlainMissile extends BaseMissile {
    public PlainMissile(String image, Vector2 position, float degrees, float power, Tank sender) {
        super(ID.MISSILE, image, position, degrees, power, 25, sender);
    }
}
