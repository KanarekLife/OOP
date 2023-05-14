package com.nieradko.worldsim.core;

import com.nieradko.worldsim.IGUIContext;
import com.nieradko.worldsim.core.animals.*;
import com.nieradko.worldsim.core.plants.*;
import com.nieradko.worldsim.core.positions.HexPosition;
import com.nieradko.worldsim.core.positions.SquarePosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class World implements IWorldContext, IActionContext, Serializable, ILogger {
    private final int N;
    private final int M;
    private final WorldMode mode;
    private final ArrayList<Organism> organisms = new ArrayList<>();
    private final ObservableList<Log> logs = FXCollections.observableArrayList();
    private int round = 1;
    private WorldState state = WorldState.WaitingForNewRound;
    private boolean isGameRunning = true;
    private transient IGUIContext guiContext;

    public World(int n, int m, WorldMode mode, IGUIContext guiContext) {
        this.N = n;
        this.M = m;
        this.mode = mode;
        this.round = 0;
        this.guiContext = guiContext;
    }

    public void render() {
        guiContext.clearScreen();
        organisms.forEach(guiContext::drawOrganism);
    }

    public void setGuiContext(IGUIContext guiContext) {
        this.guiContext = guiContext;
    }

    public void simulateRound() {
        if (!isGameRunning) {
            return;
        }

        if (state == WorldState.WaitingForHumanInput) {
            boolean humanFound = false;
            for (var organism : organisms
                    .stream()
                    .sorted((Comparator.comparing(Organism::getInitiative).reversed()).thenComparing(Organism::getAge).reversed())
                    .toList()) {
                if (organism instanceof Human) {
                    humanFound = true;
                    continue;
                }
                if (humanFound) {
                    organism.handleAction(this);
                }
            }
            render();
            organisms.forEach(Organism::makeOlder);

            log("Round ended");

            round++;
            state = WorldState.WaitingForNewRound;
        }

        log("Round started");

        for (var organism : organisms
                .stream()
                .sorted((Comparator.comparing(Organism::getInitiative).reversed()).thenComparing(Organism::getAge).reversed())
                .toList()) {
            organism.handleAction(this);
            if (organism instanceof Human) {
                break;
            }
        }

        render();
        state = WorldState.WaitingForHumanInput;
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

    public void seed() {
        if (getNumberOfLivingOrganisms() == 0) {
            add(Dandelion.class, 1);
            add(Antelope.class, 6);
            add(Grass.class, 1);
            add(Fox.class, 2);
            add(Guarana.class, 1);
            add(Sheep.class, 8);
            add(Nightshade.class, 1);
            add(Turtle.class, 2);
            add(PineBorscht.class, 1);
            add(Wolf.class, 3);
            add(Human.class, 1);
        }
    }

    public ObservableList<Log> getLogs() {
        return logs;
    }

    private <T extends Organism> void add(Class<T> c, int n) {
        try {
            var constructor = c.getDeclaredConstructor(Position.class);
            for (int i = 0; i < n; i++) {
                add(constructor.newInstance(getRandomPosition()));
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public boolean isGameRunning() {
        return isGameRunning;
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

    private boolean isPositionEmpty(Position pos) {
        return organisms.stream().noneMatch(o -> o.getPosition().equals(pos));
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
        var stream = position.getAllNearbyPositions(distance)
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
            log(String.format("%s moved to %s", attacker.getClass().getSimpleName(), to));
            movable.moveTo(to);
            return;
        }

        var defender = optionalDefender.get();

        var context = new CollisionContext(attacker, defender);
        defender.handleCollision(context, this);
        var result = context.getResult();

        switch (result) {
            case AttackerWon -> {
                log(String.format("%s won battle with defending %s and thus moved to %s", attacker.getClass().getSimpleName(), defender.getClass().getSimpleName(), to));
                kill(defender);
                movable.moveTo(to);
            }
            case DefenderWon -> {
                log(String.format("%s won battle with attacking %s at %s", defender.getClass().getSimpleName(), attacker.getClass().getSimpleName(), to));
                kill(attacker);
            }
            case DefenderEvaded -> {
                log(String.format("%s evaded and thus %s moved to %s", defender.getClass().getSimpleName(), attacker.getClass().getSimpleName(), to));
                movable.moveTo(to);
            }
            case BothDied -> {
                log(String.format("Both %s and %s have died simultaneously at %s", defender.getClass().getSimpleName(), attacker.getClass().getSimpleName(), to));
                kill(defender);
                kill(attacker);
            }
        }
    }

    @Override
    public void add(Organism organism) {
        if (isPositionEmpty(organism.getPosition())) {
            log(String.format("%s added at %s", organism.getClass().getSimpleName(), organism.getPosition().toString()));
            this.organisms.add(organism);
        }
    }

    @Override
    public void kill(Organism organism) {
        if (organism instanceof Human) {
            isGameRunning = false;
            log("Human has died. Game over!");
            guiContext.stopGame();
        }
        log(String.format("%s died at %s", organism.getClass().getSimpleName(), organism.getPosition().toString()));
        this.organisms.remove(organism);
    }

    @Override
    public int getNumberOfLivingOrganisms() {
        return organisms.size();
    }

    @Override
    public Stream<Animal> getNearbyAnimals(Position position) {
        return position.getAllNearbyPositions(1)
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

    @Override
    public void requestRender() {
        render();
    }

    @Override
    public IGUIContext getGUIContext() {
        return guiContext;
    }

    @Override
    public void log(String message) {
        this.logs.add(new Log(round, message));
    }

    public Human getHuman() {
        return (Human)organisms.stream().filter(e -> e instanceof Human).findFirst().get();
    }
}
