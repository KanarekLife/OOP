package com.nieradko.worldsim.core;

import com.nieradko.worldsim.core.animals.Animal;

import java.util.Optional;

public interface ICollisionContext {
    Animal getAttacker();
    Organism getDefender();
    void cancel();
    void defenderHasEvaded();
    void defenderHasDied();
    void attackerHasDied();
    boolean isResolved();
    CollisionResult getResult();
}
