using System;

namespace lab1
{
    class Program
    {
        static void Main(string[] args)
        {

            Line lineA;
            Line lineB;
            bool exit = false;
            string answer;
            while (!exit)
            {
                Console.WriteLine("");
                Console.WriteLine("Please, choose one of the following actions: ");
                Console.WriteLine("");
                Console.WriteLine("1 -  Check intersection of my lines; ");
                Console.WriteLine("2 -  Check if my lines are parallel; ");
                Console.WriteLine("3 -  Check if dot belongs to my line; ");
                Console.WriteLine("4 -  Exit the program; ");

                answer = Console.ReadLine();
                switch (answer[0])
                {
                    case '1':

                        Console.WriteLine("Lets fill your first line!");
                        lineA = Line.InputL();
                        Console.WriteLine("Lets fill your second line!");
                        lineB = Line.InputL();
                        Obj CrossDot = lineA.SearchCrossing(lineB);
                        if (CrossDot != null)
                        {
                            Console.WriteLine($"Coordinates of dot of intersection: X ={CrossDot.X}; Y ={CrossDot.Y};");
                        }
                        else
                        {
                            Console.WriteLine("Oops! Your lines have no intersection!");
                        }
                        break;

                    case '2':
                        Console.WriteLine("Lets fill your first line!");
                        lineA = Line.InputL();
                        Console.WriteLine("Lets fill your second line!");
                        lineB = Line.InputL();
                        if (lineA.CheckParallel(lineB))
                        {
                            Console.WriteLine("Your lines are parallel!");
                        }
                        else
                        {
                            Console.WriteLine("Oops! Your lines are not parallel!");
                        }

                        break;

                    case '3':
                        Console.WriteLine("Please, input your line below:");
                        lineA = Line.InputL();
                        Console.WriteLine("Please, input coordinates of your dot now:");
                        Console.Write("X:");
                        float x = float.Parse(Console.ReadLine());
                        Console.Write("Y:");
                        float y = float.Parse(Console.ReadLine());
                        Obj dot = new Obj(x, y);
                        if(lineA.BelobgsToLine(dot))
                        {
                            Console.WriteLine("Your dot belongs to line!:");
                        }
                        else
                        {
                            Console.WriteLine("Your dot doesn't belong to line!:");
                        }
                        break;

                    case '4':
                        exit = true;
                        break;

                    default:
                        Console.WriteLine("Not a menu option! Please, choose an action from a list!");
                        break;
                }
            }
            
        }
    }
}
