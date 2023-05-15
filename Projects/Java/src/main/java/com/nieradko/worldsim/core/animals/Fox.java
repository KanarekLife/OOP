package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.Position;
import javafx.scene.paint.Color;

public class Fox extends Animal {
    public Fox(Position position) {
        super(3, 7, Color.ORANGE, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getRandomNearbyPosition(getPosition(), true)
                .ifPresent(value -> context.move(this, value));
    }
}
