using System.ComponentModel;
using GUI.Model;
using System.Collections.ObjectModel;
using Commands.Infrastructure;
using System;
using System.Windows.Data;
using System.Windows;
using System.Diagnostics;

namespace GUI.Model
{

    class LogsModel

    {
        #region Members
        private ConnectionModel Connection_model;
        private ObservableCollection<MessageRecievedEventArgs> logs_List;
        private bool got_full_log = false;
        public event PropertyChangedEventHandler PropertyChanged;
        public event EventHandler<CommandRecievedEventArgs> SendInfo;
        #endregion

        /// <summary>
        /// Function Name: LogModel()
        /// Description: class constructor, responsable for handling the service logs on client end .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public LogsModel()
        {
            this.logs_List = new ObservableCollection<MessageRecievedEventArgs>();
            BindingOperations.EnableCollectionSynchronization(logs_List, logs_List);
            Connection_model = ConnectionModel.CreateConnection();
            SendInfo += Connection_model.SendToServer;
            Connection_model.InfoRecieved += addLogInfo;
            System.Threading.Thread.Sleep(500);
            SendLogCmdToServer();  
        }
        /// <summary>
        /// Function Name: SendLogCmdToServer()
        /// Description: sends a log request command to server.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        private void SendLogCmdToServer()
        {
            string[] args = { };
            CommandRecievedEventArgs e = new CommandRecievedEventArgs(CommandEnum.LogCommand, args, "NO_PATH");
            SendInfo?.Invoke(this, e);
        }
        /// <summary>
        /// Function Name: addLogInfo()
        /// Description: adds the given logs (from server) onto the log page.
        /// Arguments: sender: the object that called this function.
        ///            e: the log command respons from server.
        /// </summary>
        /// <returns> null </returns>
        public void addLogInfo(object sender, CommandRecievedEventArgs e)
        {
            if (e.CommandName.Equals("LogCommand"))
            {
                string[] logs_from_server = e.Args;
                // makes sure that no logs are added until we recive the full log from server.
                if (logs_from_server.Length > 1)
                {
                    Debug.WriteLine("**** In GUI *******LOG Modal, ************** got from server FULL LOG :" + e.ToString());
                    this.got_full_log = true;
                }
                if (got_full_log)
                {
                    Debug.WriteLine("**** In GUI *******LOG Modal, 1 ************** got from server :" + e.ToString());
                    // adds all the logs to the log list
                    for (int i = 0; i < logs_from_server.Length ; i++)
                    {
                        //  logs_from_server[i]  = { Status + " & " + Message };
                        string[] splited_log_entery = logs_from_server[i].Split('&');
                        MessageTypeEnum message_type = GetMessageType(splited_log_entery[0].Replace(" ",""));
                        string message = splited_log_entery[1];
                        Debug.WriteLine("**** In GUI *******LOG Modal, ADDING  TYPE["+i+"]" + splited_log_entery[0] + "Message[" + i + "] "+ splited_log_entery[1]);
                        MessageRecievedEventArgs logToAdd = new MessageRecievedEventArgs() { Status = message_type, Message = message };
                        // add to the logs list
                        Application.Current.Dispatcher.Invoke(new Action(() => { this.logs_List.Add(logToAdd); }));
                    }
                    OnPropertyChanged("LogsList");
                }
            }
        }
        /// <summary>
        /// Function Name: GetMessageType()
        /// Description: returns the type of the given log message (FAIL/INFO/WARNING).
        /// Arguments: s: the string with the message type name.
        /// </summary>
        /// <returns> type: MessageTypeEnum (FAIL/INFO/WARNING) </returns>
        private MessageTypeEnum GetMessageType(string s)
        {
            MessageTypeEnum type = new MessageTypeEnum();
            switch (s)
            {
                case "FAIL":
                    type = MessageTypeEnum.FAIL;
                    break;
                case "INFO":
                    type = MessageTypeEnum.INFO;
                    break;
                case "WARNING":
                    type = MessageTypeEnum.WARNING;
                    break;
                default:
                    type = MessageTypeEnum.WARNING;
                    break;
            }
            return type;
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
        /// <summary>
        /// Function Name: getLogsList
        /// Description: a getter and setter for this class logs list .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public ObservableCollection<MessageRecievedEventArgs> getLogsList
        {
            get { return this.logs_List; }
            set
            {
                logs_List = value;
                OnPropertyChanged("LogsList");
            }
        }
    }
}