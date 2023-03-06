#pragma once
#include "FiguraPlaska.h"
class Trojkat : public FiguraPlaska
{
	double a, b, c; // Private by default
protected:
	void Wypisz(std::ostream& out) const override;
	static int liczbaTrojkatow;
public:
	Trojkat(double a, double b, double c);
	double GetA() const;
	double GetB() const;
	double GetC() const;
	void SetA(double a);
	void SetB(double b);
	void SetC(double c);
	double Obwod() override;
	double Pole() override;
	~Trojkat() override;
};

