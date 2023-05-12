package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.*;

public abstract class Animal extends Organism implements IMovable {

    protected Animal(int strength, int initiative, SquarePosition position) {
        super(strength, initiative, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getRandomNearbyPosition(getPosition())
                .ifPresent(value -> context.move(this, value));
    }

    @Override
    protected void handleCollision(ICollisionContext context) {
        if (context.getAttacker().getClass() == this.getClass()) {
            var proposedPosition = context.getRandomNearbyPosition(getPosition(), true);
            proposedPosition
                    .flatMap(this::getNewInstance)
                    .ifPresent(context::add);
            context.cancel();
        }
    }

    @Override
    public void moveTo(SquarePosition position) {
        this.position = position;
    }

    public void increaseStrength(int delta) {
        this.strength += delta;
    }
}
