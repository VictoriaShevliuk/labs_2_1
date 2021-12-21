using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace OOP_Lab_2
{
    class LettersString
    {
        public string Str { get; private set; }

        public LettersString()
        {
            string input = Console.ReadLine();
            if (Regex.IsMatch(input, @"^[a-z]+$"))
            {
                Str = input;
            }
            else
            {
                throw new ArgumentException("String can have only small latin letters");
            }
        }

        public LettersString(string str)
        {
            if (Regex.IsMatch(str, @"^[a-z]+$"))
            {
                Str = str;
            }
            else
            {
                throw new ArgumentException("String can have only small latin letters");
            }
        }

        ~LettersString()
        {
            Console.WriteLine("This is destructor");
        }

        public int GetLength()
        {
            return Str.Length;
        }

        public void Sort()
        {
            char[] charArr = Str.ToCharArray();
            for (int i = 0; i < charArr.Length; i++)
            {
                for (int j = (i % 2 == 0) ? 1 : 0; j + 1 < charArr.Length; j += 2)
                {
                    if (charArr[j] > charArr[j + 1])
                    {
                        char temp = charArr[j];
                        charArr[j] = charArr[j + 1];
                        charArr[j + 1] = temp;
                    }
                }
            }
            Str = new String(charArr);
        }
    }
}
