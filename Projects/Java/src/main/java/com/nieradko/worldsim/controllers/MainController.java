package com.nieradko.worldsim.controllers;

import com.nieradko.worldsim.IGUIContext;
import com.nieradko.worldsim.core.Organism;
import com.nieradko.worldsim.core.Position;
import com.nieradko.worldsim.core.World;
import com.nieradko.worldsim.core.WorldMode;
import com.nieradko.worldsim.core.animals.*;
import com.nieradko.worldsim.core.plants.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MainController implements IGUIContext {
    final int GAP = 0;
    final double TILE_SIZE = 60;
    private World world = null;

    @FXML
    public HBox map;
    @FXML
    public VBox window;
    @FXML
    public ListView logs;
    @FXML
    public Button simulateButton;

    @FXML
    public void initialize() {
        world = new World(20, 20, WorldMode.Hex, this);
        renderMap();

        world.add(Dandelion.class, 1);
        world.add(Antelope.class, 6);
        world.add(Grass.class, 1);
        world.add(Fox.class, 2);
        world.add(Guarana.class, 1);
        world.add(Sheep.class, 8);
        world.add(Nightshade.class, 1);
        world.add(Turtle.class, 2);
        world.add(PineBorscht.class, 1);
        world.add(Wolf.class, 3);
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
                tile.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, new CornerRadii(0), new Insets(0))));
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
        tile.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
    }

    @Override
    public void handleOrganismAdded(Organism organism) {
        var tile = getTile(organism.getPosition().getX(), organism.getPosition().getY());
        tile.getChildren().add(new Label(organism.getClass().getSimpleName()));
        tile.setBackground(new Background(new BackgroundFill(organism.getColor(), new CornerRadii(0), new Insets(0))));
    }

    @Override
    public void handleOrganismMoved(Organism organism, Position to) {
        var fromTile = getTile(organism.getPosition().getX(), organism.getPosition().getY());;
        var toTile = getTile(to.getX(), to.getY());
        fromTile.getChildren().clear();
        fromTile.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, new CornerRadii(0), new Insets(0))));
        toTile.getChildren().add(new Label(organism.getClass().getSimpleName()));
        toTile.setBackground(new Background(new BackgroundFill(organism.getColor(), new CornerRadii(0), new Insets(0))));
    }

    public void handleSimulateButton() {
        this.world.simulateRound();
    }
}
