#include "LetterString.h"

LetterString::LetterString()
{
	std::string input;
	std::cin >> input;
	if (std::regex_match(input, std::regex("^[a-z]+$"))) {
		str = input;
	}
	else {
		throw ARGUMENT_EXCEPTION;
	}
}

LetterString::LetterString(std::string str)
{
	if (std::regex_match(str, std::regex("^[a-z]+$"))) {
		this->str = str;
	}
	else {
		throw ARGUMENT_EXCEPTION;
	}
}

LetterString::~LetterString()
{
	std::cout << "This is destructor!" << std::endl;
}

int LetterString::getLength()
{
	return str.length();
}

void LetterString::sort()
{
	for (int i = 0; i < str.length(); i++)
	{
		for (int j = (i % 2 == 0) ? 1 : 0; j + 1 < str.length(); j += 2)
		{
			if (str[j] > str[j + 1])
			{
				char temp = str[j];
				str[j] = str[j + 1];
				str[j + 1] = temp;
			}
		}
	}
}

std::string LetterString::getString()
{
	return str;
}
