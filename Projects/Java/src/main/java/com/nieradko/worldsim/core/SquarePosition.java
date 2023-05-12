package com.nieradko.worldsim.core;

import java.util.Arrays;

public class SquarePosition implements IPosition {
    private final int x;
    private final int y;

    public SquarePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != SquarePosition.class) {
            return false;
        }

        var another = (SquarePosition) obj;

        return another.x == this.x && another.y == this.y;
    }

    @Override
    public Iterable<IPosition> getAllNearbyPosition(int distance) {
        var possibleMoves = new SquarePosition[]{
                new SquarePosition(x, y + distance),
                new SquarePosition(x + distance, y + distance),
                new SquarePosition(x + distance, y),
                new SquarePosition(x + distance, y - distance),
                new SquarePosition(x, y - distance),
                new SquarePosition(x - distance, y - distance),
                new SquarePosition(x - distance, y),
                new SquarePosition(x - distance, y + distance)
        };

        return Arrays.asList(possibleMoves);
    }
}
