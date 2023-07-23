using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

public class SynchronousSocketListener
{
    private static byte newLine = (byte)'\n';
    private static string endSend = "EndSend"+newLine;

    public static void StartListening()
    {
        // Establish the local endpoint for the socket.  
        IPAddress ipAddress = IPAddress.Any;
        IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 8200);
        Socket listener = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        Socket client_socket;
        try
        {
            // Bind the socket to the local endpoint and   
            listener.Bind(localEndPoint);
            listener.Listen(100);
            // Start listening for connections.  
            while (true)
            {
                Debug.WriteLine("Waiting for a connection...");
                // Program is suspended while waiting for an incoming connection.  
                client_socket = listener.Accept();
                Debug.WriteLine("Accepted new connection...");
                handleClient(client_socket);
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
        }
    }

    public static string readByteArrayToString(Socket client_socket)
    {
        Debug.WriteLine("readByteArrayToString");
        byte[] one_byte = new byte[1] { 0 };
        List<byte> bytes_read = new List<byte>();
        // read input from client until got to endline seperator
        while (one_byte[0] != newLine)
        {
            client_socket.Receive(one_byte, 1, SocketFlags.None);
            if (one_byte[0] != newLine)
            {
                bytes_read.Add(one_byte[0]);
            }
        }
        // convert the size of the byte array to int
        string s = Encoding.ASCII.GetString(bytes_read.ToArray(), 0, bytes_read.ToArray().Length);
        Debug.WriteLine("readByteArrayToString: final read:["+s+"]");
        return s;
    }

    public static void handleClient(Socket client_socket)
    {
        int byte_array_size;
        string image_name;
        Debug.WriteLine("handleClient ");
        while (client_socket.Connected)
        {
            while (true)
            {
              Debug.WriteLine("after while 1...");
            // get the size of the (image) byte array to int
            string byte_array_string = readByteArrayToString(client_socket);
            bool parse_success = int.TryParse(byte_array_string, out byte_array_size);
            Debug.WriteLine("serverMaster: HandleClient client picSize:" + byte_array_size);
            if (!parse_success)
            {
                continue;
            }
            if (byte_array_string.Equals(endSend)) {  Debug.WriteLine("serverMaster: HandleClient client got EndSend next pic read:");break; }
                // get the name of the image
                image_name = readByteArrayToString(client_socket);
                Debug.WriteLine("serverMaster: HandleClient client image_name:" + image_name);

                /*   // get the image
                   byte[] image_bytes= new byte[byte_array_size];
                   int bytesReadFirst = client_socket.Receive(image_bytes, image_bytes.Length,SocketFlags.None);
                   Debug.WriteLine("serverMaster: HandleClient client saveImage bytesReadFirst: "+bytesReadFirst);

                   int tempBytes = bytesReadFirst;
                   while (tempBytes < image_bytes.Length)
                   {
                       tempBytes += client_socket.Receive(image_bytes, image_bytes.Length - tempBytes,SocketFlags.None);
                       Debug.WriteLine("serverMaster: HandleClient client saveImage temp: " + tempBytes);
                   }
                   */
                List<byte> bytes_read = new List<byte>();
                byte[] image_bytes = new byte[byte_array_size];
                int bytesReadFirst = client_socket.Receive(image_bytes, image_bytes.Length, SocketFlags.None);
                bytes_read.AddRange(image_bytes);
                int tempBytes = bytesReadFirst;
                while (tempBytes < image_bytes.Length)
                {
                    tempBytes += client_socket.Receive(image_bytes, image_bytes.Length - tempBytes, SocketFlags.None);
                    Debug.WriteLine("serverMaster: HandleClient client saveImage temp: " + tempBytes);
                    bytes_read.AddRange(image_bytes);
                }
                Debug.WriteLine("serverMaster: HandleClient client saveImage initial array size:"+ byte_array_size+" got array size:"+ bytes_read.ToArray().Length);
                saveImage(bytes_read.ToArray(),  image_name);
            }
        }
    client_socket.Shutdown(SocketShutdown.Both);
            client_socket.Close();
 }
    



    public static void saveImage(byte[] byte_image, string image_name)
    {
        string image_handler_path = "C:\\Users\\Doron\\Downloads\\Test\\To";
        string image_path = image_handler_path +"\\"+ image_name;
        Debug.WriteLine("AndroidServer saving new pic: " + image_name + " at:" + image_path);
        File.WriteAllBytes(Path.Combine(image_handler_path, image_name), byte_image);
        Debug.WriteLine("AndroidServer pic saved: " + " at:" + image_path);
    }
    public static int Main(String[] args)
    {
        StartListening();
        return 0;
    }
}
