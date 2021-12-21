using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OOP_Lab3
{
    class TComplex
    {
        public float Real { get; set; }
        public float Imaginary { get; set; }

        public TComplex()
        {
            Console.WriteLine("input real part:");
            Real = float.Parse(Console.ReadLine());
            Console.WriteLine("input imaginary part:");
            Imaginary = float.Parse(Console.ReadLine());
        }

        public TComplex(float real, float imaginary)
        {
            Real = real;
            Imaginary = imaginary;
        }

        public TComplex(TComplex complexNum)
        {
            Real = complexNum.Real;
            Imaginary = complexNum.Imaginary;
        }

        public void Print()
        {
            string msg;
            if (Imaginary > 0) msg = $"{Real} + {Imaginary}i";
            else if (Imaginary < 0) msg = $"{Real} {Imaginary}i";
            else msg = $"{Real}";

            Console.WriteLine(msg);
        }

        public static TComplex operator +(TComplex num1, TComplex num2)
        {
            return new TComplex(num1.Real + num2.Real, num1.Imaginary + num2.Imaginary);
        }

        public static TComplex operator +(float real, TComplex num)
        {
            return new TComplex(num.Real + real, num.Imaginary);
        }

        public static TComplex operator +(TComplex num, float real)
        {
            return real + num;
        }

        public static TComplex operator -(TComplex num1, TComplex num2)
        {
            return new TComplex(num1.Real - num2.Real, num1.Imaginary - num2.Imaginary);
        }

        public static TComplex operator -(float real, TComplex num)
        {
            return new TComplex(real - num.Real, num.Imaginary);
        }

        public static TComplex operator -(TComplex num, float real)
        {
            return num + (-real);
        }

        public static TComplex operator *(TComplex num1, TComplex num2)
        {
            return new TComplex(num1.Real * num2.Real - num1.Imaginary * num2.Imaginary,
                                num1.Real * num2.Imaginary + num2.Real * num1.Imaginary);
        }

        public static TComplex operator /(TComplex num1, TComplex num2)
        {
            float realPart = (num1.Real * num2.Real + num1.Imaginary * num2.Imaginary) / (MathF.Pow(num2.Real, 2) + MathF.Pow(num2.Imaginary, 2));
            float imaginaryPart = (num1.Imaginary * num2.Real - num1.Real * num2.Imaginary) / (MathF.Pow(num2.Real, 2) + MathF.Pow(num2.Imaginary, 2));

            return new TComplex(realPart, imaginaryPart);
        }

        public static TComplex operator *(float realNum, TComplex num)
        {
            return new TComplex(num.Real * realNum, num.Imaginary * realNum);
        }

        public static TComplex operator *(TComplex num, float realNum)
        {
            return realNum * num;
        }

        public static TComplex operator /(TComplex num, float real)
        {
            return new TComplex(num.Real / real, num.Imaginary / real);
        }

        public static TComplex operator /(float real, TComplex num)
        {
            float realPart = (real * num.Real) / (MathF.Pow(num.Real, 2) + MathF.Pow(num.Imaginary, 2));
            float imaginaryPart = -(real * num.Imaginary) / (MathF.Pow(num.Real, 2) + MathF.Pow(num.Imaginary, 2)); ;

            return new TComplex(realPart, imaginaryPart);
        }
    }
}
