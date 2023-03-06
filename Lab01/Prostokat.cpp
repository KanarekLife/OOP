#include "Prostokat.h"

Prostokat::Prostokat(double a, double b) : a(a), b(b) {
	std::cout << "Konstruktor Prostokata(" << a << ", " << b << ")" << std::endl;
	this->liczbaObiektow++;
	this->liczbaProstokatow++;
	std::cout << "Liczba figur: " << this->liczbaObiektow << ", Liczba prostokatow: " << this->liczbaProstokatow << std::endl;
}

double Prostokat::GetA() const {
	return a;
}

double Prostokat::GetB() const {
	return b;
}

void Prostokat::SetA(double a) {
	this->a = a;
}

void Prostokat::SetB(double b) {
	this->b = b;
}

double Prostokat::Obwod() {
	return 2 * a + 2 * b;
}

double Prostokat::Pole() {
	return a * b;
}

void Prostokat::Wypisz(std::ostream& out) const {
	out << "Prostokat(" << a << ", " << b << ")" << std::endl;
}

Prostokat::~Prostokat() {
	std::cout << "Dekonstruktor Prostokata(" << a << ", " << b << ")" << std::endl;
}

int Prostokat::liczbaProstokatow = 0;