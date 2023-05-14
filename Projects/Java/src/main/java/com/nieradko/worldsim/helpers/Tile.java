package com.nieradko.worldsim.helpers;

import com.nieradko.worldsim.core.Organism;
import com.nieradko.worldsim.core.WorldMode;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Tile extends VBox {
    private final int x;
    private final int y;

    public Tile(double width, Color color, WorldMode mode, int x, int y) {
        this.x = x;
        this.y = y;

        setMinWidth(width);
        setMaxWidth(width);
        setMinHeight(width);
        setMaxHeight(width);
        setBackground(getBackground(color));
        getStyleClass().add(mode == WorldMode.Square ? "square-tile" : "hex-tile");
        Tooltip.install(this, new Tooltip(String.format("(%d, %d)", x, y)));
    }

    public void hide() {
        getStyleClass().removeAll(getStyleClass());
        setBackground(Background.EMPTY);
    }

    public void drawOrganism(Organism organism) {
        getChildren().add(new Label(organism.getName()));
        setBackground(getBackground(organism.getColor().getFxColor()));
    }

    public void clear() {
        getChildren().clear();
        setBackground(getBackground(Color.GHOSTWHITE));
    }

    private Background getBackground(Color color) {
        return new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(0)));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
