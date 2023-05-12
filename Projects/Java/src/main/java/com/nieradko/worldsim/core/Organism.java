package com.nieradko.worldsim.core;

import java.util.Optional;

public abstract class Organism implements Comparable<Organism> {
    protected int strength;
    private final int initiative;
    private int age;
    protected SquarePosition position;

    protected Organism(int strength, int initiative, SquarePosition position) {
        this.strength = strength;
        this.initiative = initiative;
        this.position = position;
        this.age = 0;
    }

    protected abstract void handleAction(IActionContext context);
    protected abstract void handleCollision(ICollisionContext context);

    protected Optional<Organism> getNewInstance(SquarePosition position) {
        try {
            var newOrganism = this.getClass()
                    .getDeclaredConstructor(SquarePosition.class)
                    .newInstance(position);
            return Optional.of(newOrganism);
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return Optional.empty();
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

    public SquarePosition getPosition() {
        return position;
    }

    public void makeOlder() { this.age++; }

    @Override
    public int compareTo(Organism o) {
        var comparison = Integer.compare(this.initiative, o.initiative);

        if (comparison != 0) {
            return comparison * -1;
        }

        return Integer.compare(this.age, o.age) * -1;
    }
}
