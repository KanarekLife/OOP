package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.SquarePosition;

public class PineBorscht extends Plant {
    protected PineBorscht(SquarePosition position) {
        super(10, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getNearbyAnimals(getPosition())
                .forEach(context::kill);
        super.handleAction(context);
    }

    @Override
    protected void handleCollision(ICollisionContext context) {
        context.defenderHasDied();
        context.attackerHasDied();
    }
}
