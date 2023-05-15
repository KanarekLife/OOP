package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.*;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Plant extends Organism {
    protected Plant(int strength, Color color, Position position) {
        super(strength, 0, color, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        if (ThreadLocalRandom.current().nextInt(100) < 10) {
            var proposedPosition = context.getRandomNearbyPosition(getPosition(), true);
            proposedPosition
                    .flatMap(this::getNewInstance)
                    .ifPresent(organism -> {
                        context.log(String.format("%s seeded to %s", getName(), getPosition()));
                        context.add(organism);
                    });
        }
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {}
}
