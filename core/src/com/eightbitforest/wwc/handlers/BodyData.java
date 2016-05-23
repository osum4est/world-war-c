package com.eightbitforest.wwc.handlers;

import com.eightbitforest.wwc.objects.base.GameObjectDynamic;

/**
 * Created by osum4est on 5/16/16.
 */
public class BodyData {

    public GameObjectDynamic gameObject;
    public int id;

    public BodyData(GameObjectDynamic gameObject, int id) {
        this.gameObject = gameObject;
        this.id = id;
    }
}
