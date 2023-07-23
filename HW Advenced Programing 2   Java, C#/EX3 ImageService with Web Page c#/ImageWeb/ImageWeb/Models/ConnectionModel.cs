

using Commands.Infrastructure;
using Newtonsoft.Json;
using System;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;
namespace ImageWeb.Models
{
    class ConnectionModel
    {
        #region Members
        public event EventHandler<CommandRecievedEventArgs> InfoRecieved; // event for server answer recived
        private const int Port = 8000;
        private const string Ip = "127.0.0.1";
        private TcpClient client;
        private NetworkStream stream;
        private BinaryWriter writer;
        private BinaryReader reader;
        private Mutex mutex;
        private static ConnectionModel connection_model;
        #endregion

        /// <summary>
        /// Function Name: ConnectionModel(), singleton.
        /// Description: class constructor, this class responsable for comunicating with the server.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        private ConnectionModel()
        {
            mutex = new Mutex();
            // connect to server
            if (client == null)
            {
                IPEndPoint ep = new IPEndPoint(IPAddress.Parse(Ip), Port);
                client = new TcpClient();
                try{ client.Connect(ep); }
                catch (Exception e) { return; }
            }
            ReadFromServer();
        }
        /// <summary>
        /// Function Name: CreateConnection() , static.
        /// Description: class static singleton "constructor", returns a instance of this class.
        /// Arguments: null
        /// </summary>
        /// <returns> connection_model: a instance of this class. </returns>
        public static ConnectionModel CreateConnection()
        {
            // if not already created , create new instance
            if (connection_model == null)
            {
                connection_model = new ConnectionModel();
            }
            // otherwise create new instance
            return connection_model;
        }
        /// <summary>
        /// Function Name: SendToServer()
        /// Description: sends the given command to the server.
        /// Arguments: sender: the object that called this function.
        ///            args: the command to send.
        /// </summary>
        /// <returns> null </returns>
        public void SendToServer(object sender, CommandRecievedEventArgs args )
        {
            if (IsClientConnected())
            {
                Debug.WriteLine("#### In ImageWeb connection Modal, SendToServer sending: " + args.ToString());
                new Task(() =>
                {
                    stream = client.GetStream();
                    writer = new BinaryWriter(stream);
                    string args_json = JsonConvert.SerializeObject(args);
                    try
                    {
                        mutex.WaitOne();
                        writer.Write(args_json);
                        mutex.ReleaseMutex();
                    }
                    catch (Exception e)
                    {
                        mutex.ReleaseMutex();
                        Debug.WriteLine("#### In ImageWeb connection Modal, failed send, Error: " + e.ToString());
                        return;
                    }
                }).Start();
            }
        }
        /// <summary>
        /// Function Name: ReadFromServer()
        /// Description: reads the response from the server and envokes the event InfoRecieved.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public void ReadFromServer()
        {
            Debug.WriteLine("#### In ImageWeb connection Modal, ReadFromServer ");
            new Task(() =>
            {
                if (client.Connected) { 
                stream = client.GetStream();
                reader = new BinaryReader(stream);
                  }
                while (client.Connected)
                {
                    string args;
                    try{args = reader.ReadString(); }
                    catch (Exception e)
                    {
                        Debug.WriteLine("#### In ImageWeb connection modal, failed read, Error: " + e.ToString());
                        break;
                    }
                    CommandRecievedEventArgs from_server = JsonConvert.DeserializeObject<CommandRecievedEventArgs>(args);
                    Debug.WriteLine("#### In ImageWeb connection Modal, ReadFromServer got from server :" + from_server.ToString());
                    InfoRecieved?.Invoke(this, from_server);
                }
            }).Start();
        }
        /// <summary>
        /// Function Name: IsClientConnected()
        /// Description:returns the client (connection) status .
        /// Arguments: null
        /// </summary>
        /// <returns> true if client is connected false otherwise. </returns>
        public bool IsClientConnected()
        {
            return this.client.Connected;
        }
        /// <summary>
        /// Function Name: CloseConnection()
        /// Description: closes the client connection.
        /// Arguments: null
        /// </summary>
        /// <returns> null </returns>
        public void CloseConnection()
        {
            //close client
            client.Close();
        }
    }
}
