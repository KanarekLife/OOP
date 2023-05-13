package com.nieradko.worldsim.core;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HexPosition extends Position {

    public HexPosition(int x, int y) {
        super(x, y);
    }

    @Override
    public Stream<Position> getAllNearbyPosition(int distance) {
        HexPosition[] possibleMoves;

        if (getY() % 2 == 0) {
            possibleMoves = new HexPosition[]{
                    new HexPosition(getX() - 1, getY()),
                    new HexPosition(getX() - 1, getY() + 1),
                    new HexPosition(getX(), getY() + 1),
                    new HexPosition(getX() + 1, getY()),
                    new HexPosition(getX(), getY() - 1),
                    new HexPosition(getX() - 1, getY() - 1)
            };
        } else {
            possibleMoves = new HexPosition[]{
                    new HexPosition(getX() - 1, getY()),
                    new HexPosition(getX(), getY() + 1),
                    new HexPosition(getX() + 1, getY() + 1),
                    new HexPosition(getX() + 1, getY()),
                    new HexPosition(getX() + 1, getY() - 1),
                    new HexPosition(getX(), getY() - 1)
            };
        }

        if (distance == 1) {
            return Arrays.stream(possibleMoves);
        }

        var queue = new LinkedList<>(Arrays.asList(possibleMoves));
        var hashSet = new HashSet<Position>(Arrays.asList(possibleMoves));
        hashSet.add(this);
        int depth = 1;
        var result = new ArrayList<Position>();

        while (depth < distance) {
            result.clear();
            for (var pos : queue) {
                var moves = StreamSupport.stream(pos.getAllNearbyPosition(1).spliterator(), false)
                        .filter(p -> !hashSet.contains(p))
                        .toList();
                hashSet.addAll(moves);
                result.addAll(moves);
            }

            depth++;
            queue.addAll(result.stream().map(e -> (HexPosition) e).toList());
        }

        return result.stream();
    }
}
