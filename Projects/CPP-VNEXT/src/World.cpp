#include <iostream>
#include "World.h"
#include "Windows.h"

World::World(int n, int m) {
    this->map = std::vector<std::vector<Organism*>>(n);
    for (int i = 0; i < n; ++i) {
        this->map[i] = std::vector<Organism*>(m);
    }

    this->organisms = std::vector<Organism*>();
    this->toErase = std::unordered_set<Organism*>();
}


void World::SimulateRound() {
    std::sort(this->organisms.begin(), this->organisms.end(), Organism::Order);

    std::vector<Organism*> organismsCopy = this->organisms;

    for(Organism* organism : organismsCopy) {
        if (this->toErase.find(organism) == this->toErase.end()) {
            organism->HandleAction(*this);
        }
    }

    for(Organism* organism : this->organisms) {
        organism->MakeOlder();
    }

    if (!this->toErase.empty()) {
        this->toErase.erase(this->toErase.begin(), this->toErase.end());
    }
}

bool World::Move(Position& from, Position& to) {
    Organism* host = this->map[to.GetX()][to.GetY()];
    if (host == nullptr) {
        this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
        this->map[from.GetX()][from.GetY()] = nullptr;
        return true;
    }else {
        Organism* attacker = this->map[from.GetX()][from.GetY()];

        CollisionContext context = CollisionContext(*this, attacker);
        host->HandleCollision(context);

        if (context.IsCancelled()) {
            return false;
        }

        if (context.IsHostKilled() && !context.IsAttackerKilled()) {
            this->Kill(host);
            return true;
        }

        if (!context.IsHostKilled() && context.IsAttackerKilled()) {
            this->Kill(attacker);
            return false;
        }

        if (!context.IsHostKilled() && !context.IsAttackerKilled()) {
            // Fight
            if (attacker->IsAtLeastAsStrongAs(host)) {
                this->Kill(host);
                this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
                this->map[from.GetX()][from.GetY()] = nullptr;
                return true;
            }else {
                this->Kill(attacker);
                return false;
            }
        }

        return false;
    }
}

void World::Add(Organism* organism) {
    if (!this->IsPositionEmpty(organism->GetPosition())) {
        throw std::exception("It shouldn't be possible to add organism to already taken field.");
    }

    this->map[organism->GetPosition().GetX()][organism->GetPosition().GetY()] = organism;
    this->organisms.push_back(organism);
}

std::optional<Position> World::GetNearbyPosition(Position& source, int distance, bool empty) const {
    std::vector<Position> possiblePositions = {
            {source.GetX(), source.GetY() + distance},
            {source.GetX() + distance, source.GetY() + distance},
            {source.GetX() + distance, source.GetY()},
            {source.GetX() + distance, source.GetY() - distance},
            {source.GetX(), source.GetY() - distance},
            {source.GetX() - distance, source.GetY() - distance},
            {source.GetX() - distance, source.GetY()},
            {source.GetX() - distance, source.GetY() + distance}
    };

    std::shuffle(possiblePositions.begin(), possiblePositions.end(), GetRng());

    for (Position pos : possiblePositions) {
        if (IsPositionWithinWorld(pos) && ((empty && IsPositionEmpty(pos)) || !empty)) {
            return { pos };
        }
    }
    return std::nullopt;
}

bool World::IsPositionEmpty(Position& position) const {
    return this->map[position.GetX()][position.GetY()] == nullptr;
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

std::mt19937 World::GetRng() {
    std::random_device rd;
    return std::default_random_engine(rd());
}

void World::Draw() {
    system("cls");
    SetCursorPos(0, 0);
    for (const std::vector<Organism*>& column : this->map) {
        for (Organism* organism : column) {
            char symbol = organism == nullptr ? ' ' : organism->GetSymbol();

            std::cout << symbol;
        }
        std::cout << std::endl;
    }
}

void World::Kill(Organism* organism) {
    this->map[organism->GetPosition().GetX()][organism->GetPosition().GetY()] = nullptr;
    this->organisms.erase(std::remove(this->organisms.begin(), this->organisms.end(), organism), this->organisms.end());
    delete organism;
    this->toErase.emplace(organism);
}

