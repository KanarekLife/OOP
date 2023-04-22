#include "Sheep.h"

Sheep::Sheep(Position&& initialPosition) : Animal(4, 4, 'S', std::move(initialPosition)) {

}

const std::string Sheep::Type = "Sheep";

std::string Sheep::GetType() const {
    return Sheep::Type;
}

Organism* Sheep::GetNewOfType(Position&& position) {
    return new Sheep(std::move(position));
}


