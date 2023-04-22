#pragma once

#include "../Animal.h"

class Turtle : public Animal {
public:
    explicit Turtle(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism * GetNewOfType(Position &&position) override;
    void HandleAction(World &world) override;
    void HandleCollision(CollisionContext &collisionContext) override;
};
