#include "PineBorscht.h"
#include "../World.h"

PineBorscht::PineBorscht(Position&& position) : Plant(10, "\033[38;5;124m@\033[m", std::move(position)) {}

const std::string PineBorscht::Type = "PineBorscht";

std::string PineBorscht::GetType() const { return PineBorscht::Type; }

Organism* PineBorscht::GetNewOfType(Position&& position) { return new PineBorscht(std::move(position)); }

void PineBorscht::HandleAction(World& world) {
    int counter = 0;
    for (Organism* organism : world.GetOrganismsAtNearbyPositions(this->GetPosition())) {
        if (dynamic_cast<Animal*>(organism) != nullptr &&  organism->GetType() != "CyberSheep") {
            world.Kill(organism);
            counter++;
        }
    }
    if (counter > 0) {
        world.Log("PineBorscht", "PineBorscht has killed " + std::to_string(counter) + " organisms at " + this->GetPosition().ToString(), 196);
    }
    Plant::HandleAction(world);
}

void PineBorscht::HandleCollision(CollisionContext& context) {
    context.KillHost();
    if (context.GetAttacker()->GetType() != "CyberSheep") {
        context.KillAttacker();
    }
}