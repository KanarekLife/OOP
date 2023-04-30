#pragma once

#include "../Organism.h"

class Plant : public Organism {
public:
    void HandleAction(World &world) override;
    void HandleCollision(CollisionContext &context) override;
protected:
    Plant(int strength, std::string symbol, Position&& initialPosition);
};
