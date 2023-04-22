#include "Animal.h"
#include "Position.h"


Animal::Animal(int strength, int initiative, char symbol, Position&& initialPosition) : Organism(strength, initiative, symbol, std::move(initialPosition)) {}

void Animal::HandleAction(World& world) {
    Position pos = this->position.GetNearbyPosition(world);
    world.Move(this->position, pos);
}

void Animal::HandleCollision(World& world, Organism* attacker) {
    if (attacker->GetType() == this->GetType()) {
        Position pos = this->position.GetNearbyPosition(world, true);
        Organism* organism = this->GetNewOfType();
        world.AddAt(organism, pos);
    }
}
