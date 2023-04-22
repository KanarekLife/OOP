cmake_minimum_required(VERSION 3.25)
project(CPP_VNEXT)

set(CMAKE_CXX_STANDARD 17)

add_executable(CPP_VNEXT src/main.cpp src/World.cpp src/World.h src/Organism.cpp src/Organism.h src/Position.cpp src/Position.h src/Animal.cpp src/Animal.h src/Animals/Wolf.cpp src/Animals/Wolf.h src/CollisionContext.cpp src/CollisionContext.h src/Animals/Sheep.cpp src/Animals/Sheep.h src/Animals/Fox.cpp src/Animals/Fox.h src/Animals/Turtle.cpp src/Animals/Turtle.h)
