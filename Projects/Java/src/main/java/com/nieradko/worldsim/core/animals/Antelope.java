package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.IWorldContext;
import com.nieradko.worldsim.core.Position;

import java.util.concurrent.ThreadLocalRandom;

public class Antelope extends Animal {
    public Antelope(Position position) {
        super(4, 4, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getRandomNearbyPosition(getPosition(), false, 2)
                .ifPresent(value -> context.move(this, value));
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        super.handleCollision(collisionContext, worldContext);

        if (collisionContext.isResolved()) {
            return;
        }

        if (ThreadLocalRandom.current().nextBoolean()) {
            worldContext.getRandomNearbyPosition(getPosition(), true)
                    .ifPresent(newPosition -> {
                        worldContext.move(this, newPosition);
                        collisionContext.defenderHasEvaded();
                    });
        }
    }
}
