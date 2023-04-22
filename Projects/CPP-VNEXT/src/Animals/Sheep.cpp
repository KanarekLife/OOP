#include "Sheep.h"

Sheep::Sheep(Position&& position) : Animal(4, 4, 'S', std::move(position)) {

}

const std::string Sheep::Type = "Sheep";

std::string Sheep::GetType() const {
    return Sheep::Type;
}

Organism* Sheep::GetNewOfType(Position&& position) {
    return new Sheep(std::move(position));
}
