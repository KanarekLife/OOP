#include "Position.h"

Position::Position(int x, int y): x(x), y(y) {}

int Position::GetX() const {
    return this->x;
}

int Position::GetY() const {
    return this->y;
}

Position Position::WithOffset(int dx, int dy) const {
    return Position(this->x + dx, this->y + dy);
}


