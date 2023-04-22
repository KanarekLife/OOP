#include "Guarana.h"
#include "../World.h"

Guarana::Guarana(Position&& position) : Plant(0, 'G', std::move(position)) {}

const std::string Guarana::Type = "Guarana";

std::string Guarana::GetType() const {
    return Guarana::Type;
}

Organism* Guarana::GetNewOfType(Position&& position) {
    return new Guarana(std::move(position));
}

void Guarana::HandleCollision(CollisionContext& context) {
    context.GetAttacker()->IncreaseStrength(3);
    context.KillHost();
}