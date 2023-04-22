#pragma once

#include "../Animal.h"

class Sheep : Animal {
public:
    explicit Sheep(Position&& initialPosition);
    static const std::string Type;
    std::string GetType() const override;
    Organism* GetNewOfType(Position&& position) override;
};
