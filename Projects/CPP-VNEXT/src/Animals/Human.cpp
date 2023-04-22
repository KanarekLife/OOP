#include <conio.h>
#include "Human.h"
#include "../World.h"

Human::Human(Position&& position) : Animal(5, 4, 'H', std::move(position)) {}

const std::string Human::Type = "Human";

std::string Human::GetType() const {
    return Human::Type;
}

Organism* Human::GetNewOfType(Position&& position) {
    return new Human(std::move(position));
}

void Human::HandleAction(World& world) {
    bool inputEntered = false;
    while(!inputEntered) {
        switch((char)_getch()) {
            case 'w':
                inputEntered = this->TryToMove(world, GetPosition().WithOffset(0, 1));
                break;
            case 's':
                inputEntered = this->TryToMove(world, GetPosition().WithOffset(0, -1));
                break;
            case 'd':
                inputEntered = this->TryToMove(world, GetPosition().WithOffset(1, 0));
                break;
            case 'a':
                inputEntered = this->TryToMove(world, GetPosition().WithOffset(-1, 0));
                break;
            default:
                continue;
        }
    }
}

void Human::HandleCollision(CollisionContext& collisionContext) {
    Animal::HandleCollision(collisionContext);
}

bool Human::TryToMove(World& world, Position&& newPosition) {
    Position pos = newPosition;
    if (world.Move(GetPosition(), pos)) {
        this->SetPosition(std::move(pos));
        return true;
    }
    return false;
}
