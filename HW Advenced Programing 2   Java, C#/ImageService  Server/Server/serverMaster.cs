

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;


namespace Server
{
    public class serverMaster 
    {
        #region Members
        private NetworkStream stream;
        private const int MAXREAD = 50000000;
        private int serverPort = 8200;
        private string ip = "127.0.0.1";
        private TcpListener listener;
        private List<TcpClient> clients;
        private object locker;
        #endregion
        /*
        public static int Main(String[] args)
        {
            serverMaster s= new serverMaster();
            return 0;
        }*/
        public serverMaster()
        {
            this.locker = new object();
            Debug.WriteLine("serverMaster: constructor");
            Start();
        }


        public void Start()
        {
            Debug.WriteLine("serverMaster: start");

            // create the client list
            clients = new List<TcpClient>();
            // connect to the port
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, serverPort);
            //IPEndPoint ep = new IPEndPoint(IPAddress.Any, serverPort);
            listener = new TcpListener(ep);
            listener.Start();
            ///new Task(() => {
            ///                 
            while (true)
                {
                Debug.WriteLine("serverMaster: waiting for new client");
                try
                {
                    // accept new clients
                    Debug.WriteLine("serverMaster: try accept new client");

                    TcpClient client = listener.AcceptTcpClient();
                    Debug.WriteLine("serverMaster:  accepted new client");

                    clients.Add(client);
                        // handle client
                        Debug.WriteLine("serverMaster: got new client");
                        communicate(client);
                    }
                    catch (Exception e)
                    {
                        Debug.WriteLine(e.Message);
                        continue;
                    }
                }
         //   }).Start();
        }

        public void communicate(TcpClient client)
        {
            Debug.WriteLine("serverMaster: communicate with client");

            new Task(() =>
            {
             HandleClient(client, locker);
            }).Start();
        }
        public void HandleClient(TcpClient client, object locker)
        {
            Debug.WriteLine("serverMaster: HandleClient client");

            stream = client.GetStream();
            // as long as the client is connected
            while (client.Connected)
            {
                Debug.WriteLine("serverMaster: HandleClient client is connected");

                while (true)
                {

                    byte[] thisByte = new byte[1] { 0 };
                    List<byte> currBytes = new List<byte>();

                    try
                    {
                        while (thisByte[0] != (byte)'\n')
                        {
                            this.stream.Read(thisByte, 0, 1);
                            if (thisByte[0] != (byte)'\n')
                            {
                                currBytes.Add(thisByte[0]);
                            }
                        }

                        // convert to the size of the picture to int
                        string picStr = Encoding.ASCII.GetString(currBytes.ToArray(), 0, currBytes.ToArray().Length);
                        Debug.WriteLine("serverMaster: HandleClient client picStr:" + picStr);

                        int picSize;
                        bool successful = int.TryParse(picStr, out picSize);
                        Debug.WriteLine("serverMaster: HandleClient client picSize:" + picSize);

                        if (!successful)
                        {
                            continue;
                        }
                        
                        // if the string is End\n we reached the end of the current picture
                        if (picStr.Equals("End\n")) { break; }

                        // get the name of the picture
                        thisByte[0] = 0;
                        currBytes = new List<byte>();
                        while (!this.stream.DataAvailable) { }
                        while (thisByte[0] != (byte)'\n')
                        {
                            this.stream.Read(thisByte, 0, 1);
                            if (thisByte[0] != (byte)'\n' &&
                                thisByte[0] != 0)
                            {
                                currBytes.Add(thisByte[0]);
                            }
                        }
                        // convert to string
                        string picName = Encoding.ASCII.GetString(currBytes.ToArray(), 0, currBytes.ToArray().Length);

                        // get the picture
                        byte[] bytes = new byte[picSize];
                        int bytesReadFirst = stream.Read(bytes, 0, bytes.Length);
                        int tempBytes = bytesReadFirst;
                        while (tempBytes < bytes.Length)
                        {
                            tempBytes += stream.Read(bytes, tempBytes, bytes.Length - tempBytes);
                        }

                         // save the image
                        string directory = "C:\\Users\\Doron\\Downloads";
                        File.WriteAllBytes(Path.Combine(directory, picName), bytes);
                        Debug.WriteLine("Saved image from Application client");
                    }
                    catch (Exception e)
                    {
                        Debug.WriteLine("Error reading from Application client. Exiting client handler");
                        break;
                    }
                }
            }
            stream.Close();
            client.Close();
        }
    }
}