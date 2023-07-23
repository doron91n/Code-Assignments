using ImageService.Commands;
using ImageService.Infrastructure;
using ImageService.Infrastructure.Enums;
using ImageService.Logging;
using ImageService.Modal;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/// <summary>
/// ImageController
/// class that recieve commands related to Image modal and file system
/// </summary>
namespace ImageService.Controller
{
    public class ImageController : IImageController
    {
		#region members
        private IImageServiceModal m_modal;                      // The Modal Object
        private Dictionary<int, ICommand> commands;
		#endregion
		
		/// <summary>
        /// ImageController
        /// constructor
        /// </summary>
        /// <param name="modal">instance of image modal</param>
        public ImageController(IImageServiceModal modal)
        {
            m_modal = modal;                    // Storing the Modal Of The System
            commands = new Dictionary<int, ICommand>()
            {
				// initiallize dictionary
                { (int)CommandEnum.NewFileCommand,new NewFileCommand(modal) }
            };
        }
		
		/// <summary>
        /// ExecuteCommand
        /// Decipher command and call relevant function in image modal in new task
        /// </summary>
        /// <param name="commandID">id of the command</param>
        /// <param name="args">argumnets needed to run the command</param>
        /// <param name="result">true if succeeded,false otherwise</param>
        public string ExecuteCommand(int commandID, string[] args, out bool result)
        {
            ICommand cmd;
            // create a task for executing the given command 
            if (commands.TryGetValue(commandID,out cmd))
            {
				//create tuple that holds the command to execute and boolean if succeeded or faild
                Task<Tuple<string, bool>> taskExecute = new Task<Tuple<string, bool>>(() => {
                    bool cmdResult;
                    //execute the given command 
                    return Tuple.Create(cmd.Execute(args, out cmdResult), cmdResult);
                });
                // start a new instance of the task (thread)
                taskExecute.Start();
                System.Threading.Thread.Sleep(5);
                Tuple<string, bool> taskResult = taskExecute.Result;
                result = taskResult.Item2;
                return taskResult.Item1;
            }
            else
            {
                result = false;
                return "Error: given command was not found";
            }
		}
    }
};