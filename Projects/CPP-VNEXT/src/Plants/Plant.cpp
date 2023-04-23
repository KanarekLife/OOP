#include "Plant.h"
#include "../World.h"
#include <random>

Plant::Plant(int strength, std::string symbol, Position&& initialPosition) : Organism(strength, 0, symbol, std::move(initialPosition)) {

}

void Plant::HandleAction(World& world) {
    static std::uniform_real_distribution<double> dist = std::uniform_real_distribution<double>(0, 1);
    static std::mt19937 rng = World::GetRng();

    if (dist(rng) < 0.45) {
        std::optional<Position> position = world.GetNearbyPosition(this->GetPosition(), 1, true);
        if (position) {
            world.Log("Seeding", this->GetType() + " has appeared at " + (*position).ToString());
            world.Add(this->GetNewOfType(std::move(*position)));
        }
    }
}

void Plant::HandleCollision(CollisionContext& context) {}

