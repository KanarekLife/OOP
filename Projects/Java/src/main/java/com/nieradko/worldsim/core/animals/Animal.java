package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.*;
import javafx.scene.paint.Color;

public abstract class Animal extends Organism implements IMovable {

    protected Animal(int strength, int initiative, Color color, Position position) {
        super(strength, initiative, color, position);
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
                    .ifPresent(organism -> {
                        worldContext.log(String.format("%s has been born at %s", getName(), getPosition()));
                        worldContext.add(organism);
                    });
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
