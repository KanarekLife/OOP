#pragma once

#include "Animal.h"

class Sheep : public Animal {
public:
    explicit Sheep(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism* GetNewOfType(Position&& position) override;
};
