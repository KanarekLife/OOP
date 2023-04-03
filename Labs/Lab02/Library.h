#pragma once

#include "Book.h";
#include <initializer_list>

class Library
{
private:
	Book* arr;
	std::size_t size;
public:
	Library();
	Library(std::size_t size);
	Library(std::initializer_list<Book> list);
	Library(const Library& orig);
	Library(Library&& orig);
	Library& operator=(const Library& right);
	Library& operator=(Library&& right);
	Book& operator[](std::size_t index);
	const Book& operator[](std::size_t index) const;
	std::size_t GetSize() const;
	~Library();
	friend std::ostream& operator<<(std::ostream& out, const Library& library);
	friend void SwapBooksInLibrary(Library& l1, Library& l2);
};

