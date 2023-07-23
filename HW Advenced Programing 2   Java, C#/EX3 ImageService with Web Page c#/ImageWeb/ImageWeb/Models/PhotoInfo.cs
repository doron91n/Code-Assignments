using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Web;

namespace ImageWeb.Models
{
    public class PhotoInfo
    {
        /// <summary>
        /// PhotoInfo
        /// constructor
        /// </summary>
        /// <param name="imgPath">path of image</param>
        /// <param name="thumbPath">path of thumbnail image</param>
        /// <param name="pathDirectory">path of directory</param>
        /// 
        public PhotoInfo(string imgPath, string thumbPath, string pathDirectory)
        {
            string root = Path.GetPathRoot(imgPath);
            Debug.WriteLine("PhotoInfo path: "+ imgPath);
            Directory.SetCurrentDirectory(root);
            string[] splited=pathDirectory.Split('/');
            OutPutDirName = splited[splited.Length-2];
            directoryPath = pathDirectory; 
            ImgPath = imgPath;
            ThumbPath = thumbPath;
            ImgName = Path.GetFileName(imgPath);
            getMonth = new DirectoryInfo(Path.GetDirectoryName(imgPath)).Name;
            getYear = new DirectoryInfo(Path.GetDirectoryName(Path.GetDirectoryName(imgPath))).Name;
        }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "ThumbPath")]
        public string ThumbPath { set; get; }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "OutPutDirName")]
        public string OutPutDirName { set; get; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "ImgPath")]
        public string ImgPath { set; get; }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "directoryPath")]
        public string directoryPath { set; get; }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "ImgName")]
        public string ImgName { set; get; }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "getYear")]
        public string getYear { set; get; }

        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "getMonth")]
        public string getMonth { set; get; }
    }
}