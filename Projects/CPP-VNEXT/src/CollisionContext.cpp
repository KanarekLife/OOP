#include "CollisionContext.h"

CollisionContext::CollisionContext(World& world, Organism* attacker): world(world), attacker(attacker),
    cancelled(false), killAttacker(false), killHost(false) {}

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

bool CollisionContext::IsAttackerKilled() const {
    return this->killAttacker;
}

bool CollisionContext::IsHostKilled() const {
    return this->killHost;
}

void CollisionContext::KillAttacker() {
    this->killAttacker = true;
}

void CollisionContext::KillHost() {
    this->killHost = true;
}
