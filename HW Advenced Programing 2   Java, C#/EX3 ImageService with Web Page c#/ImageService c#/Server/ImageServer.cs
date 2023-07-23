

using Commands.Infrastructure;
using ImageService.Controller;
using ImageService.Controller.Handlers;
using ImageService.Logging;
using ImageService.Modal.Event;
using System;
using System.Collections.Generic;
/// <summary>
/// ImageServer
/// class that creates handlers and sends commands to the controller
/// </summary>
namespace ImageService.Server
{
    public class ImageServer
    {
        #region Members
        private IImageController controller;
        private ILoggingService logging;
        private Dictionary<int, IDirectoryHandler> handlers;
        #endregion

        #region Properties
        public event EventHandler<CommandRecievedEventArgs> CommandRecieved; // The event that notifies about a new Command being recieved
        public event EventHandler<CommandRecievedEventArgs> NotifyClients;

        #endregion

        /// <summary>
        /// ImageServer
        /// constructor
        /// </summary>
        /// <param name="controller1">instance of controller</param>
        /// <param name="logging1">instance of logging service</param>
        /// <param name="handlersPath">lost of path handlers listen to</param>
        public ImageServer(IImageController controller1, ILoggingService logging1,List<string> handlersPath)
        {
            controller = controller1;
            logging = logging1;
            handlers = new Dictionary<int, IDirectoryHandler> { };
            // creates the handlers dictionary
            int i = 0;
			foreach (string s in handlersPath)
            {
                createHandler(s,i);
                i++;
            }
        }

        /// <summary>
        /// CloseServer
        /// functions when the server gets a message to close
        /// </summary>
        /// <param name="sender">object that sent the event</param>
        /// <param name="e"></param>
        public void CloseServer()
        {
            string[] args = { "*" };
            CommandRecievedEventArgs e = new CommandRecievedEventArgs(CommandEnum.CloseCommand, args, "NO_PATH");
            this.CommandRecieved?.Invoke(this, e);
            this.logging.Log("Server closing", MessageTypeEnum.INFO);
        }

        /// <summary>
        /// CloseCommand
        /// sends the CloseCommand to handler
        /// </summary>
        public void CloseCommand(object sender, CommandRecievedEventArgs cmd)
        {
            CommandRecieved?.Invoke(this, cmd);
        }
		
		/// <summary>
        /// createHandler
        /// creates a handler and start running it
        /// </summary>
        /// <param name="directory">path of a directory</param>
        /// <param name="handler_num">num of the handler that starts</param>
        public void createHandler(string directory,int handler_num)
        {
            IDirectoryHandler h = new DirectoyHandler(controller,this.logging);
            handlers.Add(handler_num, h);
            CommandRecieved += h.OnCommandRecieved;
            h.DirectoryClose += new EventHandler<DirectoryCloseEventArgs>(CloseHandler);
            h.StartHandleDirectory(directory);

        }
        /// <summary>
        /// CloseHandler
        /// closes a given handler  
        /// </summary>
        /// <param name="sender"> the object that called this function </param>
        /// <param name="e"> event with information about closing handler</param>
        public void CloseHandler(object sender, DirectoryCloseEventArgs e)
        {
            Dictionary<int,IDirectoryHandler> handlers_dict = this.handlers;
            foreach (KeyValuePair<int, IDirectoryHandler> entry  in handlers_dict)
            {
                IDirectoryHandler dir_handler = entry.Value;
                if (e.DirectoryPath.Equals("*") || dir_handler.DirectoryPath().Equals(e.DirectoryPath))
                {
                    this.CommandRecieved -= dir_handler.OnCommandRecieved;
                    dir_handler.DirectoryClose -= CloseHandler;
                    dir_handler.closeHandler(e.DirectoryPath);
                    this.logging.Log("Closed handler : " + e.DirectoryPath, MessageTypeEnum.INFO);
                    string[] args = { e.DirectoryPath };
                    CommandRecievedEventArgs close_cmd_info = new CommandRecievedEventArgs(CommandEnum.CloseCommand, args, e.DirectoryPath);
                    // notify all of the clients that the handler was closed
                    NotifyClients?.Invoke(this, close_cmd_info);
                }
            }
        }
    }
}
