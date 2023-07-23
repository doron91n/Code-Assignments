using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImageWeb.Events
{
    public class PhotoEventArgs : EventArgs
    {
        public PhotoEventArgs(int number)
        {
            PhotoNumber = number;
        }

        public int PhotoNumber { get; set; }

    }
}