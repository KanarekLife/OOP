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
    private final ArrayList<Organism> organisms = new ArrayList<>();
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
        for (var organism : organisms
                .stream()
                .sorted((Comparator.comparing(Organism::getInitiative).reversed()).thenComparing(Organism::getAge).reversed())
                .toList()) {
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

    public <T extends Organism> void add(Class<T> c, int n) {
        try {
            var constructor = c.getDeclaredConstructor(Position.class);
            for (int i = 0; i < n; i++) {
                add(constructor.newInstance(getRandomPosition()));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private Position getPosition(int x, int y) {
        return switch (mode) {
            case Hex -> new HexPosition(x, y);
            case Square -> new SquarePosition(x, y);
        };
    }

    private Position getRandomPosition() {
        var rng = ThreadLocalRandom.current();
        var position = getPosition(rng.nextInt(N), rng.nextInt(M));
        while(!isPositionEmpty(position)) {
            position = getPosition(rng.nextInt(N), rng.nextInt(M));
        }
        return position;
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
        var stream = position.getAllNearbyPosition(distance)
                .filter(e -> e.isWithinWorldOfSize(N, M));

        if (empty) {
            stream = stream.filter(e -> organisms.stream().noneMatch(o -> o.getPosition().equals(e)));
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

        var attacker = (Animal)movable;

        var optionalDefender = organisms
                .stream()
                .filter(o -> o.getPosition().equals(to))
                .findFirst();

        if (optionalDefender.isEmpty()) {
            guiContext.handleOrganismMoved(attacker, to);
            movable.moveTo(to);
            return;
        }

        var defender = optionalDefender.get();

        var context = new CollisionContext(attacker, defender);
        defender.handleCollision(context, this);
        var result = context.getResult();

        switch (result) {
            case AttackerWon -> {
                kill(defender);
                guiContext.handleOrganismMoved(attacker, to);
                movable.moveTo(to);
            }
            case DefenderWon -> {
                kill(attacker);
            }
            case DefenderEvaded -> {
                guiContext.handleOrganismMoved(attacker, to);
                movable.moveTo(to);
            }
            case BothDied -> {
                kill(defender);
                kill(attacker);
            }
        }
    }

    @Override
    public void add(Organism organism) {
        if (isPositionEmpty(organism.getPosition())) {
            this.organisms.add(organism);
            guiContext.handleOrganismAdded(organism);
        }
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

    private boolean isPositionEmpty(Position pos) {
        return organisms.stream().noneMatch(o -> o.getPosition().equals(pos));
    }
}
