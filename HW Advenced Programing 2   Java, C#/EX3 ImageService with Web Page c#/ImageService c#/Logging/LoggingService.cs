

using Commands.Infrastructure;
using System;
/// <summary>
/// LoggingService
/// class that handles messages and passes them
/// </summary>
namespace ImageService.Logging
{
    public class LoggingService : ILoggingService
    {
		
        public event EventHandler<MessageRecievedEventArgs> MessageRecieved;
        public event EventHandler<CommandRecievedEventArgs> NotifyServerClients;
        private static  LoggingService Logger;

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
            string[] args = { type.ToString() + " & " + message };
            CommandRecievedEventArgs notification = new CommandRecievedEventArgs(CommandEnum.LogCommand,args,"NO_PATH");
            NotifyServerClients?.Invoke(this, notification);
        }
    }
}

