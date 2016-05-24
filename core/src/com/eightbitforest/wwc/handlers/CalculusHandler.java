package com.eightbitforest.wwc.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.eightbitforest.wwc.objects.Tank;

import java.util.Random;

public class CalculusHandler {
    public enum Stage {
        init,
        waitingforanswer,
        idle
    }
    private Stage stage;

    public CalcStatus status;


    private String equation;
    private Random random;
    private int a;
    private int b;
    private int c;

    public CalculusHandler() {
        status = CalcStatus.idle;
        stage = Stage.idle;
        random = new Random();
    }

    public void doCalc(Tank tank) {
        switch (stage) {
            case idle:
                stage = Stage.init;
            case init:
                genEquation();
                TextHandler.showIndefinate(
                        tank.name + " hit!\n" +
                        "Please enter the derivative of:\n" +
                        equation);
                stage = Stage.waitingforanswer;
            case waitingforanswer:
                String input = TextHandler.getInput(15, Gdx.graphics.getHeight() - 150);
                if (!input.equals("")) {
                    if (input.replaceAll("\\s+","").equals(getAnswer())) {
                        status = CalcStatus.succses;
                        TextHandler.show("Correct!", 1);
                    }
                    else {
                        status = CalcStatus.failure;
                        TextHandler.show("Incorrect", 1);
                    }

                    stage = Stage.idle;
                }
                break;
        }
    }

    public void genEquation() {
        a = random.nextInt(9) + 1;
        b = random.nextInt(9) + 1;
        c = random.nextInt(9) + 1;

        equation = String.format("%1$dx^2 + %2$dx + %3$d", a, b, c);
    }

    public String getAnswer() {
        return String.format("%1$dx+%2$d", a*2, b);
    }

}




