package com.nieradko.worldsim.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.StreamSupport;

public class HexPosition implements IPosition {
    private final int x;
    private final int y;

    public HexPosition(int x, int y) {
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
        if (obj.getClass() != HexPosition.class) {
            return false;
        }

        var another = (HexPosition) obj;

        return another.x == this.x && another.y == this.y;
    }

    @Override
    public Iterable<IPosition> getAllNearbyPosition(int distance) {
        HexPosition[] possibleMoves;

        if (y % 2 == 0) {
            possibleMoves = new HexPosition[]{
                    new HexPosition(x - 1, y),
                    new HexPosition(x - 1, y + 1),
                    new HexPosition(x, y + 1),
                    new HexPosition(x + 1, y),
                    new HexPosition(x, y - 1),
                    new HexPosition(x - 1, y - 1)
            };
        }
        else {
            possibleMoves = new HexPosition[]{
                    new HexPosition(x - 1, y),
                    new HexPosition(x, y + 1),
                    new HexPosition(x + 1, y + 1),
                    new HexPosition(x + 1, y),
                    new HexPosition(x + 1, y - 1),
                    new HexPosition(x, y - 1)
            };
        }

        if (distance == 1) {
            return Arrays.asList(possibleMoves);
        }

        var queue = new LinkedList<>(Arrays.asList(possibleMoves));
        var hashSet = new HashSet<>(Arrays.asList(possibleMoves));
        hashSet.add(this);
        int depth = 1;
        var result = new ArrayList<IPosition>();

        while(depth < distance) {
            result.clear();
            for (var pos : queue) {
                for (var position : pos.getAllNearbyPosition(1)) {
                    if (hashSet.contains((HexPosition) position)) {
                        continue;
                    }

                    hashSet.add((HexPosition) position);
                    result.add(position);
                }
            }

            depth++;
            queue.addAll(result.stream().map(e -> (HexPosition)e).toList());
        }

        return result;
    }
}
