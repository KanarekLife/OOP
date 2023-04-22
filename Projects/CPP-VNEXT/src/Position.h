#pragma once

class Position {
public:
    Position(int x, int y);
    int GetX() const;
    int GetY() const;
    Position WithOffset(int dx, int dy) const;
private:
    int x;
    int y;
};
