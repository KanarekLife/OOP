#include "Kolo.h"

#define _USE_MATH_DEFINES
#include <math.h>

void Kolo::Wypisz(std::ostream& out) const {
	out << "Kolo(" << r << ")" << std::endl;
}

Kolo::Kolo(double r) : r(r) {
	std::cout << "Konstruktor Kolo(" << r << ")" << std::endl;
	this->liczbaObiektow++;
	this->liczbaKol++;
	std::cout << "Liczba figur: " << this->liczbaObiektow << ", Liczba kol: " << this->liczbaKol << std::endl;
}

double Kolo::GetR() const {
	return r;
}

void Kolo::SetR(double r) {
	this->r = r;
}

double Kolo::Obwod() {
	return 2 * M_PI * r;
}

double Kolo::Pole() {
	return M_PI * pow(r, 2);
}

Kolo::~Kolo() {
	std::cout << "Dekonstruktor Kolo(" << r << ")" << std::endl;
}

int Kolo::liczbaKol = 0;