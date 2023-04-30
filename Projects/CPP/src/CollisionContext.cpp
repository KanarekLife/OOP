#include "CollisionContext.h"

CollisionContext::CollisionContext(World& world, Organism* attacker, Organism* defender): world(world), attacker(attacker), defender(defender),
                                                                      cancelled(false), isAttackerKilled(false), isHostKilled(false), hasDefenderEvaded(false) {}

void CollisionContext::Cancel() {
    this->cancelled = true;
}

World& CollisionContext::GetWorld() const {
    return this->world;
}

Organism* CollisionContext::GetAttacker() {
    return this->attacker;
}

bool CollisionContext::IsCancelled() const {
    return this->cancelled;
}

void CollisionContext::KillAttacker() {
    this->isAttackerKilled = true;
}

void CollisionContext::KillHost() {
    this->isHostKilled = true;
}

void CollisionContext::DefenderHasEvaded() {
    this->hasDefenderEvaded = true;
}

CollisionResult CollisionContext::GetResult() const {
    if (cancelled) {
        return Cancelled;
    }

    if (this->isHostKilled && !this->isAttackerKilled) {
        return AttackerWon;
    }

    if (!this->isHostKilled && this->isAttackerKilled) {
        return DefenderWon;
    }

    if (this->hasDefenderEvaded) {
        return DefenderEvaded;
    }

    if (!this->isHostKilled) {
        if (attacker->GetStrength() >= defender->GetStrength()) {
            return AttackerWon;
        }else {
            return DefenderWon;
        }
    }

    return BothDied;
}
