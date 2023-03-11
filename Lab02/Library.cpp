#include "Library.h"

Library::Library() : arr(new Book[0]), size(0) {}

Library::Library(std::size_t size) : size(size), arr(new Book[size]) {}

Library::Library(std::initializer_list<Book> list) : size(list.size()), arr(new Book[list.size()]) {
	std::size_t i = 0;
	for (Book b : list)
	{
		arr[i] = b;
		i++;
	}
}

Library::Library(const Library& orig) : size(orig.size) {
	arr = new Book[orig.size];
	for (size_t i = 0; i < orig.size; i++)
	{
		arr[i] = orig.arr[i];
	}
}

Library::Library(Library&& orig) : size(orig.size), arr(std::exchange(orig.arr, nullptr)) {
	orig.size = 0;
}

Library& Library::operator=(const Library& right) {
	this->arr = right.arr;
	this->size = right.size;
	return *this;
}

Library& Library::operator=(Library&& right) {
	std::swap(this->size, right.size);
	std::swap(this->arr, right.arr);
	return *this;
}

Book& Library::operator[](std::size_t index) {
	return arr[index];
}

const Book& Library::operator[](std::size_t index) const {
	return arr[index];
}

std::size_t Library::GetSize() const {
	return size;
}

Library::~Library() {
	this->size = 0;
	this->arr = nullptr;
}

std::ostream& operator<<(std::ostream& out, const Library& library) {
	out << "[";
	for (size_t i = 0; i < library.size; i++)
	{
		out << library.arr[i] << ", ";
	}
	out << "]";
	return out;
}