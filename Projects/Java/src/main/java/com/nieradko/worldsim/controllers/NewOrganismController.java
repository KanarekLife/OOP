package com.nieradko.worldsim.controllers;

import com.nieradko.worldsim.core.Position;
import com.nieradko.worldsim.core.World;
import com.nieradko.worldsim.core.animals.*;
import com.nieradko.worldsim.core.plants.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class NewOrganismController {
    @FXML
    public Label label;

    private World world = null;
    private Position position = null;

    public NewOrganismController setWorld(World world) {
        this.world = world;
        return this;
    }

    public NewOrganismController setPosition(Position position) {
        this.position = position;
        return this;
    }

    public void handleAddButton(ActionEvent actionEvent) {
        if (world == null || position == null) {
            return;
        }

        switch (((Button) actionEvent.getSource()).getUserData().toString()) {
            case "Wolf" -> world.add(new Wolf(position));
            case "Sheep" -> world.add(new Sheep(position));
            case "Fox" -> world.add(new Fox(position));
            case "Turtle" -> world.add(new Turtle(position));
            case "Antelope" -> world.add(new Antelope(position));
            case "Grass" -> world.add(new Grass(position));
            case "Dandelion" -> world.add(new Dandelion(position));
            case "Guarana" -> world.add(new Guarana(position));
            case "Nightshade" -> world.add(new Nightshade(position));
            case "PineBorscht" -> world.add(new PineBorscht(position));
            default -> throw new RuntimeException("That shouldn't happen");
        }

        world.render();

        closeWindow();
    }

    private void closeWindow() {
        ((Stage)label.getScene().getWindow()).close();
    }
}
