#pragma once

#include "../Animal.h"

class Wolf : Animal {
public:
    explicit Wolf(Position&& initialPosition);
    static const std::string Type;
    std::string GetType() const override;
    Organism* GetNewOfType(Position&& position) override;
private:
};
