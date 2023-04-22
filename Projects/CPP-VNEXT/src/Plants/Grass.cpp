#include "Grass.h"

Grass::Grass(Position&& position) : Plant(0, '^', std::move(position)) {

}

const std::string Grass::Type = "Grass";

std::string Grass::GetType() const {
    return Grass::Type;
}

Organism* Grass::GetNewOfType(Position&& position) {
    return new Grass(std::move(position));
}
