using Commands.Infrastructure;
using System.Collections.Generic;

namespace ImageService.Logging
{
   public class LogHandler
    {
        private static LogHandler logHandler;
        public List<MessageRecievedEventArgs> LogMsgList { get; }
        /// <summary>
        /// Function Name: LogHandler() ,singleton
        /// Description: class constructor, responsable for handling the service logs on server end .
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        private LogHandler ()
        {
            MessageRecievedEventArgs e = new MessageRecievedEventArgs() { Status = MessageTypeEnum.INFO, Message = "LOG STARTED" };
            MessageRecievedEventArgs e1 = new MessageRecievedEventArgs();
            e1.Status = MessageTypeEnum.FAIL;
            e1.Message = "Fail Check";
            this.LogMsgList = new List<MessageRecievedEventArgs>() { e,e1 };
        }
        /// <summary>
        /// Function Name: CreateLogHandler() , static.
        /// Description: class static singleton "constructor", returns a instance of this class.
        /// Arguments: null
        /// </summary>
        /// <returns> logHandler: a instance of this class. </returns>
        public static LogHandler CreateLogHandler()
        {
            // if not already created create new instance
            if (logHandler == null)
            {
                logHandler = new LogHandler();
            }
            return logHandler;
        }
        /// <summary>
        /// Function Name: AddEntry().
        /// Description:  adds a new log entery to the service logs.
        /// Arguments: sender: the object that called the function
        ///            msg: the new log entry to add.
        /// </summary>
        /// <returns>  null </returns>
        public void AddEntry(object sender, MessageRecievedEventArgs msg)
        {
            this.LogMsgList.Add(msg);
        }
        /// <summary>
        /// Function Name: ClearLog().
        /// Description:  clear the service logs.
        /// Arguments: null
        /// </summary>
        /// <returns>  null </returns>
        public void ClearLog()
        {
            this.LogMsgList.Clear();
        }
    }
}
