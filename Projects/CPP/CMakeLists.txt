cmake_minimum_required(VERSION 3.25)
project(CPP)

set(CMAKE_CXX_STANDARD 17)

add_executable(CPP src/main.cpp src/World.cpp src/World.h src/Position.cpp src/Position.h src/Organism.cpp src/Organism.h src/Animal.cpp src/Animal.h src/Animals/Human.cpp src/Animals/Human.h src/Animals/Wolf.cpp src/Animals/Wolf.h src/Animals/Sheep.cpp src/Animals/Sheep.h)
