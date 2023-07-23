using Commands.Infrastructure;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ImageWeb.Models
{
    public class LogsModel
    {
        private static List<MessageRecievedEventArgs> logs = new List<MessageRecievedEventArgs>();
        private ConnectionModel Connection_model;
        private bool got_full_log = false;
        private object locker;
        /// <summary>
        /// LogsModel
        /// constructor
        /// </summary>
        /// 
        public LogsModel()
        {
            Connection_model = ConnectionModel.CreateConnection();
            Connection_model.InfoRecieved += addLogInfo;
            logs = new List<MessageRecievedEventArgs>();
            this.got_full_log = false;
            locker = new object();
            SendLogCmdToServer();
        }
        /// <summary>
        /// SendLogCmdToServer
        /// sends LogCommand to server
        /// </summary>
        /// 
        public void SendLogCmdToServer()
        {
            if (!got_full_log) { 
            string[] args = { };
            CommandRecievedEventArgs e = new CommandRecievedEventArgs(CommandEnum.LogCommand, args, "NO_PATH");
                Connection_model.SendToServer(this, e);
            }
        }

        /// <summary>
        /// GetLogsList
        /// gets list of logs from server
        /// <return name= returned_list>list of logs</return>
        /// </summary>
        /// 
        public List<MessageRecievedEventArgs> GetLogsList
        {
            get
            {
                List<MessageRecievedEventArgs> returned_list = new List<MessageRecievedEventArgs>();
                List<MessageRecievedEventArgs> temp_list;
                lock (locker)
                {
                    temp_list = new List<MessageRecievedEventArgs>(logs);
                }
                foreach (MessageRecievedEventArgs log in temp_list)
                {
                  returned_list.Add(new MessageRecievedEventArgs { Status = log.Status, Message = log.Message });
                }
                return returned_list;
            }
        }
        /// <summary>
        /// GetMessageType
        /// get type of log message
        /// </summary>
        /// <param name="s">message</param>
        /// <returns name = "type">type of message</returns>
        /// 
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
        /// addLogInfo
        /// adds log to list
        /// </summary>
        /// <param name="sender">sender</param>
        /// <param name="e">event</param>
        /// 
        public void addLogInfo(object sender, CommandRecievedEventArgs e)
        {
            lock (locker)
            {
                if (e.CommandName.Equals("LogCommand"))
                {
                    string[] logs_from_server = e.Args;
                    // makes sure that no logs are added until we recive the full log from server.
                    if (logs_from_server.Length > 1)
                    {
                        Debug.WriteLine("**** In ImageWeb *******LOG Modal, ************** got from server FULL LOG :" + e.ToString());
                        this.got_full_log = true;
                    }
                    if (got_full_log)
                    {
                        Debug.WriteLine("**** In ImageWeb *******LOG Modal, 1 ************** got from server :" + e.ToString());
                        // adds all the logs to the log list
                        for (int i = 0; i < logs_from_server.Length; i++)
                        {
                            //  logs_from_server[i]  = { Status + " & " + Message };
                            string[] splited_log_entery = logs_from_server[i].Split('&');
                            MessageTypeEnum message_type = GetMessageType(splited_log_entery[0].Replace(" ", ""));
                            string message = splited_log_entery[1];   
                            Debug.WriteLine("**** In ImageWeb *******LOG Modal, ADDING  TYPE[" + i + "]" + splited_log_entery[0] + "Message[" + i + "] " + splited_log_entery[1]);
                            MessageRecievedEventArgs logToAdd = new MessageRecievedEventArgs() { Status = message_type, Message = message };
                            // add to the logs list
                           logs.Add(logToAdd);
                        }
                    }
                }
            }
        }
    }
}