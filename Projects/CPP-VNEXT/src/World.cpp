#include <iostream>
#include "World.h"
#include "Windows.h"
#include "Animals/Human.h"
#include "Plants/Plant.h"

World::World(int n, int m): running(true), round(0) {
    this->map = std::vector<std::vector<Organism*>>(n);
    for (int i = 0; i < n; ++i) {
        this->map[i] = std::vector<Organism*>(m);
    }

    this->organisms = std::vector<Organism*>();
    this->toErase = std::unordered_set<Organism*>();
    this->logs = std::list<std::string>();
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

    round++;
    Log("[World] Simulated round " + std::to_string(this->round));
}

bool World::Move(Position& from, Position& to) {
    if (!IsPositionWithinWorld(to)) {
        return false;
    }

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
            this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
            this->map[from.GetX()][from.GetY()] = nullptr;
            return true;
        }

        if (!context.IsHostKilled() && context.IsAttackerKilled()) {
            this->Kill(attacker);
            return false;
        }

        if (context.HasAttackerWon()) {
            this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
            this->map[from.GetX()][from.GetY()] = nullptr;
            return true;
        }

        if (!context.IsHostKilled() && !context.IsAttackerKilled()) {
            // Fight
            if (attacker->IsAtLeastAsStrongAs(host)) {
                if (dynamic_cast<Plant*>(host) != nullptr) {
                    Log("[Eating] " + attacker->GetType() + " has eaten " + host->GetType() + " at " + to.ToString());
                }else {
                    Log("[Fight] " + attacker->GetType() + " has defeated " + host->GetType() + " while attacking " + to.ToString());
                }
                this->Kill(host);
                this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
                this->map[from.GetX()][from.GetY()] = nullptr;
                return true;
            }else {
                if (dynamic_cast<Plant*>(host) != nullptr) {
                    Log("[Eating] " + attacker->GetType() + " has killed by eating " + host->GetType() + " at " + to.ToString());
                }else {
                    Log("[Fight] " + host->GetType() + " has defeated " + attacker->GetType() + " while defending at " + to.ToString());
                }
                this->Kill(attacker);
                return false;
            }
        }

        this->Kill(host);
        this->Kill(attacker);
        return false;
    }
}

void World::Add(Organism* organism) {
    if (!this->IsPositionEmpty(organism->GetPosition())) {
        throw std::exception("It shouldn't be possible to add organism to already taken field.");
    }

    if (organism->GetType() == Human::Type) {
        this->human = dynamic_cast<Human*>(organism);
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

    int n = this->map.size();
    int m = this->map[0].size();

    auto iterator = this->logs.begin();

    if (this->human->IsSpecialPowerActive()) {
        std::cout << "Special Power 'Całopalenie' is active for " << this->human->GetSpecialPowerTimer() << " rounds" << std::endl << std::endl;
    }else {
        if (this->human->GetSpecialPowerTimer() == 0) {
            std::cout << "Special Power 'Całopalenie' is ready" << std::endl << std::endl;
        }else {
            std::cout << "Special Power 'Całopalenie' is ready in " << this->human->GetSpecialPowerTimer() << " rounds" << std::endl << std::endl;
        }
    }

    for (int i = n - 1; i >= 0; --i) {
        for (int j = 0; j < m; ++j) {
            Organism* organism = this->map[j][i];
            char symbol = organism == nullptr ? ' ' : organism->GetSymbol();
            std::cout << symbol;
        }
        if (iterator != this->logs.end()) {
            std::cout << '\t' << *iterator << std::endl;
            iterator++;
        }else {
            std::cout << std::endl;
        }
    }

    while(iterator != this->logs.end()) {
        for (int i = 0; i < m; ++i) {
            std::cout << ' ';
        }
        std::cout << '\t' << *iterator << std::endl;
        iterator++;
    }
}

void World::Kill(Organism* organism) {
    if (organism->GetType() == Human::Type) {
        Stop();
        return;
    }
    this->map[organism->GetPosition().GetX()][organism->GetPosition().GetY()] = nullptr;
    this->organisms.erase(std::remove(this->organisms.begin(), this->organisms.end(), organism), this->organisms.end());
    delete organism;
    this->toErase.emplace(organism);
}

int World::GetNumberOfLivingOrganisms() const {
    return this->organisms.size();
}

std::vector<Organism*>&& World::GetOrganismsAtNearbyPositions(Position& source, int distance) {
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
    std::vector<Organism*> results = std::vector<Organism*>();
    results.reserve(8);

    for (Position pos : possiblePositions ) {
        if (!IsPositionWithinWorld(pos)) {
            continue;
        }

        Organism* host = this->map[pos.GetX()][pos.GetY()];
        if (host != nullptr) {
            results.emplace_back(host);
        }
    }

    return std::move(results);
}

void World::Stop() {
    this->running = false;
    Log("[World] Human died. Game over.");
}

bool World::IsRunning() const {
    return this->running;
}

std::vector<Position> World::GetRandomPointsWithinWorld(int numberOfPositions) const {
    int n = this->map.size();
    int m = this->map[0].size();

    std::mt19937 rng = World::GetRng();
    std::uniform_int_distribution<int> distX(0, n - 1);
    std::uniform_int_distribution<int> distY(0, m - 1);

    std::vector<Position> results = std::vector<Position>();
    results.reserve(n);

    for (int i = 0; i < numberOfPositions; ++i) {
        bool found = false;
        Position pos = Position(0, 0);
        while(!found) {
            pos = Position(distX(rng), distY(rng));
            bool noRepetitionsFound = true;
            for (const Position existingPos : results) {
                if (pos.GetX() == existingPos.GetX() && pos.GetY() == existingPos.GetY()) {
                    noRepetitionsFound = false;
                    break;
                }
            }
            found = noRepetitionsFound;
        }
        results.push_back(pos);
    }

    return results;
}

void World::Log(std::string&& info) {
    this->logs.push_back(std::move(info));
    while(this->logs.size() > 25) {
        this->logs.pop_front();
    }
}
