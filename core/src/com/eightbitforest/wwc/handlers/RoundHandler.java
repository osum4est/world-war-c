package com.eightbitforest.wwc.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.eightbitforest.wwc.objects.Tank;

/**
 * Created by Forrest Jones on 5/12/2016.
 */
public class RoundHandler {
    Array<Tank> tanks;
    private int currentTankTurn = 0;
    private boolean roundInProgress = false;

    private boolean doingCalc = false;
    private Tank calcEnemyTank;
    private float calcDamage;

    private CalculusHandler calculusHandler;

    public RoundHandler(Array<Tank> tanks) {
        this.tanks = tanks;
        calculusHandler = new CalculusHandler();
    }

    public void startRound() {
        roundInProgress = true;
    }

    public void update() {
        if (roundInProgress) {
            if (doingCalc) {
                calculusHandler.doCalc(calcEnemyTank);
                switch (calculusHandler.status) {
                    case succses:
                        calcEnemyTank.takeDamage(calcDamage);
                        doingCalc = false;
                        Gdx.app.log("WWC", "Correct");
                        calculusHandler.status = CalcStatus.idle;

                        break;
                    case failure:
                        doingCalc = false;
                        Gdx.app.log("WWC", "Incorrect");
                        calculusHandler.status = CalcStatus.idle;
                        break;
                }
            }
            else if (currentTank().isTakingTurn()) {
                currentTank().takeTurn();
            }
            else if (currentTankTurn == tanks.size - 1) {
                currentTankTurn = 0;
                currentTank().startTurn();
                TextHandler.show(currentTank().name + "'s turn", 2f);
            }
            else {
                currentTankTurn++;
                currentTank().startTurn();
                TextHandler.show(currentTank().name + "'s turn", 2f);
            }

            if (getWinner() != null) {
                roundInProgress = false;
                TextHandler.showIndefinate(getWinner().name + " won!");
            }
        }
    }

    public void newTurn() {
        for (Tank tank : tanks) {
            tank.startTurn();
            while (tank.isTakingTurn())
                tank.takeTurn();
        }
    }

    public void doCalc(Tank enemyTank, float damage) {
        this.calcEnemyTank = enemyTank;
        this.calcDamage = damage;
        this.doingCalc = true;
        calculusHandler.status = CalcStatus.idle;
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
