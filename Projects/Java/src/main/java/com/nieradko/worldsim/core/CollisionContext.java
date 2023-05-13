package com.nieradko.worldsim.core;

import com.nieradko.worldsim.core.animals.Animal;

import java.util.Optional;

public class CollisionContext implements ICollisionContext {
    private final Animal attacker;
    private final Organism defender;
    private boolean isCancelled = false;
    private boolean hasDefenderEvaded = false;
    private boolean hasDefenderDied = false;
    private boolean hasAttackerDied = false;

    public CollisionContext(Animal attacker, Organism defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public Animal getAttacker() {
        return attacker;
    }

    @Override
    public Organism getDefender() {
        return defender;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    @Override
    public void defenderHasEvaded() {
        hasDefenderEvaded = true;
    }

    @Override
    public void defenderHasDied() {
        hasDefenderDied = true;
    }

    @Override
    public void attackerHasDied() {
        hasAttackerDied = true;
    }

    @Override
    public boolean isResolved() {
        return isCancelled || hasDefenderEvaded || hasDefenderDied || hasAttackerDied;
    }

    @Override
    public CollisionResult getResult() {
        if (isCancelled) {
            return CollisionResult.Cancelled;
        }

        if (hasDefenderDied && !hasAttackerDied) {
            return CollisionResult.AttackerWon;
        }

        if (!hasDefenderDied && hasAttackerDied) {
            return CollisionResult.DefenderWon;
        }

        if (hasDefenderDied) {
            return CollisionResult.BothDied;
        }

        if (attacker.getStrength() >= defender.getStrength()) {
            return CollisionResult.AttackerWon;
        }else {
            return CollisionResult.DefenderWon;
        }
    }
}
