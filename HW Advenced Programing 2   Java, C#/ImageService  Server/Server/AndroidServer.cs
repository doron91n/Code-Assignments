using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Threading.Tasks;
using System.Drawing;

namespace ImageService.Server
{
    public  class AndroidServer
    {
        #region Members
        private const int Port = 8000;
        private TcpListener Listener;
        private List<TcpClient> Clients_List;
        #endregion
    /*    static void Main()
        {
            AndroidServer s = new AndroidServer();
            s.Start();
            Console.WriteLine("Hello World!");
            Debug.WriteLine("AndroidServer.");
          

        }*/
        public AndroidServer( )
        {
            Debug.WriteLine("AndroidServer created.");

        }
        public void Start()
        {
            Debug.WriteLine("AndroidServer Start.");
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, Port);
            this.Listener = new TcpListener(ep);
            this.Clients_List = new List<TcpClient>();
            Debug.WriteLine("AndroidServer created list .");

            this.Listener.Start();
             new Task(() => {
                while (true)
                {
                     Debug.WriteLine("AndroidServer LIstening.");
                     Console.WriteLine("AndroidServer LIstening. waiting for line");
                     Thread.Sleep(2 * 10000);
                     String s= Console.ReadLine();
                     Debug.WriteLine("you worte:"+s);
                     Thread.Sleep(2 * 10000);


                     try
                     {
                        TcpClient client = this.Listener.AcceptTcpClient();
                        this.Clients_List.Add(client);
                         // handle client
                         Debug.WriteLine("AndroidServer got new connection, waiting for client operation.");
                         CommunicateWith(client);
                    }
                    catch (Exception e)
                    {

                         Debug.WriteLine("Error: AndroidServer: " + e.Message);
                        continue;
                    }
                }
            }).Start();
        }
        private void CommunicateWith(TcpClient client)
        {
            new Task(() =>
            {
                Debug.WriteLine("Server got new connection.");
                HandelClient(client);
            }).Start();
        }
        public void  HandelClient(TcpClient client)
        {

            Debug.WriteLine("AndroidServer HandelClient for client: " +client.ToString());
            NetworkStream clientStream = client.GetStream();
            byte[] from_client = new byte[4096];
            int bytes_Read;
            bool start = false;
            bool finish = false;
            string pic_name="";
            while (true)
            {
                bytes_Read = 0;
                try
                {
                    //blocks until a client sends a message
                    bytes_Read = clientStream.Read(from_client, 0, 4096);
                    Debug.WriteLine("AndroidServer got new client operation: " + from_client);
                    string client_msg = BitConverter.ToString(from_client);
                    Debug.WriteLine("AndroidServer client_msg: " + client_msg );
                    saveImage(from_client, "GotIt.jpg");
                    /*
                    if (client_msg.Contains("Start Sending Pic"))
                    {
                        string[] splited_msg = client_msg.Split(':');
                        pic_name = splited_msg[1];
                        start = true;
                        finish = false;
                        Logger.Log("AndroidServer started getting new pic: " +pic_name +" msg"+ client_msg + " start:"+start+" finish:"+finish, MessageTypeEnum.INFO);
                    }
                    if (client_msg.Contains("Finish Sending Pic"))
                    {
                        finish = true;
                        Logger.Log("AndroidServer finished getting new pic: " + client_msg + " start:" + start + " finish:" + finish, MessageTypeEnum.INFO);
                    }
                    if (start && finish)
                    {
                        Logger.Log("AndroidServer got all the pic saving it (implement): " + client_msg + " start:" + start + " finish:" + finish, MessageTypeEnum.INFO);
                        finish = false;
                        start = false;
                        saveImage(from_client,pic_name);
                    }*/
                }
                catch
                {
                    Debug.WriteLine("Error: AndroidServer: Error upon reading from client socket");
                    //a socket error has occured
                    break;
                }
                if (bytes_Read == 0)
                {
                    Debug.WriteLine("Error: AndroidServer: Error upon reading from client socket, client disconnected.");
                    //the client has disconnected from the server
                    break;
                }

            }
        }

        public void saveImage(byte[] byte_image, string image_name)
        {
            string image_handler_path = "C:\\Users\\Doron\\Downloads";
            string image_path= image_handler_path+ "\\" +image_name;
            Debug.WriteLine("AndroidServer saving new pic: " + image_name + " at:" + image_path);
            Image image = Image.FromStream(new MemoryStream(byte_image));
            image.Save(image_handler_path);
         }
    }
}
