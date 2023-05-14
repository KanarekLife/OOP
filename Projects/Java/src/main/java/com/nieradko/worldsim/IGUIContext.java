package com.nieradko.worldsim;

import com.nieradko.worldsim.core.Log;
import com.nieradko.worldsim.core.Organism;
import com.nieradko.worldsim.core.Position;

import java.util.stream.Stream;

public interface IGUIContext {
    void clearScreen();
    void drawOrganism(Organism organism);
    void setupHumanControls(Stream<Position> allNearbyPositions);
    void stopGame();
    void log(Log log);
}

