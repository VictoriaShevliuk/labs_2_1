using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OOP_Lab4
{
    class TComplex
    {
        public float Real { get; set; }
        public float Imaginary { get; set; }

        public TComplex()
        {
            try
            {
                Console.WriteLine("input real part:");
                Real = float.Parse(Console.ReadLine());
                Console.WriteLine("input imaginary part:");
                Imaginary = float.Parse(Console.ReadLine());
            }
            catch (FormatException ex)
            {
                throw new FormatException("ERROR!\n" + ex.StackTrace);
            }
        }

        public TComplex(float real, float imaginary)
        {
            Real = real;
            Imaginary = imaginary;
        }

        public TComplex(TComplex complexNum)
        {
            if (complexNum == null)
            {
                throw new ArgumentNullException(nameof(complexNum));
            }
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
            if (num1 == null)
            {
                throw new ArgumentNullException(nameof(num1));
            }
            if (num2 == null)
            {
                throw new ArgumentNullException(nameof(num2));
            }
            return new TComplex(num1.Real + num2.Real, num1.Imaginary + num2.Imaginary);
        }

        public static TComplex operator +(float real, TComplex num)
        {
            if (num == null)
            {
                throw new ArgumentNullException(nameof(num));
            }
            return new TComplex(num.Real + real, num.Imaginary);
        }

        public static TComplex operator +(TComplex num, float real)
        {
            return real + num;
        }

        public static TComplex operator -(TComplex num1, TComplex num2)
        {
            if (num1 == null)
            {
                throw new ArgumentNullException(nameof(num1));
            }
            if (num2 == null)
            {
                throw new ArgumentNullException(nameof(num2));
            }
            return new TComplex(num1.Real - num2.Real, num1.Imaginary - num2.Imaginary);
        }

        public static TComplex operator -(float real, TComplex num)
        {
            if (num == null)
            {
                throw new ArgumentNullException(nameof(num));
            }
            return new TComplex(real - num.Real, num.Imaginary);
        }

        public static TComplex operator -(TComplex num, float real)
        {
            return num + (-real);
        }

        public static TComplex operator *(TComplex num1, TComplex num2)
        {
            if (num1 == null)
            {
                throw new ArgumentNullException(nameof(num1));
            }
            if (num2 == null)
            {
                throw new ArgumentNullException(nameof(num2));
            }
            return new TComplex(num1.Real * num2.Real - num1.Imaginary * num2.Imaginary,
                                num1.Real * num2.Imaginary + num2.Real * num1.Imaginary);
        }

        public static TComplex operator /(TComplex num1, TComplex num2)
        {
            if (num1 == null)
            {
                throw new ArgumentNullException(nameof(num1));
            }
            if (num2 == null)
            {
                throw new ArgumentNullException(nameof(num2));
            }
            float delimiter = MathF.Pow(num2.Real, 2) + MathF.Pow(num2.Imaginary, 2);
            if (delimiter == 0)
            {
                throw new DivideByZeroException("Division by zero!");
            }
            float realPart = (num1.Real * num2.Real + num1.Imaginary * num2.Imaginary) / delimiter;
            float imaginaryPart = (num1.Imaginary * num2.Real - num1.Real * num2.Imaginary) / delimiter;

            return new TComplex(realPart, imaginaryPart);
        }

        public static TComplex operator *(float realNum, TComplex num)
        {
            if (num == null)
            {
                throw new ArgumentNullException(nameof(num));
            }
            return new TComplex(num.Real * realNum, num.Imaginary * realNum);
        }

        public static TComplex operator *(TComplex num, float realNum)
        {
            return realNum * num;
        }

        public static TComplex operator /(TComplex num, float real)
        {
            if (num == null)
            {
                throw new ArgumentNullException(nameof(num));
            }
            if (real == 0)
            {
                throw new DivideByZeroException("Division by zero!");
            }
            return new TComplex(num.Real / real, num.Imaginary / real);
        }

        public static TComplex operator /(float real, TComplex num)
        {
            if (num == null)
            {
                throw new ArgumentNullException(nameof(num));
            }
            float delimiter = MathF.Pow(num.Real, 2) + MathF.Pow(num.Imaginary, 2);
            if (delimiter == 0)
            {
                throw new DivideByZeroException("Division by zero!");
            }
            float realPart = (real * num.Real) / delimiter;
            float imaginaryPart = -(real * num.Imaginary) / delimiter; ;

            return new TComplex(realPart, imaginaryPart);
        }
    }
}
