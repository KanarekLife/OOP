#pragma once

#include "Plant.h"

class PineBorscht : public Plant {
public:
    explicit PineBorscht(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism * GetNewOfType(Position &&position) override;
    void HandleAction(World &world) override;
    void HandleCollision(CollisionContext &context) override;
};
