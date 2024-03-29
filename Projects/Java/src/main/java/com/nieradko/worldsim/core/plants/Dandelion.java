package com.nieradko.worldsim.core.plants;

import com.nieradko.worldsim.core.IActionContext;
import com.nieradko.worldsim.core.Position;
import javafx.scene.paint.Color;

public class Dandelion extends Plant {
    public Dandelion(Position position) {
        super(0, Color.YELLOW, position);
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
