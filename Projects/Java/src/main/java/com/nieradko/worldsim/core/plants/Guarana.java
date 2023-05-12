package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.SquarePosition;

public class Guarana extends Plant {
    protected Guarana(SquarePosition position) {
        super(0, position);
    }

    @Override
    protected void handleCollision(ICollisionContext context) {
        context.getAttacker().increaseStrength(3);
        context.defenderHasDied();
    }
}
