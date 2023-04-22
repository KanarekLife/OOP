#include <iostream>
#include <Windows.h>
#include "World.h"
#include "Animals/Wolf.h"
#include "Animals/Sheep.h"
#include "Animals/Fox.h"
#include "Animals/Turtle.h"
#include "Animals/Antelope.h"
#include "Plants/Grass.h"
#include "Plants/Dandelion.h"
#include "Plants/Guarana.h"
#include "Plants/Nightshade.h"
#include "Plants/PineBorscht.h"
#include "Animals/Human.h"

int main() {
    SetConsoleOutputCP(CP_UTF8);
    SetConsoleCP(CP_UTF8);

    SetConsoleTitle("Stanisław Nieradko [193044]");

    //int n, m;
    //std::cin >> n >> m;

    //World world(n, m);

    World world(20, 20);

    world.Add(new Human(Position(0,0)));
    world.Add(new Grass(Position(19, 19)));
    //world.Add(new Antelope(Position(0, 1)));
    //world.Add(new PineBorscht(Position(1, 1)));

    world.Draw();

    while(true) {
        world.SimulateRound();
        world.Draw();
    }

    return 0;
}
