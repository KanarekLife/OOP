package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.Organism;
import com.nieradko.worldsim.core.SquarePosition;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Plant extends Organism {
    protected Plant(int strength, SquarePosition position) {
        super(strength, 0, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        if (ThreadLocalRandom.current().nextInt(100) < 10) {
            var proposedPosition = context.getRandomNearbyPosition(getPosition(), true);
            proposedPosition
                    .flatMap(this::getNewInstance)
                    .ifPresent(context::add);
        }
    }

    @Override
    protected void handleCollision(ICollisionContext context) {}
}
