package com.nieradko.worldsim.core.animals;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.SquarePosition;

public class Fox extends Animal {
    protected Fox(SquarePosition position) {
        super(3, 7, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        context.getRandomNearbyPosition(getPosition(), true)
                .ifPresent(value -> context.move(this, value));
    }
}
