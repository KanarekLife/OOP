#pragma once

#include <vector>
#include <unordered_set>
#include <random>
#include <optional>
#include "Organism.h"
#include "Animals/Human.h"


class World {
public:
    World(int n, int m);
    void Draw();
    void SimulateRound();
    bool Move(Position& from, Position& to);
    void Add(Organism* organism);
    void Kill(Organism* organism);
    int GetNumberOfLivingOrganisms() const;
    std::vector<Organism*>&& GetOrganismsAtNearbyPositions(Position& position, int distance = 1);
    std::optional<Position> GetNearbyPosition(Position& source, int distance = 1, bool empty = false) const;
    std::vector<Position> GetRandomPointsWithinWorld(int numberOfPositions) const;
    static std::mt19937 GetRng();
    bool IsRunning() const;
    void Stop();
    void Log(std::string&& info);
private:
    bool IsPositionEmpty(Position& position) const;
    bool IsPositionWithinWorld(Position& position) const;
    std::vector<std::vector<Organism*>> map;
    std::vector<Organism*> organisms;
    Human* human;
    std::unordered_set<Organism*> toErase;
    std::list<std::string> logs;
    int round;
    bool running;
};

