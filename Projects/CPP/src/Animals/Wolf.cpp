#include "Wolf.h"

Wolf::Wolf(Position&& position) : Animal(9, 5, "\033[38;5;246mW\033[m", std::move(position)) {}

const std::string Wolf::Type = "Wolf";

std::string Wolf::GetType() const {
    return Wolf::Type;
}

Organism* Wolf::GetNewOfType(Position&& position) {
    return new Wolf(std::move(position));
}
