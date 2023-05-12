package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.SquarePosition;

public class Nightshade extends Plant {
    protected Nightshade(SquarePosition position) {
        super(99, position);
    }

    @Override
    protected void handleCollision(ICollisionContext context) {
        context.defenderHasDied();
        context.attackerHasDied();
    }
}
