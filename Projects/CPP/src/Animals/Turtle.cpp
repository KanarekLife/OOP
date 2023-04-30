#include "Turtle.h"
#include "../World.h"

Turtle::Turtle(Position&& position) : Animal(2, 1, "\033[38;5;10mT\033[m", std::move(position)) {

}

const std::string Turtle::Type = "Turtle";

std::string Turtle::GetType() const {
    return Turtle::Type;
}

Organism* Turtle::GetNewOfType(Position&& position) {
    return new Turtle(std::move(position));
}

void Turtle::HandleAction(World& world) {
    static std::uniform_real_distribution<double> dist = std::uniform_real_distribution<double>(0, 1);
    static std::mt19937 rng = World::GetRng();

    if (dist(rng) > 0.75) {
        Animal::HandleAction(world);
    }
}

void Turtle::HandleCollision(CollisionContext& collisionContext) {
    Animal::HandleCollision(collisionContext);

    if (collisionContext.IsCancelled()) {
        return;
    }

    if (collisionContext.GetAttacker()->GetStrength() < 5) {
        collisionContext.GetWorld().Log("Turtle", "Turtle at " + this->GetPosition().ToString() + " has evaded the " + collisionContext.GetAttacker()->GetType() + "!", 11);
        collisionContext.Cancel();
        return;
    }

    collisionContext.KillHost();
}


