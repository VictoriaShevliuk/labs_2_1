using System;
using System.Collections.Generic;
using System.Text;

namespace lab1
{
    class Obj
    {
        public float X { get; private set; }
        public float Y { get; private set; }

        public Obj(float x, float y)
        {
            X = x;
            Y = y;
        }
    }
}
