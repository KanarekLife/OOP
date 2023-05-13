package com.nieradko.worldsim.core;

import java.util.Arrays;
import java.util.stream.Stream;

public class SquarePosition extends Position {

    public SquarePosition(int x, int y) {
        super(x, y);
    }

    @Override
    public Stream<Position> getAllNearbyPosition(int distance) {
        var possibleMoves = new Position[]{
                new SquarePosition(getX(), getY() + distance),
                new SquarePosition(getX() + distance, getY() + distance),
                new SquarePosition(getX() + distance, getY()),
                new SquarePosition(getX() + distance, getY() - distance),
                new SquarePosition(getX(), getY() - distance),
                new SquarePosition(getX() - distance, getY() - distance),
                new SquarePosition(getX() - distance, getY()),
                new SquarePosition(getX() - distance, getY() + distance)
        };

        return Arrays.stream(possibleMoves);
    }
}
