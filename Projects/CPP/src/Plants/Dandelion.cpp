#include "Dandelion.h"
#include "../World.h"

Dandelion::Dandelion(Position&& position) : Plant(0, "\033[38;5;11m*\033[m", std::move(position)) {}

const std::string Dandelion::Type = "Dandelion";

std::string Dandelion::GetType() const {
    return Dandelion::Type;
}

Organism* Dandelion::GetNewOfType(Position&& position) {
    return new Dandelion(std::move(position));
}

void Dandelion::HandleAction(World& world) {
    int originalNumberOfOrganisms = world.GetNumberOfLivingOrganisms();
    for (int i = 0; i < 3; ++i) {
        Plant::HandleAction(world);
        if (world.GetNumberOfLivingOrganisms() > originalNumberOfOrganisms) {
            break;
        }
    }
}