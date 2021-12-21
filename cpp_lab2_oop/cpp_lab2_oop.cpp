#include "LetterString.h"

using namespace std;

int main() {
    int exitCode = EXIT_SUCCESS;
    try
    {
        cout << "Input your string: (You can input only latin letter in lower case)" << endl;;
        LetterString newStr;
        cout << "Your string: " << newStr.getString() << endl;
        newStr.sort();
        cout << "Your sorted string: " << newStr.getString() << endl;
        cout << "Length of your string: " << newStr.getLength() << endl;
    }
    catch (int exCode)
    {
        if (exCode == ARGUMENT_EXCEPTION) {
            cout << "Error: You can input only latin letter in lower case!" << endl;
            exitCode = ARGUMENT_EXCEPTION;
        }
    }

    return exitCode;
}