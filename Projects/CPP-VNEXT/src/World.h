#pragma once

#include <vector>
#include <unordered_set>
#include <random>
#include <optional>
#include "Organism.h"


class World {
public:
    World(int n, int m);
    void Draw();
    void SimulateRound();
    bool Move(Position& from, Position& to);
    void Add(Organism* organism);
    void Kill(Organism* organism);
    std::optional<Position> GetNearbyPosition(Position& source, int distance = 1, bool empty = false) const;
    static std::mt19937 GetRng();
private:
    bool IsPositionEmpty(Position& position) const;
    bool IsPositionWithinWorld(Position& position) const;
    std::vector<std::vector<Organism*>> map;
    std::vector<Organism*> organisms;
    std::unordered_set<Organism*> toErase;
};

