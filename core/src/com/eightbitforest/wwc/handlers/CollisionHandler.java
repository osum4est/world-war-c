package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by osum4est on 5/16/16.
 */
public class CollisionHandler implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getBody().getUserData() != null &&
                contact.getFixtureB().getBody().getUserData() != null) {
            ((BodyData) contact.getFixtureA().getBody().getUserData()).gameObject.onCollideEnter(
                    ((BodyData) contact.getFixtureB().getBody().getUserData()).gameObject, contact);
            ((BodyData) contact.getFixtureB().getBody().getUserData()).gameObject.onCollideEnter(
                    ((BodyData) contact.getFixtureA().getBody().getUserData()).gameObject, contact);

        }

//        Gdx.app.log("WWC", "Contact points: " + contact.getWorldManifold().getNumberOfContactPoints());
    }

    @Override
    public void endContact(Contact contact) {
//        ((BodyData) contact.getFixtureA().getBody().getUserData()).gameObject.onCollideExit(
//                ((BodyData) contact.getFixtureB().getBody().getUserData()).gameObject, contact);
//        ((BodyData) contact.getFixtureB().getBody().getUserData()).gameObject.onCollideExit(
//                ((BodyData) contact.getFixtureA().getBody().getUserData()).gameObject, contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}