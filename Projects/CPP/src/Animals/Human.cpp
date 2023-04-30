#include <conio.h>
#include "Human.h"
#include "../World.h"

Human::Human(Position&& position) : Animal(5, 4, "\033[38;5;226mH\033[m", std::move(position)) {
    this->specialPowerTimer = 0;
    this->isSpecialPowerActive = false;
}

const std::string Human::Type = "Human";

std::string Human::GetType() const {
    return Human::Type;
}

Organism* Human::GetNewOfType(Position&& position) {
    return new Human(std::move(position));
}

void Human::HandleAction(World& world) {
    this->SpecialPowerTick();
    world.Draw();

    bool inputEntered = false;
    while(!inputEntered && world.IsRunning()) {
        char c = (char)_getch();
        switch(c) {
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
            case 'j':
                TryToActivateSpecialPower();
                world.Draw();
                break;
            case ' ':
                inputEntered = true;
                break;
            case 27: // ESC
                world.Stop();
                inputEntered = true;
                break;
            default:
                continue;
        }
    }
}

void Human::HandleCollision(CollisionContext& collisionContext) {
    if (this->isSpecialPowerActive) {
        World& world = collisionContext.GetWorld();
        int counter = 0;
        for (Organism* organism : world.GetOrganismsAtNearbyPositions(GetPosition())) {
            world.Kill(organism);
            counter++;
        }
        collisionContext.Cancel();
        world.Log("Całopalenie", "Human has killed " + std::to_string(counter) + " organisms with the usage of his special power", 196);
    }
}

bool Human::TryToMove(World& world, Position&& newPosition) {
    Position pos = newPosition;
    if (world.Move(GetPosition(), pos)) {
        this->SetPosition(std::move(pos));
        return true;
    }
    return false;
}

bool Human::TryToActivateSpecialPower() {
    if (!this->isSpecialPowerActive && this->specialPowerTimer == 0) {
        this->isSpecialPowerActive = true;
        this->specialPowerTimer = 5;
        return true;
    }
    return false;
}

bool Human::IsSpecialPowerActive() const {
    return this->isSpecialPowerActive;
}

void Human::SpecialPowerTick() {
    this->specialPowerTimer = std::max(0, this->specialPowerTimer - 1);

    if (this->specialPowerTimer == 0 && this->isSpecialPowerActive) {
        this->isSpecialPowerActive = false;
        this->specialPowerTimer = 5;
        return;
    }
}

int Human::GetSpecialPowerTimer() const {
    return this->specialPowerTimer;
}
