using System;

namespace OOP_Lab_2
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                Console.WriteLine("Input your string: (You can input only latin letter in lower case)");
                LettersString newStr = new LettersString();
                Console.WriteLine($"Your string: {newStr.Str}");
                newStr.Sort();
                Console.WriteLine($"Your sorted string: {newStr.Str}");
                Console.WriteLine($"Length of your string: {newStr.GetLength()}");
            }
            catch (ArgumentException ex)
            {
                Console.WriteLine("Error: You can input only latin letter in lower case!");
                Console.WriteLine(ex.StackTrace);
            }
            Console.ReadKey();
        }

    }


}
