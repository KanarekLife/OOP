#pragma once

#include <string>

class Position {
public:
    Position(int x, int y);
    int GetX() const;
    int GetY() const;
    bool Equals(const Position& position) const;
    Position WithOffset(int dx, int dy) const;
    std::string ToString() const;
private:
    int x;
    int y;
};
