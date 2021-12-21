#include <iostream>
#include <string>
#include <regex>

#define ARGUMENT_EXCEPTION 101

#ifndef LETTER_STRING_H
#define LETTER_STRING_H
class LetterString {
private:
	std::string str;

public:
	LetterString();
	LetterString(std::string str);
	~LetterString();
	int getLength();
	void sort();
	std::string getString();
};
#endif

