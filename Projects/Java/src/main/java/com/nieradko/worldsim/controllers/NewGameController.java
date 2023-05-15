package com.nieradko.worldsim.controllers;

import com.nieradko.worldsim.IWorldEventsHandler;
import com.nieradko.worldsim.core.World;
import com.nieradko.worldsim.core.WorldMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class NewGameController implements Initializable {
    @FXML
    public TextField widthField;
    @FXML
    public TextField heightField;
    @FXML
    public ToggleGroup typeGroup;

    private final ArrayList<IWorldEventsHandler> _worldEventsHandler = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            var newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        widthField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
        widthField.setText("20");
        heightField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
        heightField.setText("20");
    }

    public void addWorldEventHandler(IWorldEventsHandler worldEventsHandler) {
        _worldEventsHandler.add(worldEventsHandler);
    }

    public void handleCreateButton() {
        var width = Integer.parseInt(widthField.getText());
        var height = Integer.parseInt(heightField.getText());
        var toggle = typeGroup.getSelectedToggle();

        if (width <= 0 || height <= 0) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid values!");
            alert.setContentText("Width and/or Height cannot be less or equal to 0");
            alert.showAndWait();
            return;
        }

        if (toggle == null || toggle.getUserData() == null) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid values!");
            alert.setContentText("World type is not selected!");
            alert.showAndWait();
            return;
        }

        WorldMode mode = toggle.getUserData().equals("Hex") ? WorldMode.Hex : WorldMode.Square;

        _worldEventsHandler.forEach(handler -> {
            handler.updateWorld(new World(width, height, mode, handler.getGUIContext()));
        });

        closeWindow();
    }

    public void handleCancelButton() {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage)widthField.getScene().getWindow()).close();
    }
}
