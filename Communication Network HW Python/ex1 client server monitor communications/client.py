import sys
from socket import socket, AF_INET, SOCK_DGRAM

''' a client loop, user input a  query = site_name , and prints given answer = site_ip'''


def client_start():
    s = socket(AF_INET, SOCK_DGRAM)
    try:
        dest_ip = str(sys.argv[1])
        dest_port = int(sys.argv[2])

    except:
        dest_ip = "127.0.0.1"
        dest_port = 15244
    server_info = (dest_ip, dest_port)
    while True:
        msg = input()
        s.sendto(msg.encode(), server_info)
        data, sender_info = s.recvfrom(2048)
        if len(data) > 0:
            answer = data.decode().split(",")
            print("\n" + answer[1] + "\n")


def main():
    client_start()


if __name__ == "__main__":
    main()
