using ImageService.Controller;
using ImageService.Controller.Handlers;
using ImageService.Infrastructure.Enums;
using ImageService.Logging;
using ImageService.Modal;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
        /// onCloseServer
        /// functions when the server gets a message to close
        /// </summary>
        /// <param name="sender">object that sent the event</param>
        /// <param name="e"></param>
        public void onCloseServer(object sender, DirectoryCloseEventArgs e)
        {
            IDirectoryHandler h = sender as IDirectoryHandler;
            CommandRecieved -= h.OnCommandRecieved;
        }

		/// <summary>
        /// sendCommand
        /// sends commands to handler
        /// </summary>
        public void sendCommand()
        {
            CommandRecievedEventArgs Com = new CommandRecievedEventArgs(1, null, "*");
            CommandRecieved?.Invoke(this, Com);
        }
		
		/// <summary>
        /// createHandler
        /// creates a handler and start running it
        /// </summary>
        /// <param name="directory">path of a directory</param>
        /// <param name="handler_num">num of the handler that starts</param>
        public void createHandler(string directory,int handler_num)
        {
            IDirectoryHandler h = new DirectoyHandler(controller,logging);
            handlers.Add(handler_num, h);
            h.StartHandleDirectory(directory);
            CommandRecieved += h.OnCommandRecieved;
            h.DirectoryClose += onCloseServer; 
        }
    }
}
