using System;
using System.Globalization;
using System.Threading;

namespace OOP_Lab3
{
    class Program
    {
        static void Main(string[] args)
        {
            Thread.CurrentThread.CurrentCulture = new CultureInfo("en-US");
            bool exitFlag = false;
            string ans;
            while (!exitFlag)
            {
                Console.WriteLine("Chose operation:");
                Console.WriteLine("\"0\" - sum 2 complex numbers:");
                Console.WriteLine("\"1\" - sum complex number and real:");
                Console.WriteLine("\"2\" - substract 2 complex numbers:");
                Console.WriteLine("\"3\" - substract complex number from real:");
                Console.WriteLine("\"4\" - substract real number from complex:");
                Console.WriteLine("\"5\" - multiply 2 complex numbers:");
                Console.WriteLine("\"6\" - multiply complex number by real:");
                Console.WriteLine("\"7\" - divide 2 complex numbers:");
                Console.WriteLine("\"8\" - divide complex number by real:");
                Console.WriteLine("\"9\" - divide real by complex number:");
                Console.WriteLine("\"e\" - exit:");

                TComplex result = null, num1, num2;
                float real;

                ans = Console.ReadLine();
                switch (ans[0])
                {
                    case '0':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input complex num:");
                        num2 = new TComplex();
                        result = num1 + num2;
                        break;
                    case '1':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input real num:");
                        real = float.Parse(Console.ReadLine());
                        result = num1 + real;
                        break;
                    case '2':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input complex num:");
                        num2 = new TComplex();
                        result = num1 - num2;
                        break;
                    case '3':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input real num:");
                        real = float.Parse(Console.ReadLine());
                        result = real - num1;
                        break;
                    case '4':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input real num:");
                        real = float.Parse(Console.ReadLine());
                        result = num1 - real;
                        break;
                    case '5':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input complex num:");
                        num2 = new TComplex();
                        result = num1 * num2;
                        break;
                    case '6':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input real num:");
                        real = float.Parse(Console.ReadLine());
                        result = num1 * real;
                        break;
                    case '7':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input complex num:");
                        num2 = new TComplex();
                        result = num1 / num2;
                        break;
                    case '8':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input real num:");
                        real = float.Parse(Console.ReadLine());
                        result = num1 / real;
                        break;
                    case '9':
                        Console.WriteLine("Input complex num:");
                        num1 = new TComplex();
                        Console.WriteLine("Input real num:");
                        real = float.Parse(Console.ReadLine());
                        result = real / num1;
                        break;
                    case 'e':
                        exitFlag = true;
                        break;
                    default:
                        Console.WriteLine("There is no such option!");
                        break;

                }
                if (result != null)
                {
                    Console.WriteLine("Result is:");
                    result.Print();
                    Console.WriteLine();
                }
            }
        }
    }
}
