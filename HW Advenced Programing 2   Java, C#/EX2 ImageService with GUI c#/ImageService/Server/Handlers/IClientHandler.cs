

using Commands.Infrastructure;
using System;
using System.Net.Sockets;

namespace ImageService.Server
{
    public interface IClientHandler
    {
        event EventHandler<CommandRecievedEventArgs> CloseCommandRecieved;
        void HandleClient(TcpClient client,object mutex_lock);
    }
}
