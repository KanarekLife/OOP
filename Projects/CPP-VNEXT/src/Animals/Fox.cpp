#include <optional>
#include "Fox.h"
#include "../World.h"

Fox::Fox(Position&& position) : Animal(3, 7, 'F', std::move(position)) {

}

const std::string Fox::Type = "Fox";

std::string Fox::GetType() const {
    return Fox::Type;
}

Organism* Fox::GetNewOfType(Position&& position) {
    return new Fox(std::move(position));
}

void Fox::HandleAction(World& world) {
    std::optional<Position> newPosition = world.GetNearbyPosition(this->GetPosition(), 1, true);
    if (newPosition && world.Move(this->GetPosition(), *newPosition)) {
        this->SetPosition(std::move(*newPosition));
    }
}
