

using Commands.Infrastructure;
using System;

namespace ImageService.Logging
{
    public interface ILoggingService
    {
        event EventHandler<MessageRecievedEventArgs> MessageRecieved;
        event EventHandler<CommandRecievedEventArgs> NotifyServerClients;
        void Log(string message, MessageTypeEnum type);           // Logging the Message
    }
}
