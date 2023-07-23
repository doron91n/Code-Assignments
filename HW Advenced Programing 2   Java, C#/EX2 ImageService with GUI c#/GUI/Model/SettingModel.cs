
using System.ComponentModel;
using GUI.Model;
using System.Collections.ObjectModel;
using Commands.Infrastructure;
using System;
using System.Windows.Data;
using System.Windows;
using System.Diagnostics;
using System.Windows.Threading;
using System.Threading;

namespace GUI.Model
{
    class SettingModel : INotifyPropertyChanged
    {
        #region Members
        private ConnectionModel  Connection_model;
        private string OutPutDirectory_name;
        private string SourceName_name;
        private string LogName_name;
        private string ThumbSize_size;
        private string Selected_handler;
        private ObservableCollection<string> handlers_list;
        public event PropertyChangedEventHandler PropertyChanged;
        public event EventHandler<CommandRecievedEventArgs> SendCommand;
        #endregion
        /// <summary>
        /// Function Name: SettingModel()
        /// Description: class constructor, responsable for handling the service settings(config).
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public SettingModel()
        {
            // create the connection with server and ask for appconfig details
            this.Connection_model = ConnectionModel.CreateConnection();
            handlers_list = new ObservableCollection<string>();
            BindingOperations.EnableCollectionSynchronization(handlers_list, handlers_list);
            this.Connection_model.InfoRecieved += GetConfigFromServer;
            SendCommand += this.Connection_model.SendToServer;
            System.Threading.Thread.Sleep(10);
            SendGetConfigCmd();
        }
        /// <summary>
        /// Function Name: GetConfigFromServer()
        /// Description: invoked when server sends a response, close handler or add the service config .
        /// Arguments: sender: the object calling this function.
        ///            args: the response from server.
        /// </summary>
        /// <returns> null </returns>
        public void GetConfigFromServer(object sender, CommandRecievedEventArgs args)
        {
            if (args.CommandName.Equals("GetConfigCommand"))
            {
                this.SetSettingInfo(args);
            }
            if (args.CommandName.Equals("CloseCommand"))
            {
                this.RemoveSelectedHandler(args);
            }
        }
        /// <summary>
        /// Function Name: SendGetConfigCmd()
        /// Description: sends a command to server requesting the service settings(config).
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public void SendGetConfigCmd()
        {
            CommandRecievedEventArgs get_Config_cmd = new CommandRecievedEventArgs(CommandEnum.GetConfigCommand, new string[] { },"NO_PATH");
            SendCommand?.Invoke(this, get_Config_cmd);
        }
        /// <summary>
        /// Function Name: SendCloseHandlerCmd()
        /// Description:  sends a command to server requesting the closing of a selected handler.
        /// Arguments: handler_to_close_path: the path for the handler to remove.
        /// </summary>
        /// <returns> null </returns>
        public void SendCloseHandlerCmd(string handler_to_close_path)
        {
            CommandRecievedEventArgs close_handler_cmd = new CommandRecievedEventArgs(CommandEnum.CloseCommand, new string[] { handler_to_close_path }, handler_to_close_path);
            SendCommand?.Invoke(this, close_handler_cmd);
        }
        /// <summary>
        /// Function Name: SetSettingInfo()
        /// Description: responsable for putting the service settings(config) on the setting window.
        /// Arguments: config_from_server: the service configuration to put on the setting window.
        /// </summary>
        /// <returns> null </returns>
        public void SetSettingInfo(CommandRecievedEventArgs config_from_server)
        {
            // Arg[0] = OutputDir ,  Arg[1] = SourceName , Arg[2] = LogName , Arg[3] = ThumbnailSize ,Arg[4 -> n] = Handler
            this.OutputDirectory = config_from_server.Args[0];
            this.SourceName = config_from_server.Args[1];
            this.LogName = config_from_server.Args[2];
            this.ThumbSize = config_from_server.Args[3];
            // build the handlers list
            handlers_list = new ObservableCollection<string>();
            BindingOperations.EnableCollectionSynchronization(handlers_list, handlers_list);
            for (int i = 4; i < config_from_server.Args.Length; i++)
            {
                Application.Current.Dispatcher.Invoke(new Action(() => { handlers_list.Add(config_from_server.Args[i]); }));
            }
        }
        /// <summary>
        /// Function Name: RemoveSelectedHandler()
        /// Description: after the server closes a handler , remove it from handlers list.
        /// Arguments: e: the response from server for the close command that was sent.
        /// </summary>
        /// <returns> null </returns>
        public void RemoveSelectedHandler(CommandRecievedEventArgs e)
        {
            if (e.CommandName.Equals("CloseCommand"))
            {
                // the path of the directory
                string dir_to_remove = e.RequestDirPath;
                Debug.WriteLine("********&&&&&&&    RemoveSelectedHandler got : " + e.ToString());
                Application.Current.Dispatcher.BeginInvoke(new Action(() => { handlers_list.Remove(dir_to_remove); }));
             //   OnPropertyChanged("Directories"); this line creates an exception
            }
        }

        /// <summary>
        /// Function Name: OnPropertyChanged()
        /// Description: activates event for the class members upon change detected .
        /// Arguments: name: the property name.
        /// </summary>
        /// <returns> null </returns>
        protected void OnPropertyChanged(string name)
        {
            if (PropertyChanged != null)
                PropertyChanged(this, new PropertyChangedEventArgs(name));
        }
        #region Getters & Setters
        /// <summary>
        /// Function Name: OutputDirectory
        /// Description: a getter and setter for OutputDirectory.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string OutputDirectory
        {
            get { return OutPutDirectory_name; }
            set
            {
                OutPutDirectory_name = value;
                OnPropertyChanged("OutPutDirectory");
            }
        }
        /// <summary>
        /// Function Name: SourceName
        /// Description: a getter and setter for SourceName.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string SourceName
        {
            get { return SourceName_name; }
            set
            {
                SourceName_name = value;
                OnPropertyChanged("SourceName");
            }
        }
        /// <summary>
        /// Function Name: LogName
        /// Description: a getter and setter for LogName .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string LogName
        {
            get { return LogName_name; }
            set
            {
                LogName_name = value;
                OnPropertyChanged("LogName");
            }
        }
        /// <summary>
        /// Function Name: ThumbSize
        /// Description: a getter and setter for ThumbSize .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string ThumbSize
        {
            get { return ThumbSize_size; }
            set
            {
                ThumbSize_size = value;
                OnPropertyChanged("ThumbSize");
            }
        }
        /// <summary>
        /// Function Name: HandlersList
        /// Description: a getter and setter for this class Handlers list .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public ObservableCollection<string> HandlersList
        {
            get { return this.handlers_list; }
            set
            {
                handlers_list = value;
                OnPropertyChanged("HandlersList");
            }
        }
        /// <summary>
        /// Function Name: SelectedHandler
        /// Description: a getter and setter for this class selected handler to remove .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public string SelectedHandler
        {
            get { return this.Selected_handler; }
            set
            {
                this.Selected_handler = value;
                OnPropertyChanged("SelectedHandler");
            }
        }
        #endregion
    }
}
