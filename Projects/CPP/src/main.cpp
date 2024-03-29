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

    // Allow conhost to use colors
    HANDLE handleOut = GetStdHandle(STD_OUTPUT_HANDLE);
    DWORD consoleMode;
    GetConsoleMode( handleOut , &consoleMode);
    consoleMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
    consoleMode |= DISABLE_NEWLINE_AUTO_RETURN;
    SetConsoleMode( handleOut , consoleMode);

    int n, m;
    std::cout << "Enter dimensions of the world (ex. \"20 20\"):" << std::endl;
    std::cin >> n >> m;

    World world(n, m);

    std::vector<Position> randomPoints = world.GetRandomPointsWithinWorld(27);

    world.Add(new Human(std::move(randomPoints[0])));
    world.Add(new Dandelion(std::move(randomPoints[1])));

    world.Add(new Antelope(std::move(randomPoints[2])));
    world.Add(new Antelope(std::move(randomPoints[18])));
    world.Add(new Antelope(std::move(randomPoints[19])));
    world.Add(new Antelope(std::move(randomPoints[20])));
    world.Add(new Antelope(std::move(randomPoints[21])));
    world.Add(new Antelope(std::move(randomPoints[22])));

    world.Add(new Grass(std::move(randomPoints[3])));
    world.Add(new Fox(std::move(randomPoints[4])));
    world.Add(new Guarana(std::move(randomPoints[5])));

    world.Add(new Sheep(std::move(randomPoints[6])));
    world.Add(new Sheep(std::move(randomPoints[14])));
    world.Add(new Sheep(std::move(randomPoints[15])));
    world.Add(new Sheep(std::move(randomPoints[16])));
    world.Add(new Sheep(std::move(randomPoints[23])));
    world.Add(new Sheep(std::move(randomPoints[24])));
    world.Add(new Sheep(std::move(randomPoints[25])));
    world.Add(new Sheep(std::move(randomPoints[26])));

    world.Add(new Nightshade(std::move(randomPoints[7])));
    world.Add(new Turtle(std::move(randomPoints[8])));
    world.Add(new Turtle(std::move(randomPoints[17])));
    world.Add(new PineBorscht(std::move(randomPoints[9])));

    world.Add(new Wolf(std::move(randomPoints[10])));
    world.Add(new Wolf(std::move(randomPoints[11])));
    world.Add(new Wolf(std::move(randomPoints[13])));

    /* For special power testing
     *  world.Add(new Human(Position(0, 0)));
        world.Add(new Wolf(Position(0, 1)));
        world.Add(new Wolf(Position(1,0)));
        world.Add(new Wolf(Position(1, 1)));
     */

    while(world.IsRunning()) {
        world.SimulateRound();
    }

    world.Draw();
    std::cout << "Game Over! Click any button to close the simulation." << std::endl;

    getchar();
    getchar();

    return 0;
}
