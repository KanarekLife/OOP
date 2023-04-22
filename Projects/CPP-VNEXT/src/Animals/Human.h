#pragma once

#include <optional>
#include "Animal.h"

class Human : public Animal {
public:
    explicit Human(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism * GetNewOfType(Position &&position) override;
    void HandleAction(World &world) override;
    void HandleCollision(CollisionContext &collisionContext) override;
    bool IsSpecialPowerActive() const;
    int GetSpecialPowerTimer() const;
private:
    bool TryToMove(World& world, Position&& newPosition);
    void SpecialPowerTick();
    bool TryToActivateSpecialPower();
    bool isSpecialPowerActive;
    int specialPowerTimer;
};
