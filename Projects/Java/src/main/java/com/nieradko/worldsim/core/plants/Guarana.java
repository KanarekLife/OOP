package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.IWorldContext;
import com.nieradko.worldsim.core.Position;

public class Guarana extends Plant {
    public Guarana(Position position) {
        super(0, position);
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        collisionContext.getAttacker().increaseStrength(3);
        collisionContext.defenderHasDied();
    }
}
