#pragma once
#include "FiguraPlaska.h"
class Kolo : public FiguraPlaska
{
private:
	double r;
protected:
	void Wypisz(std::ostream& out) const override;
	static int liczbaKol;
public:
	Kolo(double r);
	double GetR() const;
	void SetR(double r);
	double Obwod() override;
	double Pole() override;
	~Kolo() override;
};

