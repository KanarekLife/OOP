#include <iostream>
#include <Windows.h>
#include "World.h"
#include "Animals/Human.h"

int main() {
    SetConsoleOutputCP(CP_UTF8);
    SetConsoleCP(CP_UTF8);

    SetConsoleTitle("Stanisław Nieradko [193044]");

    int n, m;
    std::cin >> n >> m;

    World world(n, m);

    world.AddAt(dynamic_cast<Organism>(new Human(Position(0,0))), Position(0, 0));

    return 0;
}