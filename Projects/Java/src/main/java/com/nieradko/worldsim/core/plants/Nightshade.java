package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.IWorldContext;
import com.nieradko.worldsim.core.Position;

public class Nightshade extends Plant {
    public Nightshade(Position position) {
        super(99, position);
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        collisionContext.defenderHasDied();
        collisionContext.attackerHasDied();
    }
}
