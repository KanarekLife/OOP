#pragma once

#include "../Animal.h"

class Fox : public Animal {
public:
    explicit Fox(Position&& position);
    static const std::string Type;
    std::string GetType() const override;
    Organism* GetNewOfType(Position&& position) override;
    void HandleAction(World& world) override;
};
