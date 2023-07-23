

using Commands.Infrastructure;
using ImageService.Controller;
using ImageService.Logging;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading.Tasks;

namespace ImageService.Server
{
    class ServerTcp
    {
        #region Members
        private const int Port = 8000;
        private const string IP="127.0.0.1";
        private IImageController Controller;
        private ILoggingService Logger;
        private TcpListener Listener;
        private List<TcpClient> Clients_List;
        private object mutex_locker;
        #endregion
        #region Properties
        public event EventHandler<CommandRecievedEventArgs> CloseCommandRecieved;
        #endregion
        /// <summary>
        /// Function Name: ServerTcp() 
        /// Description: class constructor, responsable for handling the service <---> client comunications .
        /// Arguments: l: logger service
        ///            c: service image controller
        /// </summary>
        /// <returns> null </returns>
        public ServerTcp(ILoggingService l, IImageController c)
        {
            this.Controller = c;
            this.Logger = l;
            this.mutex_locker = new object();
        }
        /// <summary>
        /// Function Name: Start() 
        /// Description: starts the server client listener,accepts new clients and handles them .
        /// Arguments: void
        /// </summary>
        /// <returns> null </returns>
        public void Start()
        {
            IPEndPoint ep = new IPEndPoint(IPAddress.Parse(IP), Port);
            this.Listener = new TcpListener(ep);
            this.Clients_List = new List<TcpClient>();
            this.Listener.Start();
            new Task(() => {
                while (true)
                {
                    try
                    {
                        TcpClient client = this.Listener.AcceptTcpClient();
                        this.Clients_List.Add(client);
                        // handle client
                        CommunicateWith(client);
                    }
                    catch (Exception e)
                    {
                        Logger.Log("Error: ServerTcp: "+e.Message, MessageTypeEnum.FAIL);
                        continue;
                    }
                }
            }).Start();
        }
        /// <summary>
        /// Function Name: CommunicateWith() 
        /// Description:comunicates (handels) with the given client .
        /// Arguments: client : the client to comunicate with.
        /// </summary>
        /// <returns> null </returns>
        private void CommunicateWith(TcpClient client)
        {
            new Task(() =>
            {
                IClientHandler client_handler = new ClientHandler(this.Logger, this.Controller);
                Logger.Log("Server got new connection.", MessageTypeEnum.INFO);
                client_handler.CloseCommandRecieved += CloseHandlerCommand;
                client_handler.HandleClient(client,this.mutex_locker);
            }).Start();
        }
        /// <summary>
        /// Function Name: NotifyClients() 
        /// Description:notifies all the clients about commands excution.
        /// Arguments: sender : the object that called this function.
        ///            e: the notification to send to all clients
        /// </summary>
        /// <returns> null </returns>
        public void NotifyClients(object sender, CommandRecievedEventArgs e)
        {
            new Task(() =>
            {
                string notification = JsonConvert.SerializeObject(e);

                foreach (TcpClient client in this.Clients_List)
                {
                    if (client.Connected)
                    {
                        try { SendToClient(client, notification); }
                        catch (Exception e2)
                        {
                            Logger.Log("Error: failed Notifing client reason: " + e2.ToString(), MessageTypeEnum.FAIL);
                            continue;
                        }
                    }
                }
            }).Start();
        }
        /// <summary>
        /// Function Name: SendToClient() 
        /// Description: sends a given string to the client.
        /// Arguments:  client : the client to comunicate with.
        ///            to_send: the notification to send to the client.
        /// </summary>
        /// <returns> null </returns>
        private void SendToClient(TcpClient client, string to_send)
        {
            if (client.Connected)
            {
                NetworkStream stream = client.GetStream();
                BinaryWriter writer = new BinaryWriter(stream);
                lock (this.mutex_locker)
                {
                    writer.Write(to_send);
                }
            }
            else
            {
                // if the client is not connected then remove it from the list of clients
                this.Clients_List.Remove(client);
            }
        }
        /// <summary>
        /// Function Name: NewLogEntry() 
        /// Description: sends a given message to the client.
        /// Arguments: sender : the object that called this function.
        ///            e: the new log entery to send to all clients.
        /// </summary>
        /// <returns> null </returns>
        public void NewLogEntry(object sender, MessageRecievedEventArgs e)
        {
            string[] args = { e.Status.ToString() + " & " + e.Message };
            CommandRecievedEventArgs new_entery = new CommandRecievedEventArgs(CommandEnum.LogCommand, args,"NO_PATH");
            NotifyClients(this, new_entery);
        }
        /// <summary>
        /// Function Name: CloseHandlerCommand() 
        /// Description: invokes a event to close a given handler.
        /// Arguments:   sender : the object that called this function.
        ///            e: the handler to close.
        /// </summary>
        /// <returns> null </returns>
        public void CloseHandlerCommand(object sender, CommandRecievedEventArgs e)
        {
            CloseCommandRecieved?.Invoke(this, e);
        }
        /// <summary>
        /// Function Name: Stop() 
        /// Description: stops the server tcp, stops listening to handlers and clear all clients.
        /// Arguments: void
        /// </summary>
        /// <returns> null </returns>
        public void Stop()
        {
            this.Clients_List.Clear();
            this.Listener.Stop();
        }
    }
    
}
