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
    Log("World", "================================");
    Log("World", "Simulating round " + std::to_string(this->round));
    Log("World", "================================");

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
}

void World::MoveOnMap(Position& from, Position& to) {
    this->map[to.GetX()][to.GetY()] = this->map[from.GetX()][from.GetY()];
    this->map[from.GetX()][from.GetY()] = nullptr;
}

bool World::Move(Position& from, Position& to) {
    if (!IsPositionWithinWorld(to)) {
        return false;
    }

    Organism* defender = this->map[to.GetX()][to.GetY()];

    if (defender == nullptr) {
        MoveOnMap(from, to);
        return true;
    }else {
        Organism* attacker = this->map[from.GetX()][from.GetY()];
        CollisionContext context = CollisionContext(*this, attacker, defender);
        defender->HandleCollision(context);

        switch (context.GetResult()) {
            case Cancelled:
                return false;
            case AttackerWon:
                if (dynamic_cast<Plant*>(defender) != nullptr) {
                    Log("Eating", attacker->GetType() + " has eaten " + defender->GetType() + " at " + to.ToString(), 178);
                }else {
                    Log("Fight", attacker->GetType() + " has defeated " + defender->GetType() + " while attacking " + to.ToString(), 160);
                }
                this->Kill(defender);
                MoveOnMap(from, to);
                return true;
            case DefenderWon:
                if (dynamic_cast<Plant*>(defender) != nullptr) {
                    Log("Eating" , attacker->GetType() + " has killed by eating " + defender->GetType() + " at " + to.ToString(), 201);
                }else {
                    Log("Fight", defender->GetType() + " has defeated " + attacker->GetType() + " while defending at " + to.ToString(), 1);
                }
                this->Kill(attacker);
                return false;
            case DefenderEvaded:
                Log("Fight", defender->GetType() + " has evaded " + attacker->GetType() + " at " + to.ToString(), 2);
                MoveOnMap(from, to);
                return true;
            case BothDied:
                if (dynamic_cast<Plant*>(defender) != nullptr) {
                    Log("Eating" , attacker->GetType() + " has been killed by eating " + defender->GetType() + " at " + to.ToString(), 201);
                }else {
                    Log("Fight", defender->GetType() + " has died alongside " + attacker->GetType() + " while fighting at " + to.ToString(), 1);
                }
                this->Kill(defender);
                this->Kill(attacker);
                return false;
        }

        throw std::exception("Should never happen");
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
            std::string symbol = organism == nullptr ? " " : organism->GetSymbol();
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

    std::cout << "Legend:" << std::endl;
    std::cout << "Movement: w,s,a,d" << std::endl;
    std::cout << "Wait: <spacebar>" << std::endl;
    std::cout << "Special Power: j" << std::endl;
    std::cout << "End (human): ESC" << std::endl;
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

std::vector<Organism*> World::GetOrganismsAtNearbyPositions(Position& source, int distance) {
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

    return results;
}

void World::Stop() {
    this->running = false;
    Log("World", "Human died. Game over.", 9);
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
                if (pos.Equals(existingPos)) {
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

void World::Log(std::string&& source, std::string&& info, int color) {
    this->logs.push_back("[" + std::move(source) + "] \033[38;5;" + std::to_string(color) + "m" + std::move(info) + "\033[m");
    while(this->logs.size() > this->map[0].size()) {
        this->logs.pop_front();
    }
}
