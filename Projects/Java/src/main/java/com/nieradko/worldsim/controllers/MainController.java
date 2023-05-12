package com.nieradko.worldsim.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController {
    final int GAP = 0;

    @FXML
    public VBox map;
    @FXML
    public VBox window;
    @FXML
    public ListView logs;

    @FXML
    public void initialize() {
        int N = 20;
        int M = 20;

        drawHexMap(N, M);
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

    private void drawSquareMap(int n, int m) {
        final double TILE_SIZE = 60;

        map.getChildren().clear();
        map.setSpacing(GAP);
        for (var i = 0; i < m; i++) {
            var row = new HBox();
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
        final double TILE_SIZE = 60;
        final double HSPACING = (TILE_SIZE / 3) + 8 + (2 * GAP);
        final double VSPACING = ((TILE_SIZE / 2) + 1) * -1 + GAP;
        final double PADDING = ((TILE_SIZE / 3) * 2) + 4 + GAP;

        map.getChildren().clear();
        map.setSpacing(VSPACING);
        for (var i = 0; i < m; i++) {
            var row = new HBox();
            row.setSpacing(HSPACING);
            if (i % 2 != 0) {
                row.setPadding(new Insets(0, 0, 0, PADDING));
            }
            for (var j = 0; j < n; j++) {
                var tile = new VBox();
                tile.getStyleClass().add("hex-tile");
                tile.setMinWidth(TILE_SIZE);
                tile.setMaxWidth(TILE_SIZE);
                tile.setMinHeight(TILE_SIZE);
                tile.setMaxHeight(TILE_SIZE);
                row.getChildren().add(tile);
            }
            map.getChildren().add(row);
        }
    }
}
