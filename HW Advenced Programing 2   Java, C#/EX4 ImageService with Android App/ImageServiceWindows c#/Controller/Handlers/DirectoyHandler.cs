
using ImageService.Modal;
using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImageService.Infrastructure;
using ImageService.Infrastructure.Enums;
using ImageService.Logging;
using ImageService.Logging.Modal;
using System.Text.RegularExpressions;
using System.Configuration;
using System.Drawing;
using System.Collections;
using System.Drawing.Printing;

/// <summary>
/// DirectoryHandler
/// class that listen to a directory comes from the server and handles it
/// </summary>
namespace ImageService.Controller.Handlers
{
    public class DirectoyHandler : IDirectoryHandler
    {
        #region Members
        private IImageController m_controller;              // The Image Processing Controller
        private ILoggingService m_logging;
        private FileSystemWatcher watcher;             // The Watcher of the Dir
        private string m_path;                              // The Path of directory
        private readonly string[] filterTypes = { ".jpg", ".png", ".gif", ".bmp" };
        #endregion

        public event EventHandler<DirectoryCloseEventArgs> DirectoryClose; // The Event That Notifies that the Directory is being closed

		/// <summary>
        /// DirectoryHandler
		/// constructor
        /// </summary>
        /// <param name="directory">path to directory</param>
        /// <param name="controller">Image controller instance</param>
        /// <param name="logger">instance of logger</param>
        public DirectoyHandler(IImageController controller, ILoggingService logger)
        {
            this.m_controller = controller;
            this.m_logging = logger;
        }

		/// <summary>
        /// StartHandkeDirectory
        /// initiallizing FileSystemWatcher and starts listening to directory
        /// </summary>
        /// <param name="dirPath"></param>
        public void StartHandleDirectory(string dirPath)
        {
            m_logging.Log("StartHandleDirectory for: "+ dirPath, MessageTypeEnum.INFO);
            this.m_path = dirPath;
            this.watcher = new FileSystemWatcher(dirPath,"*");
            // Add event handlers.
            watcher.EnableRaisingEvents = true;
            watcher.Created += new FileSystemEventHandler(OnChanged);
        }

		/// <summary>
        /// OnChanged
		/// When directory changes,sends command to controller
        /// </summary>
        /// <param name="source">object that sent the event</param>
        /// <param name="e">file system event</param>        
        private void OnChanged(object source, FileSystemEventArgs e)
        {	
			m_logging.Log("change detected in directory: " +e.FullPath, MessageTypeEnum.INFO);
            //send command to controller to add a file
            foreach(string filter in this.filterTypes)
            {
                if (Path.GetExtension(e.FullPath).Equals(filter))
                {
					string[] args = { e.FullPath,e.Name };
					CommandRecievedEventArgs cmd = new CommandRecievedEventArgs((int)CommandEnum.NewFileCommand, args, this.m_path);
					this.OnCommandRecieved(this, cmd);
                    return;
                }
            }
        }

        /// <summary>
        /// OnCommandRecieved
        /// When gets command-check if command is meant for its directory, if yes – handle command
        /// </summary>
        /// <param name="sender">object that sent the event</param>
        /// <param name="e">CommandRecievedEventArgs object</param>
        public void OnCommandRecieved(object sender, CommandRecievedEventArgs e)
        {
             if (e.RequestDirPath.Equals("*")||this.m_path.Equals(e.RequestDirPath) )
            {
                // recived clodse command
                if (e.CommandID == (int) CommandEnum.CloseCommand)
                {
                    m_logging.Log("closeHandler command recieved " + e.RequestDirPath, MessageTypeEnum.INFO);
                    this.closeHandler();
                    return;
                }

                bool result;
                //send command to controller to add a file
                string cmdResult = this.m_controller.ExecuteCommand(e.CommandID, e.Args, out result);
                if (result != true)
                {
                    //notify logger on success
                    m_logging.Log("added file " + e.Args[1] + " succesfully at: "+ cmdResult, MessageTypeEnum.INFO);
                }
                else 
				{     //notify logger on failure
                    m_logging.Log("ERROR: faild to add  " + e.Args[1] + "  file reason: "+ cmdResult, MessageTypeEnum.FAIL);
                }
            }
        }

		/// <summary>
        /// closeHandler
        /// closing handler by making the FileSystemWatcher to stop listening to directories
        /// and raises the DirectoryClose event
        /// </summary>
        /// <param name="path"></param>
        public void closeHandler()
        {
            //close FileSystemWatcher and invoke DirectoryClose event
            watcher.EnableRaisingEvents = false;
            DirectoryClose?.Invoke(this, new DirectoryCloseEventArgs(this.m_path, "Closed Directory at:" + this.m_path + " "));
        }
    }
}
