package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.Position;
import javafx.scene.paint.Color;

public class Human extends Animal {
    private boolean isSpecialPowerActive = false;
    private int specialPowerTimer = 0;

    public Human(Position position) {
        super(5, 4, Color.LIGHTCYAN, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        specialPowerTick(context);

        context.getGUIContext().setupHumanControls(getPosition().getAllNearbyPositions(1));
    }

    @Override
    public Color getColor() {
        if (isSpecialPowerActive) {
            return Color.DARKORANGE;
        }else {
            return super.getColor();
        }
    }

    public int getSpecialPowerTimer() {
        return specialPowerTimer;
    }

    public void tryToActivateSpecialPower(IActionContext context) {
        if (!isSpecialPowerActive && specialPowerTimer == 0) {
            context.log("Human special power actived!");
            isSpecialPowerActive = true;
            specialPowerTimer = 5;
        }

    }

    private void specialPowerTick(IActionContext context) {
        if (isSpecialPowerActive) {
            context.getNearbyAnimals(getPosition())
                    .forEach(context::kill);
        }

        specialPowerTimer = Math.max(0, specialPowerTimer - 1);

        if (specialPowerTimer == 0 && isSpecialPowerActive) {
            context.log("Human special power disabled");
            isSpecialPowerActive = false;
            specialPowerTimer = 5;
        }
    }
}
