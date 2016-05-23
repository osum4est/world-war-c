package com.eightbitforest.wwc.objects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Forrest Jones on 5/12/2016.
 */
public class CPUTank extends Tank {
    public CPUTank(int id, String tankImage, String barrelImage, String name, Vector2 position) {
        super(id, tankImage, barrelImage, name, position);
    }

    @Override
    public void takeTurn() {

    }
}
