using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel.DataAnnotations;
using Commands.Infrastructure;
using ImageWeb.Events;

namespace ImageWeb.Models
{
    public class ConfigModel
    {

        private ConnectionModel m_Connection;
        public event EventHandler<CommandRecievedEventArgs> SendCommand;
        public event EventHandler<CommandRecievedEventArgs> DeletedHandler;

        public event EventHandler<PhotoEvent> sendPath;
        /// <summary>
        /// ConfigModel
        /// constructor
        /// </summary>
        public ConfigModel()
        {
            HandlersList = new List<DirectoryModel>();
            m_Connection = ConnectionModel.CreateConnection();
            // sign to the event of receive info from the server
            m_Connection.InfoRecieved += GetConfigFromServer;
            SendCommand += m_Connection.SendToServer;
            System.Threading.Thread.Sleep(50);
            // initialize the fields
            this.SendGetConfigCmd();
        }
        public bool InfoReceived { private set; get; }
        /// <summary>
        /// SendGetConfigCmd
        /// send GetConfig command to server
        /// </summary>
        public void SendGetConfigCmd()
        {
            CommandRecievedEventArgs get_Config_cmd = new CommandRecievedEventArgs(CommandEnum.GetConfigCommand, new string[] { }, "NO_PATH");
            SendCommand?.Invoke(this, get_Config_cmd);
        }
        /// <summary>
        /// SendCloseHandlerCmd
        /// send CloseCommand to server to close handler
        /// </summary>
        /// <param name="handler_to_close_path">path to handler</param>
        /// 
        public void SendCloseHandlerCmd(string handler_to_close_path)
        {
            CommandRecievedEventArgs close_handler_cmd = new CommandRecievedEventArgs(CommandEnum.CloseCommand, new string[] { handler_to_close_path }, handler_to_close_path);
            SendCommand?.Invoke(this, close_handler_cmd);
        }
        /// <summary>
        /// GetConfigFromServer
        /// get config information from the server
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="args">arguments recieved</param>
        public void GetConfigFromServer(object sender, CommandRecievedEventArgs args)
        {
            if (args.CommandName.Equals("GetConfigCommand"))
            {
                this.SetSettingInfo(args);
            }
            if (args.CommandName.Equals("CloseCommand"))
            {
                this.RemoveFromHandlersList(args);
            }
        }
        /// <summary>
        /// SetSettingInfo
        /// sets information of config
        /// </summary>
        /// <param name="config_from_server">CommandRecieved event</param>
        public void SetSettingInfo(CommandRecievedEventArgs config_from_server)
        {
            // Arg[0] = OutputDir ,  Arg[1] = SourceName , Arg[2] = LogName , Arg[3] = ThumbnailSize ,Arg[4 -> n] = Handler
            OutputDir = config_from_server.Args[0];
            SourceName = config_from_server.Args[1];
            LogName = config_from_server.Args[2];
            ThumbSize = config_from_server.Args[3];
            for (int i = 4; i < config_from_server.Args.Length; i++)
            {
                DirectoryModel dir = new DirectoryModel(config_from_server.Args[i]);
                HandlersList.Add(dir);
            }
            string path = OutputDir;
            PhotoEvent args = new PhotoEvent(path);
            sendPath?.Invoke(this, args);
            // set flag of info received
            this.InfoReceived = true;
        }
        /// <summary>
        /// RemoveFromHandlersList
        /// removes handler from list
        /// </summary>
        /// <param name="e">event</param>
        public void RemoveFromHandlersList(CommandRecievedEventArgs e)
        {
            string[] args = e.Args;
            // get the path of the directory
            string handlerRecieved = args[0];
            foreach (DirectoryModel hand in HandlersList)
            {
                if (hand.DirectoryPath.Equals(handlerRecieved))
                {
                    HandlersList.Remove(hand);
                    this.DeletedHandler?.Invoke(this, e);
                    break;
                }
            }
        }
        #region Getters+Setters
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "OutputDir")]
        public string OutputDir { get; set; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "SourceName")]
        public string SourceName { get; set; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "LogName")]
        public string LogName { get; set; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "ThumbSize")]
        public string ThumbSize { get; set; }
        [Required]
        [DataType(DataType.Text)]
        [Display(Name = "HandlersList")]
        public List<DirectoryModel> HandlersList { get; set; }
        #endregion
    }
}