package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.ICollisionContext;
import com.nieradko.worldsim.core.IWorldContext;
import com.nieradko.worldsim.core.Position;
import javafx.scene.paint.Color;

public class Nightshade extends Plant {
    public Nightshade(Position position) {
        super(99, Color.PURPLE, position);
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        collisionContext.defenderHasDied();
        collisionContext.attackerHasDied();
    }
}
