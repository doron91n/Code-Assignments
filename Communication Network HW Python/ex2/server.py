import os
import sys
import socket
from socket import socket, AF_INET, SOCK_STREAM, timeout

MAX_SIZE = 1024  # socket packets transfer max buffer size
CONNECTION_CLOSE = "Connection: close"
CONNECTION_ALIVE = "Connection: keep-alive"
CONTENT_LENGTH = "Content-Length:"
REDIRECT = "GET /redirect HTTP/1.1"
GET="GET"
HTTP="HTTP/1.1"
MSG_END = "\r\n\r\n"
HTTP_200_OK = "HTTP/1.1 200 OK\r\n"
HTTP_404_ERROR = "HTTP/1.1 404 Not Found\r\nConnection: close\r\n\r\n"
HTTP_301_MOVED_FILE = "HTTP/1.1 301 Moved Permanently\r\nConnection: close\r\nLocation: /result.html\r\n\r\n"

'''binds the server to the socket and start server listening loop'''


def server_start():
    server_socket = socket(AF_INET, SOCK_STREAM)  # the server socket
    try:
        source_port = int(sys.argv[1])
    except:
        source_port = 12345
    finally:
        myIP = '0.0.0.0'
        server_socket.bind((myIP, source_port))
        server_socket.listen(10)
        while True:
            try:
                #  accept a new client and handle his request, each request ends with "\r\n\r\n"
                client_socket, client_address = server_socket.accept()
                keep_connection = True
                while keep_connection:
                    if client_socket.fileno() == -1:  # break from loop if socket closed
                        break
                    client_socket.settimeout(1.0)  # set timeout after 1 sec of no activity
                    packet = client_socket.recv(MAX_SIZE)
                    client_socket.settimeout(None)  # set timeout to none
                    temp = packet.decode()
                    while MSG_END not in temp:
                        client_socket.settimeout(1.0)  # set timeout after 1 sec of no activity
                        packet = client_socket.recv(MAX_SIZE)
                        client_socket.settimeout(None)  # set timeout to none
                        temp += packet.decode()
                        if not packet:
                            break
                    print(str(temp))  # print client request
                    message_response, keep_connection, binary_data = decode_client_msg(temp)
                    if message_response is not None:  # send only a valid response to client
                        if binary_data is not None:
                            client_socket.send(message_response.encode()+binary_data)
                        else:
                            client_socket.send(message_response.encode())

                client_socket.close()

            except timeout:  # got timeout after 1 second
                client_socket.close()
            except:
                x=0

'''decode_client_msg - handles client requests of format:  GET [file] HTTP/1.1'''


def decode_client_msg(client_msg):
    try:
        if len(client_msg.strip()) == 0:  # got empty message close connection
            return None, False, None

        if client_msg.__contains__(REDIRECT):  # got redirection message close connection
            return HTTP_301_MOVED_FILE, False, None

        connection_status = CONNECTION_CLOSE
        if client_msg.__contains__(CONNECTION_ALIVE):
            connection_status = CONNECTION_ALIVE

        msg_lines_split = client_msg.split("\n")  # split the message by lines first
        first_line_split = msg_lines_split[0].split()  # split the first line of format GET [file] HTTP/1.1 by space

        if len(first_line_split)==3: # check format for the client msg
            if first_line_split[0].__eq__(GET) and first_line_split[2].__eq__(HTTP):
                file_path = first_line_split[1]
                return build_client_answer(file_path, connection_status)
    except:
        x=0

    return HTTP_404_ERROR, False, None # returns 404 error if client msg not in format


''' returns the client answer in format:
HTTP/1.1 X      where X = "200 OK" | "404 Not Found" | "301 Moved Permanently"
Connection: 
Content-Length: 

Content
'''


def build_client_answer(file_path, connection_status):
    try:
        file_content = get_file(file_path)
        binary_data = None
        if file_content is None:  # file not found at file path return 404 not found msg
            return HTTP_404_ERROR, False, None
        length = len(str(file_content))
        if connection_status.__eq__(CONNECTION_ALIVE):
            connection_s = True
        else:
            connection_s = False
        # file found build message in format
        answer = HTTP_200_OK + connection_status + "\r\n" + CONTENT_LENGTH + " " + str(
            length) + MSG_END
        # if file content is binary, send it separately, otherwise include in answer
        if str(file_content).startswith("b'"):
            binary_data = file_content
        else:
            answer += str(file_content)

        print(answer)
        return answer, connection_s, binary_data
    except:
        x=0
    return HTTP_404_ERROR,False,None        # if we got here, something went wrong return error page


'''  reads the file at .../files/file path None returned if Not found  '''


def get_file(file_path):
    relative_path = os.path.abspath('.') + "/files/"
    if file_path == "/":  # handle index request
        relative_path += "index.html"
    else:
        relative_path += file_path
    try:
        if relative_path.endswith(".ico") or relative_path.endswith(".jpg"):
            file = open(relative_path, "rb")
        else:
            file = open(relative_path, "r")
        file_content = file.read()
        file.close()

    except FileNotFoundError:
        return None
    except PermissionError:
        return None
    except IsADirectoryError:
        return None
    except:
        return None

    return file_content


def main():
    while True:
        try:
            server_start()
        except:
            x=0



if __name__ == "__main__":
    main()
