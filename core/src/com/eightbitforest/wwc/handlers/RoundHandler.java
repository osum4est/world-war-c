package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.utils.Array;
import com.eightbitforest.wwc.objects.Tank;

/**
 * Created by Forrest Jones on 5/12/2016.
 */
public class RoundHandler {
    Array<Tank> tanks;
    private int currentTankTurn = 0;

    private boolean roundInProgress = false;

    public RoundHandler(Array<Tank> tanks) {
        this.tanks = tanks;
    }

    public void startRound() {
        roundInProgress = true;
    }

    public void update() {
        if (roundInProgress) {
            if (currentTank().isTakingTurn()) {
                currentTank().takeTurn();
            }
            else if (currentTankTurn == tanks.size - 1) {
                currentTankTurn = 0;
                currentTank().startTurn();
            }
            else {
                currentTankTurn++;
                currentTank().startTurn();
            }

            if (getWinner() != null)
                roundInProgress = false;
        }
    }

    public void newTurn() {
        for (Tank tank : tanks) {
            tank.startTurn();
            while (tank.isTakingTurn())
                tank.takeTurn();
        }
    }

    public Tank getWinner() {
        Tank winner = null;
        for (Tank tank : tanks) {
            if (tank.isAlive()) {
                if (winner != null) {
                    return null;
                }
                winner = tank;
            }
        }
        return winner;
    }

    public Tank currentTank() {
        return tanks.get(currentTankTurn);
    }
}
