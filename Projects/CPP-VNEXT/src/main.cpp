#include <iostream>
#include <Windows.h>
#include "World.h"
#include "Animals/Wolf.h"
#include "Animals/Sheep.h"
#include "Animals/Fox.h"
#include "Animals/Turtle.h"

int main() {
    SetConsoleOutputCP(CP_UTF8);
    SetConsoleCP(CP_UTF8);

    SetConsoleTitle("Stanisław Nieradko [193044]");

    //int n, m;
    //std::cin >> n >> m;

    //World world(n, m);

    World world(20, 20);

    world.Add(new Sheep(Position(0,0)));
    world.Add(new Turtle(Position(0, 1)));

    world.Draw();

    while(true) {
        getchar();
        world.SimulateRound();
        world.Draw();
    }

    return 0;
}
