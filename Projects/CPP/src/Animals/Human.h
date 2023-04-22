#pragma once

#include "../Animal.h"

class Human : Animal {
public:
    explicit Human(Position&& initialPosition);
    static const std::string Type;
    void HandleAction(World& world) override;
    void HandleCollision(World& world, Organism* attacker) override;
    std::string GetType() const override;
    Organism* GetNewOfType(Position&& position) override;
private:
};
