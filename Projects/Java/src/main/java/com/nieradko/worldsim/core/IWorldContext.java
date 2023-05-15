package com.nieradko.worldsim.core;

import java.util.Optional;

public interface IWorldContext extends ILogger {
    Optional<Position> getRandomNearbyPosition(Position position);
    Optional<Position> getRandomNearbyPosition(Position position, boolean empty);
    Optional<Position> getRandomNearbyPosition(Position position, boolean empty, int distance);
    void move(IMovable movable, Position to);
    void add(Organism organism);
    void kill(Organism animal);
}
