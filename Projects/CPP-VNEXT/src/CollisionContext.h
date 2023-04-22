#pragma once

#include "Organism.h"
#include <vector>

class World;
class Organism;

class CollisionContext {
public:
    CollisionContext(World& world, Organism* attacker);
    void Cancel();
    bool IsCancelled() const;
    void KillAttacker();
    bool IsAttackerKilled() const;
    void KillHost();
    bool IsHostKilled() const;
    World& GetWorld() const;
    Organism* GetAttacker();
private:
    Organism* attacker;
    World& world;
    bool cancelled;
    bool killAttacker;
    bool killHost;
};
