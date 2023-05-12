package com.nieradko.worldsim.core;

public interface IPosition {
    Iterable<IPosition> getAllNearbyPosition(int distance);
}
