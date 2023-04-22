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

Organism::Organism(int strength, int initiative, char symbol, Position&& initialPosition): strength(strength), initiative(initiative), symbol(symbol),
                                                                                           position(std::move(initialPosition)), age(0) {

}

Position& Organism::GetPosition() {
    return this->position;
}

void Organism::SetPosition(Position&& newPosition) {
    this->position = std::move(newPosition);
}

char Organism::GetSymbol() const {
    return this->symbol;
}

bool Organism::IsAtLeastAsStrongAs(Organism* organism) const {
    return this->strength >= organism->strength;
}

int Organism::GetStrength() const {
    return this->strength;
}
