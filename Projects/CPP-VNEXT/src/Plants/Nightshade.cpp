#include "Nightshade.h"
#include "../World.h"

Nightshade::Nightshade(Position&& position) : Plant(99, 'X', std::move(position)) {}

const std::string Nightshade::Type = "Nightshade";

std::string Nightshade::GetType() const {
    return Nightshade::Type;
}

Organism* Nightshade::GetNewOfType(Position&& position) {
    return new Nightshade(std::move(position));
}

void Nightshade::HandleCollision(CollisionContext& context) {
    context.KillHost();
    context.KillAttacker();
}