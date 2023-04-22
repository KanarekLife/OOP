#pragma once

#include "Organism.h"

class Animal : protected Organism {
public:
    Animal(int strength, int initiative, char symbol, Position&& initialPosition);
    void HandleAction(World& world) override;
    void HandleCollision(World& world, Organism* attacker) override;
private:
};
