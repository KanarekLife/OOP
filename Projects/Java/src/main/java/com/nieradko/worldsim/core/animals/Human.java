package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.Position;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicInteger;

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
    public com.nieradko.worldsim.core.Color getColor() {
        if (isSpecialPowerActive) {
            return new com.nieradko.worldsim.core.Color(javafx.scene.paint.Color.DARKORANGE);
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
            AtomicInteger atomicKills = new AtomicInteger();
            context.getNearbyOrganisms(getPosition())
                    .forEach(animal -> {
                        context.kill(animal);
                        atomicKills.getAndIncrement();
                    });
            var kills = atomicKills.get();
            if (kills > 0) {
                context.log(String.format("Human has killed %d organisms with his special power", kills));
            }
        }

        specialPowerTimer = Math.max(0, specialPowerTimer - 1);

        if (specialPowerTimer == 0 && isSpecialPowerActive) {
            context.log("Human special power disabled");
            isSpecialPowerActive = false;
            specialPowerTimer = 5;
        }
    }
}
