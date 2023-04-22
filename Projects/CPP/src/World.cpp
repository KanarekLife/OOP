#include "World.h"

World::World(int n, int m): numberOfRounds(0) {
    std::random_device rd = std::random_device();
    this->rng = std::default_random_engine(rd());

    this->map = std::vector<std::vector<Organism*>>(n);
    for (int i = 0; i < n; ++i) {
        this->map[i] = std::vector<Organism*>(m);
    }

    this->organisms = std::vector<Organism*>();
}

void World::Move(Position& from, Position& to) {
    Organism* currentFieldOwner = this->map[to.GetX()][to.GetY()];
    if (currentFieldOwner == nullptr) {
        this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
        this->map[from.GetX()][from.GetY()] = nullptr;
        return;
    }else {
        // TODO Verify
        currentFieldOwner->HandleCollision(this, this->map[from.GetX()][from.GetY()]);
    }
}

void World::Move(Position& from, Position&& to) {
    Position pos = to;
    this->Move(from, to);
}

void World::AddAt(Organism* organism, Position& position) {
    if (!this->IsPositionEmpty(position)) {
        throw std::exception("Field is not empty");
    }

    this->map[position.GetX()][position.GetY()] = organism;

    this->organisms.push_back(organism);
}

bool World::IsPositionWithinWorld(Position& position) const {
    if (position.GetX() < 0 || position.GetX() >= this->map.size()) {
        return false;
    }

    if (position.GetY() < 0 || position.GetY() >= this->map[0].size()) {
        return false;
    }

    return true;
}

bool World::IsPositionEmpty(Position& position) const {
    return this->map[position.GetX()][position.GetY()] == nullptr;
}


std::mt19937 World::GetRng() const {
    return this->rng;
}

void World::SimulateRound() {
    std::sort(this->organisms.begin(), this->organisms.end(), Organism::Compare);

    for(const Organism* organism : this->organisms) {
        organism->HandleAction();
    }
}

Position World::GetNearbyPosition(Position& position, bool empty, int distance) const {
    std::vector<Position> possiblePositions = {
            {position.GetX(), position.GetY() + 1},
            {position.GetX() + 1, position.GetY() + 1},
            {position.GetX() + 1, position.GetY()},
            {position.GetX() + 1, position.GetY() - 1},
            {position.GetX(), position.GetY() - 1},
            {position.GetX() - 1, position.GetY() - 1},
            {position.GetX() - 1, position.GetY()},
            {position.GetX() - 1, position.GetY() + 1}
    };
    std::shuffle(possiblePositions.begin(), possiblePositions.end(), GetRng());
    for (Position pos : possiblePositions) {
        if (IsPositionWithinWorld(pos) && (empty || IsPositionEmpty(pos))) {
            return pos;
        }
    }
}
