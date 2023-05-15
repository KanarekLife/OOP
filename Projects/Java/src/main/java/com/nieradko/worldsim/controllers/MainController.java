package com.nieradko.worldsim.controllers;

import com.nieradko.worldsim.Application;
import com.nieradko.worldsim.IGUIContext;
import com.nieradko.worldsim.IWorldEventsHandler;
import com.nieradko.worldsim.core.*;
import com.nieradko.worldsim.core.positions.HexPosition;
import com.nieradko.worldsim.core.positions.SquarePosition;
import com.nieradko.worldsim.helpers.Tile;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.stream.Stream;

public class MainController implements IGUIContext, IWorldEventsHandler {
    final double TILE_SIZE = 60;
    private final SimpleObjectProperty<World> world = new SimpleObjectProperty<>(null);
    private final SimpleStringProperty saveFile = new SimpleStringProperty(null);
    private Tile[] controlButtons = null;
    private Tile abilityButton = null;

    @FXML
    public HBox map;
    @FXML
    public VBox window;
    @FXML
    public ListView<Label> logs;
    @FXML
    public HBox controls;

    // FXML actions

    @FXML
    public void initialize() {
        world.addListener(e -> {
            var newWorld = ((SimpleObjectProperty<World>) e).getValue();
            renderMap();
            newWorld.seed();
            newWorld.render();
            logs.getItems().clear();
            newWorld.getLogs().forEach(this::log);
            if (newWorld.isGameRunning()) {
                newWorld.simulateRound();
            }
        });
        saveFile.addListener(e -> {
            var stage = ((Stage) getWindow());
            var value = ((StringProperty) e).getValue();
            if (value == null) {
                stage.setTitle("Stanisław Nieradko [193044]");
            } else {
                stage.setTitle(String.format("Stanisław Nieradko [193044] %s", value));
            }
        });
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

    public void handleNewGameButton() throws IOException {
        var fxmlLoader = new FXMLLoader(Application.class.getResource("views/newgame.fxml"));
        var scene = new Scene(fxmlLoader.load());
        fxmlLoader.<NewGameController>getController()
                .addWorldEventHandler(this);
        var stage = new Stage();
        stage.setTitle("Create new world");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.showAndWait();
    }

    public void handleLoadGameButton() throws IOException, ClassNotFoundException {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your game");
        fileChooser.setInitialFileName("game.world");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game Files", "*.world"));
        var file = fileChooser.showOpenDialog(getWindow());
        if (file == null) {
            return;
        }

        if (!file.exists()) {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("File not found!");
            alert.showAndWait();
            return;
        }

        var stream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
        World world = (World) stream.readObject();
        world.setGuiContext(this);
        updateWorld(world);
        saveFile.setValue(file.getAbsolutePath());
        stream.close();
        log(String.format("Loaded world from %s", saveFile.get()));
    }

    public void handleSaveGameButton() throws IOException {
        if (saveFile.getValue() == null) {
            handleSaveAsGameButton();
            return;
        }

        var stream = new ObjectOutputStream(new FileOutputStream(saveFile.get(), false));
        World tmpWorld = world.getValue();
        stream.writeObject(tmpWorld);
        stream.close();
        log(String.format("Saved game to %s", saveFile.get()));
    }

    public void handleSaveAsGameButton() throws IOException {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Choose where to save your game");
        fileChooser.setInitialFileName("game.world");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game Files", "*.world"));
        var file = fileChooser.showSaveDialog(getWindow());

        if (file == null) {
            return;
        }
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException();
        }

        saveFile.setValue(file.getAbsolutePath());
        handleSaveGameButton();
    }

    // interface methods

    @Override
    public void clearScreen() {
        this.map.getChildren()
                .stream()
                .flatMap(e -> ((VBox) e).getChildren().stream())
                .forEach(n -> {
                    ((Tile)n).clear();
                });
    }

    @Override
    public void drawOrganism(Organism organism) {
        getTile(organism.getPosition().getX(), organism.getPosition().getY())
                .drawOrganism(organism);
    }

    @Override
    public void setupHumanControls(Stream<Position> allNearbyPositions) {
        var iterator = allNearbyPositions.iterator();
        for (var button : controlButtons) {
            button.setPadding(new Insets(5));
            final Position position = iterator.next();
            button.setOnMouseClicked(e -> {
                var tmp = world.getValue();
                if (tmp != null && tmp.isGameRunning()) {
                    tmp.move(tmp.getHuman(), position);
                    tmp.simulateRound();
                }
            });
        }

        var specialPowerTimer = world.getValue().getHuman().getSpecialPowerTimer();
        ((Label)abilityButton.getChildren().get(0)).setText(specialPowerTimer == 0 ? "🔥" : Integer.toString(specialPowerTimer));

        abilityButton.setOnMouseClicked(e -> {
            var tmp = world.getValue();
            if (tmp != null && tmp.isGameRunning()) {
                tmp.getHuman().tryToActivateSpecialPower(tmp);
                tmp.render();
            }
        });
    }

