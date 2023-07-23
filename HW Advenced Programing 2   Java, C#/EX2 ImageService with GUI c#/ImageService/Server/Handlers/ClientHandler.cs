using Commands.Infrastructure;
using ImageService.Controller;
using ImageService.Logging;
using Newtonsoft.Json;
using System;
using System.Diagnostics;
using System.IO;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;

namespace ImageService.Server
{
    class ClientHandler : IClientHandler
    {
        #region Members
        private NetworkStream Stream;
        private BinaryWriter Writer;
        private BinaryReader Reader;
        private IImageController Controller;
        private ILoggingService Logger;
        private bool got_log_cmd = false;
        #endregion
        public event EventHandler<CommandRecievedEventArgs> CloseCommandRecieved;
        /// <summary>
        /// Function Name: ClientHandler() 
        /// Description: class constructor, responsable for handling the service clients .
        /// Arguments: l: the logger service
        ///            c: the image controller
        /// </summary>
        /// <returns> null </returns>
        public ClientHandler(ILoggingService l, IImageController c)
        {
            this.Controller = c;
            this.Logger = l;
         }
        /// <summary>
        /// Function Name: HandleClient() 
        /// Description: responsable for handling the comunication with the service clients .
        /// Arguments: client: the client to handle
        ///            mutex_locker: the object that called this function
        /// </summary>
        /// <returns> null </returns>
        public void HandleClient(TcpClient client, object mutex_locker)
        {
            new Task(() =>
            {
                Stream = client.GetStream();
                Reader = new BinaryReader(Stream);
                Writer = new BinaryWriter(Stream);
                // as long as the client is connected we handle his requests (commmands).
                while (client.Connected)
                {
                    string from_client;
                    // read a message from the client
                    try { from_client = Reader.ReadString();  }
                    catch (Exception e){ break;}
                    bool result;
                    CommandRecievedEventArgs cmd_from_client = JsonConvert.DeserializeObject<CommandRecievedEventArgs>(from_client);
                    Logger.Log("Client sent command: " + cmd_from_client.CommandName + ", arg: " +
    cmd_from_client.Args.ToString()+" path: "+ cmd_from_client.RequestDirPath, MessageTypeEnum.INFO);
                    // if client sent a getConfig/log commands
                    if (cmd_from_client.CommandID != CommandEnum.CloseCommand)
                    {
                        // sends logs to client after he asked for the full log
                        if(cmd_from_client.CommandID== CommandEnum.LogCommand){this.got_log_cmd = true;}
                        string to_client = Controller.ExecuteCommand(cmd_from_client.CommandID, cmd_from_client.Args, out result);
                        // if the command executed correctly send to client what he asked for. 
                        if (result)
                        {
                            try
                            {
                                lock (mutex_locker)
                                {
                                    Writer.Write(to_client);
                                }
                            }
                            catch (Exception e)
                            {
                                Logger.Log("Error: Failed sending to client, Client disconnected.", MessageTypeEnum.INFO);
                                break;
                            }
                            Logger.Log("Executed " + cmd_from_client.CommandName +" command and sent needed info to the client.", MessageTypeEnum.INFO);
                        }
                        else
                        {
                            Logger.Log("Error: Failed executing command: " + cmd_from_client.CommandName,
                                MessageTypeEnum.FAIL);
                        }
                    }
                    else
                    {
                        // client sent a close command for a specific directory
                        Logger.Log("Recived command: " + cmd_from_client.CommandName +
                            ", with arguments: " + cmd_from_client.Args.ToString() + ", to directory: " +
                            cmd_from_client.RequestDirPath, MessageTypeEnum.INFO);
                        // activates the close command
                        this.CloseCommandRecieved?.Invoke(this, cmd_from_client);
                    } } }).Start();
        }
    }
}
