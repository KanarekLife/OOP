package com.nieradko.worldsim.core;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class Position {
    private final int x;
    private final int y;

    protected Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    boolean isWithinWorldOfSize(int n, int m) {
        return x >= 0
                && y >= 0
                && x < n
                && y < m;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        return ((Position)obj).x == x && ((Position)obj).y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    abstract Stream<Position> getAllNearbyPosition(int distance);
}
