package com.nieradko.worldsim.core;

import com.nieradko.worldsim.core.animals.Animal;

public interface ICollisionContext extends IWorldContext {
    Animal getAttacker();
    Organism getDefender();
    void cancel();
    void defenderHasEvaded();
    void defenderHasDied();
    void attackerHasDied();
    boolean isResolved();
}
