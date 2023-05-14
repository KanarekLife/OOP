package com.nieradko.worldsim;

import com.nieradko.worldsim.core.Organism;

public interface IGUIContext {
    void clearScreen();
    void drawOrganism(Organism organism);
}
