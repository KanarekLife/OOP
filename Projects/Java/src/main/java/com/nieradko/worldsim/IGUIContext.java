package com.nieradko.worldsim;

import com.nieradko.worldsim.core.Organism;
import com.nieradko.worldsim.core.Position;

public interface IGUIContext {
    void handleOrganismKilled(Organism organism);
    void handleOrganismAdded(Organism organism);
    void handleOrganismMoved(Organism organism, Position to);
}
