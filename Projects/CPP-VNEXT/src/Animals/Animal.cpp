#include "Animal.h"
#include "../World.h"

Animal::Animal(int strength, int initiative, std::string symbol, Position&& initialPosition) : Organism(strength, initiative,
                                                                                                 symbol,
                                                                                                 std::move(initialPosition)) {

}

void Animal::HandleAction(World& world) {
    std::optional<Position> newPosition = world.GetNearbyPosition(this->GetPosition());
    if (newPosition && world.Move(this->GetPosition(), *newPosition)) {
        this->SetPosition(std::move(*newPosition));
    }
}

void Animal::HandleCollision(CollisionContext& collisionContext) {
    if (collisionContext.GetAttacker()->GetType() == this->GetType()) {
        std::optional<Position> pos = collisionContext.GetWorld().GetNearbyPosition(this->GetPosition(), 1, true);
        if (pos) {
            collisionContext.GetWorld().Log("Breeding", this->GetType() + " has appeared at " + (*pos).ToString());
            Organism* organism = this->GetNewOfType(std::move(*pos));
            collisionContext.GetWorld().Add(organism);
        }
        collisionContext.Cancel();
    }
}
