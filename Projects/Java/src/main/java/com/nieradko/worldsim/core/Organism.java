package com.nieradko.worldsim.core;

import javafx.scene.paint.Color;

import java.util.Optional;

public abstract class Organism {
    protected int strength;
    private final int initiative;
    private int age;
    private Position position;
    private Color color;

    protected Organism(int strength, int initiative, Color color, Position position) {
        this.strength = strength;
        this.initiative = initiative;
        this.color = color;
        this.position = position;
        this.age = 0;
    }

    protected abstract void handleAction(IActionContext context);
    protected abstract void handleCollision(ICollisionContext collisionContext, IWorldContext worldContext);

    protected Optional<Organism> getNewInstance(Position position) {
        try {
            var newOrganism = this.getClass()
                    .getDeclaredConstructor(Position.class)
                    .newInstance(position);
            return Optional.of(newOrganism);
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
            return Optional.empty();
        }
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public Color getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }
    protected void setPosition(Position position) {
        this.position = position;
    }

    public void makeOlder() { this.age++; }
}
