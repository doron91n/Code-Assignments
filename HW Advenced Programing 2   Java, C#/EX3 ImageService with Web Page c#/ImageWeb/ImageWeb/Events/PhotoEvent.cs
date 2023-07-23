using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImageWeb.Events
{
    public class PhotoEvent
    { 
        //constructor
        public PhotoEvent(string path)
        {
            Path = path;
        }
        public string Path { get; set; }

    }
}