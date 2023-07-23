import sys
import time
from socket import socket, AF_INET, SOCK_DGRAM

server_socket = socket(AF_INET, SOCK_DGRAM)  # the server socket
ips_map = {}  # initialize a new map for all the static ips entries
#  key=site_name value=site_entry(site_name,site_ip,TTL)

'''binds the server to the socket and start server listening loop'''


def server_start():
    """ arguments = [myPort] [parentIP] [parentPort] [ipsFileName] ,
     if [parentIP]= -1 [parentPort] = -1 then there is no parent server """
    try:
        my_port = int(sys.argv[1])
        parent_IP = sys.argv[2]
        parent_port = int(sys.argv[3])
        ips_file_name = sys.argv[4]
    except:
        my_port = 15244
        parent_IP = '-1'
        parent_port = -1
        ips_file_name = 'ips.txt'
    finally:
        my_ip = "0.0.0.0"
        parent_info = (parent_IP, parent_port)
        clean_ips_file(ips_file_name)  # clean ips file from expired TTL ,clear and reload the static ips map
        server_socket.bind((my_ip, my_port))
    while True:
        data, sender_info = server_socket.recvfrom(2048)
        decode_msg(data, sender_info, parent_info, ips_file_name)


'''simple ips map print function'''


def print_ips_map():
    for site_name, site_entry in ips_map.items():
        print("Key(site_name)= ", site_name, " value(site_entry): ", site_entry)


''' decodes and handels clients qurey '''


def decode_msg(message, sender_info, parent_info, ips_file_name):
    # sender sends queries in form:  "query_site_name" , gets answer in form "query_site_name,query_site_ip,TTL"
    query_decoded = message.decode()
    query_splited = query_decoded.split(",")
    answer = search_ips_file_for_query(query_splited[0], parent_info, ips_file_name)
    # send answer to query client
    server_socket.sendto(answer.encode(), sender_info)  # send message encoded as bytes


''' learn a new ips entry with TTL into ips file for future queries '''


def add_record(record, ips_file_name):
    split_record_line = record.strip().split(",")
    TTL = int(split_record_line[2])
    if TTL > 0:  # a TTL with less then 0 is invalid and wont be added
        TTL_expire_time = int(time.time()) + TTL
        ips_file = open(ips_file_name, 'a')  # open with append mode
        s = "\n"+split_record_line[0] + "," + split_record_line[1] + "," + split_record_line[2] + "," + str(
            TTL_expire_time) + "\n"
        ips_file.write(s)
        ips_file.close()


''' cleans the ips file from entries with expired TTL, rebuild the static ips map'''


def clean_ips_file(ips_file_name):
    ips_map.clear()  # clear the static ips map
    ips_file = open(ips_file_name, 'r')
    file_data = ""
    while True:
        # Get next line from file
        line = ips_file.readline()
        # if line is empty end of file is reached
        if not line:
            break
        # find line that has the client query
        temp_split_line = line.split(",")
        if len(temp_split_line) < 3:  # remove ips entries not in format
            continue
        if len(temp_split_line) == 3:  # don't delete the static lines ( have 3 sections ) , reload into ips_map
            file_data += line
            ips_map[temp_split_line[0]] = line  # add entry into ips map , key=site_name , value = site_entry
        else:  # check TTL, if TTL is valid keep the record else delete
            current_time = int(time.time())
            TTL_finish_time = int(temp_split_line[3])
            if TTL_finish_time > current_time:  # TTL time didnt passed, keep record
                file_data += line
    ips_file.close()
    ips_file = open(ips_file_name, 'w')  # write to file the clean ips data
    ips_file.seek(0, 0)
    ips_file.write(file_data)
    ips_file.close()


'''searches the client query answer in the static ips map if not found checks the ips file for learend unexpierd TTL 
entries, if needed asks parent '''


def search_ips_file_for_query(query, parent_info, ips_file_name):
    clean_ips_file(ips_file_name)  # remove all expired TTL records,reload ips_map
    # check if query answer in static ips map, if so return answer else search ips file
    if query in ips_map.keys():
        return ips_map.get(query)
    ips_file = open(ips_file_name, 'r')  # query answer not found in static, search ips file or ask parent
    while True:
        line = ips_file.readline()  # Get next line from file
        if not line:  # if line is empty end of file is reached
            break
        # find line that has the client query
        temp_split_line = line.split(",")
        if str(temp_split_line[0]) == str(query):  # found the line with the right query
            ips_file.close()
            return temp_split_line[0] + "," + temp_split_line[1] + "," + temp_split_line[2] + "\n"
    ips_file.close()
    # ask parent server for the query,learn answer -- add answer to ips_file
    return search_query_at_parent(query, parent_info, ips_file_name)


''' asks the parent for the given query answer'''


def search_query_at_parent(query, parent_info, ips_file_name):
    #  parent_info = (parent_IP, parent_port)
    if parent_info[0] == "-1" or parent_info[1] == -1:
        return "ERROR_NOT_FOUND,-1,-1,-1\n"
    server_socket.sendto(query.encode(), parent_info)  # ask parent,send message encoded as bytes
    data, sender_info = server_socket.recvfrom(2048)
    answer = data.decode()
    add_record(answer, ips_file_name)  # Learn the new answer, add to ips file for future queries
    return answer


def main():
    server_start()


if __name__ == "__main__":
    main()
