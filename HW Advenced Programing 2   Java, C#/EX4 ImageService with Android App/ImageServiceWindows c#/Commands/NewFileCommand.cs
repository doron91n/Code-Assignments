using ImageService.Infrastructure;
using ImageService.Modal;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/// <summary>
/// NewFileCommand
/// class that operate the AddFile function from the Image modal
/// </summary>
namespace ImageService.Commands
{
    public class NewFileCommand : ICommand
    {
        private IImageServiceModal m_modal;

		/// <summary>
        /// NewFileCommand
        /// constructor
        /// </summary>
        /// <param name="modal">instance of image modal</param>
        public NewFileCommand(IImageServiceModal modal)
        {
            m_modal = modal;            // Storing the Modal
        }

		/// <summary>
        /// Execute
        /// gets the arguments and add a file with them
        /// </summary>
        /// <param name="args">array with the full path to the picture and picture's name</param>
        /// <param name="result">true if succeeded,false otherwise</param>
        /// <returns>the new path of the image</returns>
        public string Execute(string[] args, out bool result)
        {
            // The String Will Return the New Path if result = true, else will return the error message 
            // args[0] = file path , args[1] = file name
            return m_modal.AddFile(args[0], out result);
        }
    }
};