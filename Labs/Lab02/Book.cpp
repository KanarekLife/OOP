#include "Book.h"

Book::Book() {
	this->author = "";
	this->title = "";
}

Book::Book(std::string& author, std::string& title) : author(author), title(title) {
}

Book::Book(std::string&& author, std::string&& title) : author(author), title(title) {
}

Book::Book(const Book& other) : author(other.author), title(other.title) {
}

Book::Book(Book&& other) {
	this->author = other.author;
	this->title = other.title;
	other.author = "";
	other.title = "";
}

std::string Book::GetAuthor() const {
	return author;
}

std::string Book::GetTitle() const {
	return title;
}

void Book::SetAuthor(std::string& author) {
	this->author = author;
}

void Book::SetTitle(std::string& title) {
	this->title = title;
}

void Book::SetAuthor(std::string&& author) {
	this->author = author;
}

void Book::SetTitle(std::string&& title) {
	this->title = title;
}

Book& Book::operator=(const Book& right) {
	this->author = right.author;
	this->title = right.title;
	return *this;
}

Book& Book::operator=(Book&& right) {
	author.swap(right.author);
	title.swap(right.title);
	return *this;
}

std::ostream& operator<<(std::ostream& out, const Book& book) {
	out << book.title << " by " << book.author;
	return out;
}