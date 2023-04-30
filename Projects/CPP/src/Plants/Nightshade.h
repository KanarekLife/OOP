#pragma once

#include "Plant.h"

class Nightshade : public Plant {
public:
    Nightshade(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism * GetNewOfType(Position &&position) override;
    void HandleCollision(CollisionContext &context) override;
};
