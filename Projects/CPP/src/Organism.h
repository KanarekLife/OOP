#pragma once

#include "Position.h"
#include "CollisionContext.h"
#include <string>

class World;
class CollisionContext;

class Organism {
public:
    void MakeOlder();
    std::string GetSymbol() const;
    virtual void HandleAction(World& world) = 0;
    virtual void HandleCollision(CollisionContext& context) = 0;
    virtual std::string GetType() const = 0;
    virtual Organism* GetNewOfType(Position&& position) = 0;
    Position& GetPosition();
    int GetStrength() const;
    void IncreaseStrength(int delta);
    static bool Order(Organism* lhs, Organism* rhs);
protected:
    Organism(int strength, int initiative, std::string symbol, Position&& initialPosition);
    void SetPosition(Position&& newPosition);
private:
    int strength;
    int initiative;
    int age;
    std::string symbol;
    Position position;
};
