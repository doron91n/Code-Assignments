using ImageService.Logging.Modal;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.ServiceProcess;

/// <summary>
/// LoggingService
/// class that handles messages and passes them
/// </summary>
namespace ImageService.Logging
{
    public class LoggingService : ILoggingService
    {
        public event EventHandler<MessageRecievedEventArgs> MessageRecieved;
        private static LoggingService Logger;

        public static LoggingService CreateLogger()
        {
            // if not already created , create new instance
            if (Logger == null)
            {
                Logger = new LoggingService();
            }
            // otherwise create new instance
            return Logger;
        }

        
		/// <summary>
        /// Log
        /// raises the message event and applies the message viewer
        /// </summary>
        /// <param name="message"></param>
        /// <param name="type"></param>
		public void Log(string message, MessageTypeEnum type)
        {
            MessageRecievedEventArgs msg = new MessageRecievedEventArgs();
            msg.Message = message;
            msg.Status = type;
            MessageRecieved?.Invoke(this, msg);
        }

    }
}

