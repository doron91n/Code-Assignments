
using Commands.Infrastructure;
using ImageService.Commands;
using ImageService.Logging;
using ImageService.Modal;
using ImageService.Modal.Event;
using System;
using System.Collections.Generic;
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
        private Dictionary<CommandEnum, ICommand> commands;
        #endregion
        public event EventHandler<DirectoryCloseEventArgs> HandlerClosedEvent;

        /// <summary>
        /// ImageController
        /// constructor
        /// </summary>
        /// <param name="modal">instance of image modal</param>
        public ImageController(IImageServiceModal modal)
        {
            this.m_modal = modal;                    // Storing the Modal Of The System
            commands = new Dictionary<CommandEnum, ICommand>()
            {
				// initiallize dictionary
                {CommandEnum.NewFileCommand,new NewFileCommand(modal) },
             { CommandEnum.GetConfigCommand,new GetConfigCommand() },
                {CommandEnum.LogCommand,new LogCommand() },
                { CommandEnum.CloseCommand,new CloseCommand() }
            };
        }
        public void HandlerClosed(object sender, DirectoryCloseEventArgs e)
        {
            HandlerClosedEvent?.Invoke(sender, e);
        }
        /// <summary>
        /// ExecuteCommand
        /// Decipher command and call relevant function in image modal in new task
        /// </summary>
        /// <param name="commandID">id of the command</param>
        /// <param name="args">argumnets needed to run the command</param>
        /// <param name="result">true if succeeded,false otherwise</param>
        public string ExecuteCommand(CommandEnum commandID, string[] args, out bool result)
        {
            ICommand cmd;
            // create a task for executing the given command 
            if (commands.TryGetValue(commandID, out cmd))
            {
				//create tuple that holds the command to execute and boolean if succeeded or faild
                Task<Tuple<string, bool>> taskExecute = new Task<Tuple<string, bool>>(() => {
                    bool cmdResult;
                    //execute the given command 
                    string excute_result = cmd.Execute(args, out cmdResult);
                    return Tuple.Create(excute_result, cmdResult);
                });
                // start a new instance of the task (thread)
                taskExecute.Start();
                System.Threading.Thread.Sleep(1);
                Tuple<string, bool> taskResult = taskExecute.Result;
                result = taskResult.Item2;
                return taskResult.Item1;
            }
            else
            {
                result = false;
                return "Error: given command is not valid command";
            }
		}
    }
};