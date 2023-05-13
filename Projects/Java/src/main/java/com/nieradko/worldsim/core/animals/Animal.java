package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.*;

public abstract class Animal extends Organism implements IMovable {

    protected Animal(int strength, int initiative, Position position) {
        super(strength, initiative, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getRandomNearbyPosition(getPosition())
                .ifPresent(value -> context.move(this, value));
    }

    @Override
    protected void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext) {
        if (collisionContext.getAttacker().getClass() == this.getClass()) {
            var proposedPosition = worldContext.getRandomNearbyPosition(getPosition(), true);
            proposedPosition
                    .flatMap(this::getNewInstance)
                    .ifPresent(worldContext::add);
            collisionContext.cancel();
        }
    }

    @Override
    public void moveTo(Position position) {
        setPosition(position);
    }

    public void increaseStrength(int delta) {
        this.strength += delta;
    }
}
