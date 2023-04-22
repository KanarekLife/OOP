#pragma once

#include <vector>
#include <algorithm>
#include <random>

#include "Organism.h"
#include "Position.h"

class World {
public:
    World(int n, int m);
    void SimulateRound();
    void Move(Position& from, Position& to);
    void Move(Position& from, Position&& to);
    void AddAt(Organism* organism, Position& position);
    bool IsPositionWithinWorld(Position& position) const;
    bool IsPositionEmpty(Position& position) const;
    Position GetNearbyPosition(Position& position, bool empty = false, int distance = 1) const;
    std::mt19937 GetRng() const;
private:
    int numberOfRounds;
    std::vector<Organism*> organisms;
    std::vector<std::vector<Organism*>> map;
    std::mt19937 rng;
};
