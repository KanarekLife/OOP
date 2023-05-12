package com.nieradko.worldsim.core;

import java.util.Optional;

public interface IWorldContext {
    Optional<SquarePosition> getRandomNearbyPosition(SquarePosition position);
    Optional<SquarePosition> getRandomNearbyPosition(SquarePosition position, boolean empty);
    Optional<SquarePosition> getRandomNearbyPosition(SquarePosition position, boolean empty, int distance);
    void move(IMovable movable, SquarePosition to);
    void add(Organism organism);
    void kill(Organism animal);
}
