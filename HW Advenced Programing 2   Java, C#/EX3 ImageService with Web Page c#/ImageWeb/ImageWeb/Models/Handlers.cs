using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImageWeb.Models
{
    public class Handlers
    {
        /// <summary>
        /// Handlers
        /// constructor
        /// </summary>
        /// <param name="handpath">handler path</param>
        public Handlers(string handpath)
        {
            getPath = handpath;
        }
        public string getPath { set; get; }

    }

}