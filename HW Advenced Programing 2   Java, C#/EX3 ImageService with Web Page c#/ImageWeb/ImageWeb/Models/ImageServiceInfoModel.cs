using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using System.IO;
using Newtonsoft.Json.Linq;
using System.Web.Mvc;
using ImageWeb.Events;

namespace ImageWeb.Models
{
    public class ImageServiceInfoModel
    {
        public List<Student> students_info = new List<Student>();
        private ConnectionModel Connection_model;
        /// <summary>
        /// ImageServicInfoeModel
        /// constructor
        /// </summary>
        public ImageServiceInfoModel() {
            Connection_model = ConnectionModel.CreateConnection();
            ReadStudentInfo();
        }
        public List<Student> GetStudentsInfo() { return this.students_info;}
        /// <summary>
        /// Function Name: ReadStudentInfo
        /// Description: reads info from App_Data
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public void ReadStudentInfo()
        {
            this.students_info = new List<Student>();
            StreamReader file = new StreamReader(System.Web.HttpContext.Current.Server.MapPath("~/App_Data/StudentsINFO.txt"));
            string line;
            for (int i = 0; i < 2; i++)
            {
                line = file.ReadLine();
                string[] name = line.Split(' ');
                this.students_info.Add(new Student(name[0], name[1], name[2]));
            }
        }
        /// <summary>
        /// Function Name: GetServiceStatus
        /// Description: get information about service status
        /// Arguments: null
        /// </summary>
        /// <returns> status </returns>
        public string GetServiceStatus
        {
            get
            {
                if (Connection_model.IsClientConnected())
                {
                    return "Running";
                }
                else {
                    return "Not Running";
                }
            }
        }
        /// <summary>
        /// Function Name: SetPicNum
        /// Description: sets number of pictures in output directory
        /// Arguments: 
        ///<param name="sender">path to handler</param>
        /// <param name="photoEventArgs">photo event holds number of photos</param>
        /// </summary>
        /// <returns> null </returns>
        public void SetPicNum(object sender, PhotoEventArgs photoEventArgs)
        {
            int temp = photoEventArgs.PhotoNumber;
            GetServicePicNum = temp.ToString();
        }

        /// <summary>
        /// GetServicePicNum
        /// gets number of picstures in directory
        /// </summary>
        public string GetServicePicNum
            { get; set;}
        /// <summary>
        /// GetStudentInstance
        /// 
        /// </summary>
        /// <returns></returns>
        public Student GetStudentInstance()
        {
            return new Student("Nope", "Nope", "Nope");
        }
    }
}