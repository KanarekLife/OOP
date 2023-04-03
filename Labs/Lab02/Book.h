#pragma once

#include <iostream>

class Book
{
private:
	std::string author, title;
public:
	Book();
	Book(std::string& author, std::string& title); //l-references
	Book(std::string&& author, std::string&& title); //r-references
	Book(const Book& other);
	Book(Book&& other);
	std::string GetAuthor() const;
	std::string GetTitle() const;
	void SetAuthor(std::string& author);
	void SetTitle(std::string& title);
	void SetAuthor(std::string&& author);
	void SetTitle(std::string&& title);
	Book& operator=(const Book& right);
	Book& operator=(Book&& right);
	friend std::ostream& operator<<(std::ostream& out, const Book& book);
};

