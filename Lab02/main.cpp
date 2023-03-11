#include <iostream>
#include "Book.h"
#include "Library.h"

void BookTests() {
    std::string a = "Rafal Kosik", t = "Felix, Net i Nika";
    Book e;
    std::cout << "e: " << e << std::endl;
    Book b1 = { a, t };
    std::cout << "b1: " << b1 << std::endl;
    Book b2 = { "Frank Herbert","Dune" };
    std::cout << "b2: " << b2 << std::endl;
    Book b3 = b1;
    std::cout << "b3: " << b3 << " b1: " << b1 << std::endl;
    e = std::move(b2);
    std::cout << "e: " << e << " b2:" << b2 << std::endl;
    e.SetAuthor("Andrzej Sapkowski");
    std::cout << "e: " << e << std::endl;
    e.SetTitle("Wiedzmin");
    std::cout << "e: " << e << std::endl;
}

void LibraryTests() {
    Library e;
    std::cout << "e: " << e << std::endl;
    //3-5 books
    Library l1 = { {"Rafal Kosik", "Felix, Net i Nika"},
    {"Frank Herbert", "Dune"},
    {"Andrzej Sapkowski", "Wiedzmin"} };
    std::cout << "l1: " << l1 << std::endl;
    Library l2(2);
    std::cout << "l2: " << l2 << std::endl;
    l2[0] = { "Frank Herbert", "Dune" };
    l2[1] = { "Andrzej Sapkowski", "Wiedzmin" };
    std::cout << "l2: " << l2 << std::endl;
    e = std::move(l2);
    std::cout << "e: " << e << " l2: " << l2 << std::endl;
    l1[0] = std::move(e[1]);
    std::cout << "l1: " << l1 << " e: " << e << std::endl;
}

int main()
{
    BookTests();
    LibraryTests();
}

