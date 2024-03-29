package com.nieradko.worldsim.core;

import java.io.Serializable;

public class Color implements Serializable {
    private final double red;
    private final double green;
    private final double blue;
    private final double alpha;

    public Color(javafx.scene.paint.Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getOpacity();
    }

    public Color(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public javafx.scene.paint.Color getFxColor() {
        return new javafx.scene.paint.Color(red, green, blue, alpha);
    }
}
