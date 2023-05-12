package com.nieradko.worldsim.core;

import com.nieradko.worldsim.core.animals.Animal;

import java.util.*;

public class World implements IWorldContext, IActionContext {
    private final int N;
    private final int M;
    private final WorldMode mode;

    private final SortedSet<Organism> organisms = new TreeSet<>();

    public World(int n, int m, WorldMode mode) {
        this.N = n;
        this.M = m;
        this.mode = mode;
    }

    public void simulateRound() {
        for (var organism : organisms) {
            organism.handleAction(this);
        }

        organisms.forEach(Organism::makeOlder);
    }

    @Override
    public Optional<SquarePosition> getRandomNearbyPosition(SquarePosition position) {
        return Optional.empty();
    }

    @Override
    public Optional<SquarePosition> getRandomNearbyPosition(SquarePosition position, boolean empty) {
        return Optional.empty();
    }

    @Override
    public Optional<SquarePosition> getRandomNearbyPosition(SquarePosition position, boolean empty, int distance) {
        return Optional.empty();
    }

    @Override
    public void move(IMovable movable, SquarePosition to) {

    }

    @Override
    public void add(Organism organism) {

    }

    @Override
    public void kill(Organism animal) {

    }

    @Override
    public int getNumberOfLivingOrganisms() {
        return 0;
    }

    @Override
    public Iterable<Animal> getNearbyAnimals(SquarePosition position) {
        return null;
    }
}
