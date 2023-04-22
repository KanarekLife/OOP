#include "PineBorscht.h"
#include "../World.h"

PineBorscht::PineBorscht(Position&& position) : Plant(10, 'Y', std::move(position)) {}

const std::string PineBorscht::Type = "PineBorscht";

std::string PineBorscht::GetType() const { return PineBorscht::Type; }

Organism* PineBorscht::GetNewOfType(Position&& position) { return new PineBorscht(std::move(position)); }

void PineBorscht::HandleAction(World& world) {
    std::vector<Organism*> x = world.GetOrganismsAtNearbyPositions(this->GetPosition());
    for (Organism* organism : x) {
        if (dynamic_cast<Animal*>(organism) != nullptr &&  organism->GetType() != "CyberSheep") { // TODO Fix when CyberSheep is implemented
            world.Kill(organism);
        }
    }
    world.Log("[PineBorscht] PineBorscht has killed " + std::to_string(x.size()) + " organisms at " + this->GetPosition().ToString());
    Plant::HandleAction(world);
}

void PineBorscht::HandleCollision(CollisionContext& context) {
    context.KillHost();
    if (context.GetAttacker()->GetType() != "CyberSheep") { // TODO Fix when CyberSheep is implemented
        context.KillAttacker();
    }
}