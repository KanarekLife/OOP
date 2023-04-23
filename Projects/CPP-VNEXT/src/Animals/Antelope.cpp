#include "Antelope.h"
#include "../World.h"

Antelope::Antelope(Position&& position) : Animal(4, 4, "\033[38;5;219mA\033[m", std::move(position)) {

}

const std::string Antelope::Type = "Antelope";

std::string Antelope::GetType() const {
    return Antelope::Type;
}

Organism* Antelope::GetNewOfType(Position&& position) {
    return new Antelope(std::move(position));
}

void Antelope::HandleAction(World& world) {
    std::optional<Position> newPosition = world.GetNearbyPosition(this->GetPosition(), 2);
    if (newPosition && world.Move(this->GetPosition(), *newPosition)) {
        this->SetPosition(std::move(*newPosition));
    }
}

void Antelope::HandleCollision(CollisionContext& collisionContext) {
    static std::uniform_real_distribution<double> dist = std::uniform_real_distribution<double>(0, 1);
    static std::mt19937 rng = World::GetRng();

    Animal::HandleCollision(collisionContext);

    if (collisionContext.IsCancelled()) {
        return;
    }

    if (dist(rng) <= 0.5) {
        std::optional<Position> newPosition = collisionContext.GetWorld().GetNearbyPosition(this->GetPosition(), 1, true);
        if (newPosition && collisionContext.GetWorld().Move(this->GetPosition(), *newPosition)) {
            this->SetPosition(std::move(*newPosition));
            collisionContext.DefenderEvaded();
        }
        return;
    }
}
