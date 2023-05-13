package com.nieradko.worldsim.controllers;

import com.nieradko.worldsim.IGUIContext;
import com.nieradko.worldsim.core.Organism;
import com.nieradko.worldsim.core.Position;
import com.nieradko.worldsim.core.World;
import com.nieradko.worldsim.core.WorldMode;
import com.nieradko.worldsim.core.animals.Wolf;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController implements IGUIContext {
    final int GAP = 0;
    final double TILE_SIZE = 60;

    @FXML
    public HBox map;
    @FXML
    public VBox window;
    @FXML
    public ListView logs;
    private World world = null;

    @FXML
    public void initialize() {
        world = new World(20, 20, WorldMode.Hex, this);
        renderMap();

        world.add(new Wolf(world.getNewPosition(5, 5)));
    }

    public void handleAboutButton() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About WorldSim");
        alert.setHeaderText("WorldSim");
        alert.setContentText("WorldSim by Stanisław Nieradko [193044]");
        alert.setResult(ButtonType.OK);
        alert.showAndWait();
    }

    public void handleExitButton() {
        Platform.exit();
    }

    private void renderMap() {
        if (this.world == null) {
            return;
        }

        switch (world.getMode()) {
            case Hex -> drawHexMap(world.getN(), world.getM());
            case Square -> drawSquareMap(world.getN(), world.getM());
        }
    }

    private void drawSquareMap(int n, int m) {
        map.getChildren().clear();
        map.setSpacing(GAP);
        for (var i = 0; i < m; i++) {
            var row = new VBox();
            row.setSpacing(GAP);
            for (var j = 0; j < n; j++) {
                var tile = new VBox();
                tile.getStyleClass().add("square-tile");
                tile.setMinWidth(TILE_SIZE);
                tile.setMaxWidth(TILE_SIZE);
                tile.setMinHeight(TILE_SIZE);
                tile.setMaxHeight(TILE_SIZE);
                row.getChildren().add(tile);
            }
            map.getChildren().add(row);
        }
    }

    private void drawHexMap(int n, int m) {
        final double VSPACING = ((TILE_SIZE / 4)) * -1 + GAP;
        final double PADDING = ((TILE_SIZE / 3) * 2) - 10  + GAP;

        map.getChildren().clear();
        map.setSpacing(VSPACING);
        for (var i = 0; i < n; i++) {
            var column = new VBox();
            if (i % 2 != 0) {
                column.setPadding(new Insets(PADDING, 0, 0, 0));
            }
            for (var j = 0; j < m; j++) {
                var tile = new VBox();
                tile.getStyleClass().add("hex-tile");
                tile.setMinWidth(TILE_SIZE);
                tile.setMaxWidth(TILE_SIZE);
                tile.setMinHeight(TILE_SIZE);
                tile.setMaxHeight(TILE_SIZE);
                column.getChildren().add(tile);
            }
            map.getChildren().add(column);
        }
    }
    private VBox getTile(int x, int y) {
        return (VBox)((VBox)map.getChildren().get(y)).getChildren().get(x);
    }

    @Override
    public void handleOrganismKilled(Organism organism) {
        var tile = getTile(organism.getPosition().getX(), organism.getPosition().getY());
        tile.getChildren().clear();
    }

    @Override
    public void handleOrganismAdded(Organism organism) {
        var tile = getTile(organism.getPosition().getX(), organism.getPosition().getY());
        tile.getChildren().add(new Label(organism.getClass().getSimpleName()));
    }

    @Override
    public void handleOrganismMoved(Organism organism, Position to) {
        var fromTile = getTile(organism.getPosition().getX(), organism.getPosition().getY());;
        var toTile = getTile(to.getX(), to.getY());
        fromTile.getChildren().clear();
        toTile.getChildren().add(new Label(organism.getClass().getSimpleName()));
    }
}
