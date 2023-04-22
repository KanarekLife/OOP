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
    context.GetWorld().Log("[Guarana] Guarana has been eaten by " + context.GetAttacker()->GetType() + " which increased its strength to " + std::to_string(context.GetAttacker()->GetStrength()) + " at " + this->GetPosition().ToString());
    context.KillHost();
}