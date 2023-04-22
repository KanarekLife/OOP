#pragma once

#include "Organism.h"

class Animal : public Organism {
public:
    void HandleAction(World& world) override;
    void HandleCollision(CollisionContext& collisionContext) override;
protected:
    Animal(int strength, int initiative, char symbol, Position&& initialPosition);
};
