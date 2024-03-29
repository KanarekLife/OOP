#include "Organism.h"

void Organism::MakeOlder() {
    this->age++;
}

bool Organism::Order(Organism* lhs, Organism* rhs) {
    if (lhs->initiative < rhs->initiative) {
        return true;
    }else if (lhs->initiative > rhs->initiative) {
        return false;
    }else {
        return lhs->age > rhs->age;
    }
}

Organism::Organism(int strength, int initiative, std::string symbol, Position&& initialPosition): strength(strength), initiative(initiative), symbol(symbol),
                                                                                           position(std::move(initialPosition)), age(0) {

}

Position& Organism::GetPosition() {
    return this->position;
}

void Organism::SetPosition(Position&& newPosition) {
    this->position = std::move(newPosition);
}

std::string Organism::GetSymbol() const {
    return this->symbol;
}

int Organism::GetStrength() const {
    return this->strength;
}

void Organism::IncreaseStrength(int delta) {
    if (delta < 1) {
        throw std::exception("Delta cannot be under 1");
    }

    this->strength += delta;
}
