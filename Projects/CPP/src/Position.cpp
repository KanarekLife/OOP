#include "Position.h"

Position::Position(int x, int y): x(x), y(y) {}

Position Position::WithOffset(int dx, int dy) const {
    return {this->x + dx, this->y + dy};
}

int Position::GetX() const {
    return this->x;
}

int Position::GetY() const {
    return this->y;
}
