using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImageWeb.Models
{
    public class DirectoryModel
    {
        /// <summary>
        /// DirectoryModel
        /// constructor
        /// </summary>
        /// <param name="path">path to directory</param>
        public DirectoryModel(string path)
        {
            DirectoryPath = path;
        }

        public string DirectoryPath { set; get; }
    }
}