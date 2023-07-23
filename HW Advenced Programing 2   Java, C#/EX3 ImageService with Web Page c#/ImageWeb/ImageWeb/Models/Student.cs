using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;

namespace ImageWeb.Models
{
    public class Student
    {
        public Student(string f_name,string l_name,string id) {
            FirstName = f_name;
            LastName = l_name;
            ID = id;
        }
        public void copy(Student student) {
            FirstName = student.FirstName;
            LastName = student.LastName;
            ID = student.ID;
        }

        [Required]
        [Display(Name = "ID")]
        public string ID { get; set; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "FirstName")]
        public string FirstName { get; set; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "LastName")]
        public string LastName { get; set; }

    }
}