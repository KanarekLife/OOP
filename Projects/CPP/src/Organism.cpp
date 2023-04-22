#include "Organism.h"

Organism::Organism(int strength, int initiative, char symbol, Position&& initialPosition): strength(strength), initiative(initiative), symbol(symbol), age(0),
                                                                                           position(initialPosition) {}

int Organism::GetStrength() const {
    return this->strength;
}

int Organism::GetInitiative() const {
    return this->initiative;
}

void Organism::MakeOlder() {
    this->age++;
}

bool Organism::Compare(Organism* lhs, Organism* rhs) {
    if (lhs->initiative < rhs->initiative) {
        return true;
    }else if (lhs->initiative > rhs->initiative) {
        return false;
    }else {
        return lhs->age > rhs->age;
    }
}
