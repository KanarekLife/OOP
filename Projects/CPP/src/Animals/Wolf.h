#pragma once

#include "Animal.h"

class Wolf : public Animal{
public:
    explicit Wolf(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism* GetNewOfType(Position&& position) override;
};
