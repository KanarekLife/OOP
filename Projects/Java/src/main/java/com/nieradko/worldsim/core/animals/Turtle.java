package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.SquarePosition;

import java.util.concurrent.ThreadLocalRandom;

public class Turtle extends Animal {
    protected Turtle(SquarePosition position) {
        super(2,1, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        if (ThreadLocalRandom.current().nextInt(100) > 75) {
            super.handleAction(context);
        }
    }

    @Override
    protected void handleCollision(ICollisionContext context) {
        super.handleCollision(context);

        if (context.isResolved()) {
            return;
        }

        if (context.getAttacker().getStrength() < 5) {
            // TODO Add Log
            context.cancel();
        }else {
            context.defenderHasDied();
        }
    }
}
