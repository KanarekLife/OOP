#include <iostream>
#include "FiguraPlaska.h"
#include "Kolo.h"
#include "Prostokat.h"
#include "Trojkat.h"

void task_3_1() {
    Kolo kolo = Kolo(2); // Konstuktor
    Kolo* koloPtr = &kolo;

    // Operator wypisywania
    std::cout << kolo;
    std::cout << *koloPtr;

    // Zwracanie z metody
    std::cout << kolo.GetR() << std::endl;

    // Nadpisywanie danych wewnetrznych obiektu przez setter
    kolo.SetR(3);
    std::cout << koloPtr->GetR() << std::endl;

    std::cout << "Obwod: " << kolo.Obwod() << std::endl;
    std::cout << "Pole: " << koloPtr->Pole() << std::endl;

    // Dekonstruktor wywolywany jest automatycznie gdy program wychodzi ze scope'a
}

void task_3_2() {
    auto kolo = Kolo(3);
    auto prostokat = Prostokat(2, 3);
    auto trojkat = Trojkat(1, 2, 3);

    FiguraPlaska* arr[] = {
        &kolo,
        &prostokat,
        &trojkat
    };
    for (size_t i = 0; i < sizeof(arr) / sizeof(FiguraPlaska*); i++)
    {
        std::cout << *arr[i];
        std::cout << "Obwod: " << arr[i]->Obwod() << std::endl;
        std::cout << "Pole: " << arr[i]->Pole() << std::endl;
    }
}

class Calculator {
public:
    virtual int Add(int a, int b) const {
        return a + b;
    }
    virtual double Add(double a, double b) const {
        return a + b;
    }
};

class NonNegativeCalculator : public Calculator {
public:
    int Add(int a, int b) const override {
        if (a < 0) {
            a = 0;
        }

        if (b < 0) {
            b = 0;
        }

        return a + b;
    }

    double Add(double a, double b) const override {
        if (a < 0) {
            a = 0;
        }

        if (b < 0) {
            b = 0;
        }

        return a + b;
    }
};


void task_3_3() {
    auto basicCalc = Calculator();
    auto nonNegativeCalc = NonNegativeCalculator();
    
    // Overriding
    Calculator* calc = &basicCalc;
    std::cout << "BasicCalc: " << calc->Add(-2, -2) << std::endl;
    calc = &nonNegativeCalc;
    std::cout << "NonNegativeCalc: " << calc->Add(-2, -2) << std::endl;

    // Overloading
    std::cout << "Passing ints: " << basicCalc.Add(2, 2) << std::endl;
    std::cout << "Passing doubles: " << basicCalc.Add(2.5, 3.25) << std::endl;
}

int main()
{
    Kolo k1 = Kolo(2);
    Trojkat t1 = Trojkat(2, 3, 4);
    Trojkat t2 = Trojkat(3, 4, 5);
    Prostokat p1 = Prostokat(1, 2);
    Prostokat p2 = Prostokat(2, 3);
    Prostokat p3 = Prostokat(3, 4);

    std::cout << "\nZadanie 3.1:\n" << std::endl;;
    task_3_1();
    std::cout << "\nZadanie 3.2:\n"<< std::endl;
    task_3_2();
    std::cout << "\nZadanie 3.3:\n" << std::endl;
    task_3_3();
}
