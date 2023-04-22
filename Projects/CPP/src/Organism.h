#pragma once

#include "World.h"

class Organism {
public:
    Organism(int strength, int initiative, char symbol, Position&& initialPosition);
    int GetStrength() const;
    int GetInitiative() const;
    void MakeOlder();
    virtual void HandleAction(World* world) = 0;
    virtual void HandleCollision(World* world, Organism* attacker) = 0;
    virtual std::string GetType() const = 0;
    virtual Organism* GetNewOfType(Position&& position) = 0;
    static bool Compare(Organism* lhs, Organism* rhs);
protected:
    Position position;
private:
    int strength;
    int initiative;
    int age;
    char symbol;
};
