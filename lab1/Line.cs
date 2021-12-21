using System;
using System.Collections.Generic;
using System.Text;

namespace lab1
{
    class Line
    {
        public Obj Vect { get; set; }
        public Obj Dot { get; set; }

        public Line(Obj dot, Obj vect)
        {
            Vect = vect;
            Dot = dot;
        }
        
        public Obj SearchCrossing(Line line)
        {
            float a1, b1, c1;
            float a2, b2, c2;

            a1 = Vect.Y;
            b1 = -Vect.X;
            c1 = Dot.Y * Vect.X - Dot.X * Vect.Y;

            a2 = line.Vect.Y;
            b2 = -line.Vect.X;
            c2 = line.Dot.Y * line.Vect.X - line.Dot.X * line.Vect.Y;

            float devider = (a1 * b2 - a2 * b1);

            if(devider ==0 || a1 == 0)
            {
                return null;
            }

            float y = (a2 * c1 - a1 * c2) / devider;
            float x = -(b1 * y + c1) / a1;
         
            return new Obj(x, y);
        }


      public  static Line InputL()
        {
            Console.WriteLine("Please, input coordinates for your dot below:");
            Console.WriteLine("X: ");
            int x = int.Parse(Console.ReadLine());
            Console.WriteLine("Y: ");
            int y = int.Parse(Console.ReadLine());
            Obj dot = new Obj(x, y);

            Console.WriteLine("Please, input coordinates for your line below:");
            Console.WriteLine("X: ");
            x = int.Parse(Console.ReadLine());
            Console.WriteLine("Y: ");
            y = int.Parse(Console.ReadLine());
            Obj vect = new Obj(x, y);
            return new Line(dot, vect);
        }


        public bool CheckParallel(Line line)
        {
            bool check = false;
         
            if(!(line.Vect.X ==0 &&line.Vect.Y ==0)||!(Vect.X == 0 && Vect.Y == 0))
            {
                float koeficient = 0;
                if (Vect.X == 0)
                {
                    koeficient = line.Vect.Y / Vect.Y;
                }
                else
                {
                    koeficient = line.Vect.X / Vect.X;
                }
                check = (koeficient * Vect.X == line.Vect.X) && (koeficient * Vect.Y == line.Vect.Y);
            }
            return check;
        }


        public bool BelobgsToLine(Obj dot)
        {
            return (dot.X - Dot.X) / Vect.X == (dot.Y - Dot.Y) / Vect.Y;
        }


        public void ShowDot()
        {
            Console.WriteLine($"Your dots: X: {Dot.X}, Y: {Dot.Y}");
        }


        public void ShowVect()
        {
            Console.WriteLine($"Your line's coordinates: X: {Vect.X}, Y: {Vect.Y}");
        }


      
    }
}
