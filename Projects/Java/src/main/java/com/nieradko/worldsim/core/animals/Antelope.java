package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.SquarePosition;

import java.util.concurrent.ThreadLocalRandom;

public class Antelope extends Animal {
    protected Antelope(SquarePosition position) {
        super(4, 4, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getRandomNearbyPosition(getPosition(), false, 2)
                .ifPresent(value -> context.move(this, value));
    }

    @Override
    protected void handleCollision(ICollisionContext context) {
        super.handleCollision(context);

        if (context.isResolved()) {
            return;
        }

        if (ThreadLocalRandom.current().nextBoolean()) {
            context.getRandomNearbyPosition(getPosition(), true)
                    .ifPresent(newPosition -> {
                        context.move(this, newPosition);
                        context.defenderHasEvaded();
                    });
        }
    }
}