    @Override
    public void stopGame() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Human has died.");
        alert.showAndWait();
    }

    @Override
    public IGUIContext getGUIContext() {
        return this;
    }

    @Override
    public void updateWorld(World world) {
        this.world.setValue(world);
        this.saveFile.setValue(null);
    }

    public void log(Log log) {
        logs.getItems().add(new Label(String.format("[%d] %s", log.round(), log.message())));
        logs.scrollTo(logs.getItems().size() - 1);
    }

    private void renderMap() {
        if (this.world.getValue() == null) {
            return;
        }

        switch (world.getValue().getMode()) {
            case Hex -> drawHexMap(world.getValue().getN(), world.getValue().getM());
            case Square -> drawSquareMap(world.getValue().getN(), world.getValue().getM());
        }

        abilityButton.getChildren().add(new Label("🔥"));
    }

    private void drawSquareMap(int n, int m) {
        final double CONTROL_TILE = TILE_SIZE / 2;

        map.getChildren().clear();
        for (var i = 0; i < m; i++) {
            var row = new VBox();
            for (var j = 0; j < n; j++) {
                var tile = new Tile(TILE_SIZE, Color.GHOSTWHITE, WorldMode.Square, j, i);
                tile.setOnMouseClicked(e -> addOnField(new SquarePosition(tile.getX(), tile.getY())));
                row.getChildren().add(tile);
            }
            map.getChildren().add(row);
        }

        controls.getChildren().clear();
        for (var i = 0; i < 3; i++) {
            var column = new VBox();
            for (var j = 0; j < 3; j++) {
                var tile = new Tile(CONTROL_TILE, Color.GHOSTWHITE, WorldMode.Square, j, i);
                tile.setPadding(new Insets(5));
                column.getChildren().add(tile);
            }
            controls.getChildren().add(column);
        }

        controlButtons = new Tile[] {
                (Tile)((VBox)controls.getChildren().get(2)).getChildren().get(1),
                (Tile)((VBox)controls.getChildren().get(2)).getChildren().get(2),
                (Tile)((VBox)controls.getChildren().get(1)).getChildren().get(2),
                (Tile)((VBox)controls.getChildren().get(0)).getChildren().get(2),
                (Tile)((VBox)controls.getChildren().get(0)).getChildren().get(1),
                (Tile)((VBox)controls.getChildren().get(0)).getChildren().get(0),
                (Tile)((VBox)controls.getChildren().get(1)).getChildren().get(0),
                (Tile)((VBox)controls.getChildren().get(2)).getChildren().get(0),
        };
        abilityButton = (Tile)((VBox)controls.getChildren().get(1)).getChildren().get(1);
    }

    private void drawHexMap(int n, int m) {
        final double VSPACING = ((TILE_SIZE / 4)) * -1;
        final double PADDING = ((TILE_SIZE / 3) * 2) - 10;
        final double CONTROL_TILE = TILE_SIZE / 2;
        final double CONTROL_VSPACING = ((CONTROL_TILE / 4)) * -1;
        final double CONTROL_PADDING = ((CONTROL_TILE / 3) * 2) - 5;

        map.getChildren().clear();
        map.setSpacing(VSPACING);
        for (var i = 0; i < n; i++) {
            var column = new VBox();
            if (i % 2 != 0) {
                column.setPadding(new Insets(PADDING, 0, 0, 0));
            }
            for (var j = 0; j < m; j++) {
                var tile = new Tile(TILE_SIZE, Color.GHOSTWHITE, WorldMode.Hex, j, i);
                tile.setOnMouseClicked(e -> addOnField(new HexPosition(tile.getX(), tile.getY())));
                column.getChildren().add(tile);
            }
            map.getChildren().add(column);
        }

        controls.getChildren().clear();
        controls.setSpacing(CONTROL_VSPACING);
        for (var i = 0; i < 3; i++) {
            var column = new VBox();
            if (i % 2 != 0) {
                column.setPadding(new Insets(CONTROL_PADDING, 0, 0, 0));
            }
            for (var j = 0; j < 3; j++) {
                var tile = new Tile(CONTROL_TILE, Color.GHOSTWHITE, WorldMode.Hex, j, i);
                tile.setPadding(new Insets(5));
                if ((i == 0 && j == 0) || (i == 2 && j == 0)) {
                    tile.hide();
                }
                column.getChildren().add(tile);
            }
            controls.getChildren().add(column);
        }
        controlButtons = new Tile[] {
                (Tile)((VBox)controls.getChildren().get(1)).getChildren().get(0),
                (Tile)((VBox)controls.getChildren().get(2)).getChildren().get(1),
                (Tile)((VBox)controls.getChildren().get(2)).getChildren().get(2),
                (Tile)((VBox)controls.getChildren().get(1)).getChildren().get(2),
                (Tile)((VBox)controls.getChildren().get(0)).getChildren().get(2),
                (Tile)((VBox)controls.getChildren().get(0)).getChildren().get(1)
        };
        abilityButton = (Tile)((VBox)controls.getChildren().get(1)).getChildren().get(1);
    }

    private void addOnField(Position position) {
        var tmp = world.getValue();

        if (tmp == null) {
            return;
        }

        try {
            var fxmlLoader = new FXMLLoader(Application.class.getResource("views/neworganism.fxml"));
            var scene = new Scene(fxmlLoader.load());
            fxmlLoader.<NewOrganismController>getController()
                    .setWorld(world.getValue())
                    .setPosition(position);
            var stage = new Stage();
            stage.setTitle("Add new organism");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.showAndWait();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private Tile getTile(int x, int y) {
        return (Tile) ((VBox) map.getChildren().get(y)).getChildren().get(x);
    }

    private Window getWindow() {
        return map.getScene().getWindow();
    }

    private void log(String message) {
        logs.getItems().add(new Label(String.format("[!] %s", message)));
    }
}
