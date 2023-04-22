#include "Human.h"

Human::Human(Position&& initialPosition) : Animal(5, 4, 'H', std::move(initialPosition)) {}

const std::string Human::Type = "Human";

void Human::HandleAction(World& world) {
    while(true) {
        switch((char)getchar()) {
            case 'w':
                world.Move(this->position, this->position.WithOffset(1, 0));
                return;
            case 's':
                world.Move(this->position, this->position.WithOffset(-1, 0));
                return;
            case 'a':
                world.Move(this->position, this->position.WithOffset(0, -1));
                return;
            case 'd':
                world.Move(this->position, this->position.WithOffset(0, 1));
                return;
        }
    }
}

void Human::HandleCollision(World& world, Organism* attacker) {
    // TODO Implement
}

std::string Human::GetType() const {
    return Human::Type;
}

Organism* Human::GetNewOfType(Position&& position) {
    return new Human(std::move(position));
}


