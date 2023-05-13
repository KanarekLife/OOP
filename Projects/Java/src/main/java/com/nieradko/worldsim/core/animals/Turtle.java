package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.*;

import java.util.concurrent.ThreadLocalRandom;

public class Turtle extends Animal {
    public Turtle(Position position) {
        super(2,1, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        if (ThreadLocalRandom.current().nextInt(100) > 75) {
            super.handleAction(context);
        }
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        super.handleCollision(collisionContext, worldContext);

        if (collisionContext.isResolved()) {
            return;
        }

        if (collisionContext.getAttacker().getStrength() < 5) {
            // TODO Add Log
            collisionContext.cancel();
        }else {
            collisionContext.defenderHasDied();
        }
    }
}
