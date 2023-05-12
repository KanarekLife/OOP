package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.SquarePosition;

public class Dandelion extends Plant {
    protected Dandelion(SquarePosition position) {
        super(0, position);
    }

    @Override
    protected void handleAction(IActionContext context) {
        var numberOfLivingOrganisms = context.getNumberOfLivingOrganisms();
        for (var i = 0; i < 3; i++) {
            super.handleAction(context);
            if (numberOfLivingOrganisms < context.getNumberOfLivingOrganisms()) {
                break;
            }
        }
    }
}
