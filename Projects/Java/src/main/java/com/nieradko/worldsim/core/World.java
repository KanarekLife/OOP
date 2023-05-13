package com.nieradko.worldsim.core;

import com.nieradko.worldsim.IGUIContext;
import com.nieradko.worldsim.core.animals.Animal;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class World implements IWorldContext, IActionContext {
    private final int N;
    private final int M;
    private final WorldMode mode;
    private final SortedSet<Organism> organisms = new TreeSet<>();
    private int round;
    private final IGUIContext guiContext;

    public World(int n, int m, WorldMode mode, IGUIContext guiContext) {
        this.N = n;
        this.M = m;
        this.mode = mode;
        this.round = 0;
        this.guiContext = guiContext;
    }

    public void simulateRound() {
        // TODO Verify that when organism is killed this does not break!
        for (var organism : organisms) {
            organism.handleAction(this);
        }

        organisms.forEach(Organism::makeOlder);

        round++;
    }

    public WorldMode getMode() {
        return this.mode;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public Position getNewPosition(int x, int y) {
        return switch (mode) {
            case Hex -> new HexPosition(x, y);
            case Square -> new SquarePosition(x, y);
        };
    }

    @Override
    public Optional<Position> getRandomNearbyPosition(Position position) {
        return getRandomNearbyPosition(position, false, 1);
    }

    @Override
    public Optional<Position> getRandomNearbyPosition(Position position, boolean empty) {
        return getRandomNearbyPosition(position, empty, 1);
    }

    @Override
    public Optional<Position> getRandomNearbyPosition(Position position, boolean empty, int distance) {
        // TODO Could be simplified with hashset
        var stream = position.getAllNearbyPosition(distance);

        if (empty) {
            stream = stream.filter(e -> organisms.stream().anyMatch(o -> o.getPosition().equals(e)));
        }

        var arr = stream.toArray(Position[]::new);

        if (arr.length == 0) {
            return Optional.empty();
        }

        return Optional.of(arr[ThreadLocalRandom.current().nextInt(arr.length)]);
    }

    @Override
    public void move(IMovable movable, Position to) {
        if (!to.isWithinWorldOfSize(N, M)) {
            return;
        }

        var optionalDefender = organisms
                .stream()
                .filter(o -> o.getPosition().equals(to))
                .findFirst();

        if (optionalDefender.isEmpty()) {
            movable.moveTo(to);
            return;
        }

        var defender = optionalDefender.get();

        var optionalAttacker = organisms
                .stream()
                .filter(o -> o instanceof Animal)
                .filter(o -> o.getPosition().equals(movable.getPosition()))
                .map(o -> (Animal)o)
                .findFirst();

        if (optionalAttacker.isEmpty()) {
            throw new RuntimeException("Shouldn't happen");
        }

        var attacker = optionalAttacker.get();

        var context = new CollisionContext(attacker, defender);
        defender.handleCollision(context, this);
        var result = context.getResult();

        if (result.isEmpty()) {
            throw new RuntimeException("Shouldn't happen");
        }

        switch (result.get()) {
            case AttackerWon -> {
                kill(defender);
                movable.moveTo(to);
                guiContext.handleOrganismMoved(attacker, to);
            }
            case DefenderWon -> {
                kill(attacker);
            }
            case DefenderEvaded -> {
                movable.moveTo(to);
                guiContext.handleOrganismMoved(attacker, to);
            }
            case BothDied -> {
                kill(defender);
                kill(attacker);
            }
        }
    }

    @Override
    public void add(Organism organism) {
        this.organisms.add(organism);
        guiContext.handleOrganismAdded(organism);
    }

    @Override
    public void kill(Organism organism) {
        this.organisms.remove(organism);
        guiContext.handleOrganismKilled(organism);
    }

    @Override
    public int getNumberOfLivingOrganisms() {
        return organisms.size();
    }

    @Override
    public Stream<Animal> getNearbyAnimals(Position position) {
        return position.getAllNearbyPosition(1)
                .map(p -> organisms
                        .stream()
                        .filter(o -> o instanceof Animal)
                        .map(o -> (Animal)o)
                        .filter(o -> o.getPosition().equals(p))
                        .findFirst()
                )
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
