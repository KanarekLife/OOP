package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.Position;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Human extends Animal {
    private boolean isSpecialPowerActive = false;
    private int specialPowerTimer = 0;
    private final Object lock = new Object();

    public Human(Position position) {
        super(5, 4, Color.LIGHTCYAN, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        specialPowerTick();

        context.getGUIContext().setupControls(getPosition().getAllNearbyPositions(1));
    }

    public boolean isSpecialPowerActive() {
        return isSpecialPowerActive;
    }

    public boolean tryToActivateSpecialPower() {
        if (!isSpecialPowerActive && specialPowerTimer == 0) {
            isSpecialPowerActive = true;
            specialPowerTimer = 5;
            return true;
        }

        return false;
    }

    private void specialPowerTick() {
        specialPowerTimer = Math.max(0, specialPowerTimer - 1);

        if (specialPowerTimer == 0 && isSpecialPowerActive) {
            isSpecialPowerActive = false;
            specialPowerTimer = 5;
        }
    }
}
