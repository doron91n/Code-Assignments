using ImageService.Controller;
using ImageService.Logging;
using ImageService.Logging.Modal;
using ImageService.Modal;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace ImageService.Server
{
    public class AndroidServer
    {
        #region Members
        private IImageController Controller;
        private ILoggingService Logger;
        private static byte newLine = (byte)'\n';
        private static string endSend = "EndSend" + newLine;
        private TcpListener listener;
        private NetworkStream stream;
        private List<TcpClient> Clients_List;
        private static int port = 8200;
        #endregion
        /// <summary>
        /// AndroidServer
        /// constructor
        /// </summary>
        /// <param name="c">instance of controller</param>
        /// <param name="l">instance of logging service</param>
        public AndroidServer(ILoggingService l, IImageController c)
        {
            this.Controller = c;
            this.Logger = LoggingService.CreateLogger();
            Logger.Log("AndroidServer created.", MessageTypeEnum.INFO);
        }

        /// <summary>
        /// Start
        /// Start listening for new cnnections 
        /// </summary>
        public void Start()
        {
            Logger.Log("AndroidServer Start.", MessageTypeEnum.INFO);
            // Establish the local endpoint for the socket.  
            IPEndPoint localEndPoint = new IPEndPoint(IPAddress.Any, port);
            this.listener = new TcpListener(localEndPoint);
            this.Clients_List = new List<TcpClient>();
           this.listener.Start();
            new Task(() => {
                while (true)
                {
                    try
                    {
                        Debug.WriteLine("Waiting for a connection...");
                        Logger.Log("AndroidServer : Waiting for a connection....", MessageTypeEnum.INFO);
                        TcpClient client = this.listener.AcceptTcpClient();
                        this.Clients_List.Add(client);
                        // handle client
                        Logger.Log("AndroidServer: Accepted new connection", MessageTypeEnum.INFO);
                        Debug.WriteLine("Accepted new connection...");
                        CommunicateWith(client);
                    }
                    catch (Exception e)
                    {
                        Logger.Log("Error: AndroidServer: " + e.Message, MessageTypeEnum.FAIL);
                        continue;
                    }
                }
            }).Start();
        }
        /// <summary>
        /// CommunicateWith
        /// handels clients
        /// </summary>
        /// <param name="client">the client to handle</param>
        private void CommunicateWith(TcpClient client)
        {
            new Task(() =>
            {
                Logger.Log("AndroidServer: Handeling new connection.", MessageTypeEnum.INFO);
                handelClient(client);
            }).Start();
        }
        /// <summary>
        /// handelClient
        /// handels clients
        /// </summary>
        /// <param name="client">the client to handle</param>
        public void handelClient(TcpClient client)
        {
            int byte_array_size;
            string image_name;
            Debug.WriteLine("handleClient client:" + client.ToString());
            new Task(() =>
            {
                while (client.Connected)
                {
                    stream = client.GetStream();
                    while (true)
                    {
                        while (!this.stream.DataAvailable) { }
                        Debug.WriteLine("after while 1...");
                        // get the size of the (image) byte array to int
                        string byte_array_string = readByteArrayToString(client);
                        bool parse_success = int.TryParse(byte_array_string, out byte_array_size);
                        Debug.WriteLine("AndroidServer: HandleClient client picSize:" + byte_array_size);
                        if (byte_array_size < 1) { Debug.WriteLine("serverMaster: HandleClient client got image size 0 breaking"); break; }
                        if (!parse_success)
                        {
                            Debug.WriteLine("AndroidServer: HandleClient, image size parse failed breaking");
                            break;
                        }
                        if (byte_array_string.Equals(endSend)) { Debug.WriteLine("serverMaster: HandleClient client got EndSend next pic read:"); break; }
                        // get the name of the image
                        image_name = readByteArrayToString(client);
                        Debug.WriteLine("AndroidServer: HandleClient client image_name:" + image_name);
                        byte[] image_bytes = new byte[byte_array_size];
                        // get the picture
                        int first_read_bytes_num = stream.Read(image_bytes, 0, image_bytes.Length);
                        int temp_read_bytes_num = first_read_bytes_num;
                        while (temp_read_bytes_num < image_bytes.Length)
                        {
                            temp_read_bytes_num += stream.Read(image_bytes, temp_read_bytes_num, image_bytes.Length - temp_read_bytes_num);
                        }
                        Debug.WriteLine("AndroidServer: HandleClient client saveImage initial array size:" + byte_array_size + " got array size:" + image_bytes.Length);
                        saveImage(image_bytes, image_name);
                    }
                }
                stream.Close();
                client.Close();
            }).Start();
        }

        /// <summary>
        /// readByteArrayToString
        /// reads the data the client sent
        /// </summary>
        /// <param name="client">the client to read from</param>
        public string readByteArrayToString(TcpClient client)
        {
            Debug.WriteLine("AndroidServer: readByteArrayToString");
            stream = client.GetStream();
            byte[] one_byte = new byte[1] { 0 };
            List<byte> bytes_read = new List<byte>();
            // read input from client until got to endline seperator
            while (one_byte[0] != newLine)
            {
                stream.Read(one_byte, 0, 1);
                if (one_byte[0] != newLine)
                {
                    bytes_read.Add(one_byte[0]);
                }
            }
            // convert the size of the byte array to int
            string s = Encoding.ASCII.GetString(bytes_read.ToArray(), 0, bytes_read.ToArray().Length);
            Debug.WriteLine("readByteArrayToString: final read:[" + s + "]");
            return s;
        }
        /// <summary>
        /// saveImage
        /// saves the given image where needed
        /// </summary>
        /// <param name="byte_image">the image to save</param>
        /// <param name="image_name">the image name </param>

        public void saveImage(byte[] byte_image, string image_name)
        {
            string image_handler_path = ConfigReader.CreateConfigReader().HandlersList[0];
            string image_path = image_handler_path + "\\" + image_name;
            Debug.WriteLine("AndroidServer saving new pic: " + image_name + " at:" + image_path);
            File.WriteAllBytes(Path.Combine(image_handler_path, image_name), byte_image);
            Debug.WriteLine("AndroidServer pic saved: " + " at:" + image_path);
        }
        /// <summary>
        /// Stop
        /// functions when the server gets a message to close
        /// </summary>
        public void Stop()
        {
            this.Clients_List.Clear();
            this.listener.Stop();
        }
    }
}
