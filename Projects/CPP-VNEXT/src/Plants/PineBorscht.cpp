#include "PineBorscht.h"
#include "../World.h"
#include "../Animal.h"

PineBorscht::PineBorscht(Position&& position) : Plant(10, 'Y', std::move(position)) {}

const std::string PineBorscht::Type = "PineBorscht";

std::string PineBorscht::GetType() const { return PineBorscht::Type; }

Organism* PineBorscht::GetNewOfType(Position&& position) { return new PineBorscht(std::move(position)); }

void PineBorscht::HandleAction(World& world) {
    for (Organism* organism : world.GetOrganismsAtNearbyPositions(this->GetPosition())) {
        if (dynamic_cast<Animal*>(organism) != nullptr &&  organism->GetType() != "CyberSheep") { // TODO Fix when CyberSheep is implemented
            world.Kill(organism);
        }
    }
    Plant::HandleAction(world);
}

void PineBorscht::HandleCollision(CollisionContext& context) {
    context.KillHost();
    if (context.GetAttacker()->GetType() != "CyberSheep") { // TODO Fix when CyberSheep is implemented
        context.KillAttacker();
    }
}