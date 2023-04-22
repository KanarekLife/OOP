#include "Wolf.h"

Wolf::Wolf(Position&& initialPosition) : Animal(9, 4, 'W', std::move(initialPosition)) {}

const std::string Wolf::Type = "Wolf";

std::string Wolf::GetType() const {
    return Wolf::Type;
}

Organism* Wolf::GetNewOfType(Position&& position) {
    return new Wolf(std::move(position));
}


