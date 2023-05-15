package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.*;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

public class Turtle extends Animal {
    public Turtle(Position position) {
        super(2,1, Color.DARKGREEN, position);
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
            worldContext.log(String.format("%s cancelled the attack at %s!", getName(), getPosition()));
            collisionContext.cancel();
        }else {
            collisionContext.defenderHasDied();
        }
    }
}
