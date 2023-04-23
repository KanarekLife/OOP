#pragma once

#include "Organism.h"
#include <vector>

class World;
class Organism;

enum CollisionResult {
    AttackerWon,
    DefenderWon,
    BothDied,
    DefenderEvaded,
    Cancelled
};

class CollisionContext {
public:
    CollisionContext(World& world, Organism* attacker, Organism* defender);

    void Cancel();
    bool IsCancelled() const;

    void KillAttacker();
    void KillHost();
    void DefenderHasEvaded();

    CollisionResult GetResult() const;

    World& GetWorld() const;
    Organism* GetAttacker();
private:
    bool cancelled;
    bool isAttackerKilled;
    bool isHostKilled;
    bool hasDefenderEvaded;

    Organism* attacker;
    Organism* defender;

    World& world;
};
