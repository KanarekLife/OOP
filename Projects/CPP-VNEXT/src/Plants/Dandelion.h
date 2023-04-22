#pragma once

#include "../Plant.h"

class Dandelion : public Plant {
public:
    explicit Dandelion(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism * GetNewOfType(Position &&position) override;
    void HandleAction(World &world) override;
};
