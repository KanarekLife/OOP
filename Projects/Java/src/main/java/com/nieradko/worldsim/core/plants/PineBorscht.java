package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.*;
import javafx.scene.paint.Color;

public class PineBorscht extends Plant {
    public PineBorscht(Position position) {
        super(10, Color.GRAY, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getNearbyAnimals(getPosition())
                .forEach(context::kill);
        super.handleAction(context);
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        collisionContext.defenderHasDied();
        collisionContext.attackerHasDied();
    }
}
