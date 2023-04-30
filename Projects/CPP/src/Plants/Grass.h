#pragma once

#include "Plant.h"

class Grass : public Plant {
public:
    explicit Grass(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism * GetNewOfType(Position &&position) override;
};
