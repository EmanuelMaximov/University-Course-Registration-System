
#include <string>
#include "../include/massageEncoderDecoder.h"
#include <bits/stdc++.h>
#include <boost/algorithm/string.hpp>

using boost::asio::ip::tcp;
using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;
using std::vector;


massageEncoderDecoder::massageEncoderDecoder() {}//empty constructor

char bytesArr[1024] = {0};


char *massageEncoderDecoder::encode(string s, int *p_size) {
    memset(bytesArr, 0, 1024);
    vector<string> split = massageEncoderDecoder::split(s, ' ');
    string first = split[0];
    int val = 0;
    int *index = &val;


    if (first == "ADMINREG") {
        shortToBytes(1, bytesArr);// 1 to bytes
        *index = 2;
        char username[split[1].size() + 0x10]; ///todo: what about pointers?
        strcpy(username, split[1].c_str()); //copying the contents of the string to char array

        char password[split[2].size() + 0x10];
        strcpy(password, split[2].c_str()); //copying the contents of the string to char array

        addTObytes(bytesArr, username, index);
        bytesArr[*index] = 0;
        (*index)++;

        addTObytes(bytesArr, password, index);
        bytesArr[*index] = 0;
        (*index)++;

        *p_size = *index;
        return bytesArr;
    }
    if (first == "STUDENTREG") {
        shortToBytes(2, bytesArr);// 2 to bytes
        *index = 2;
        char username[split[1].size() + 0x10]; ///todo: what about pointers?
        strcpy(username, split[1].c_str()); //copying the contents of the string to char array

        char password[split[2].size() + 0x10];
        strcpy(password, split[2].c_str()); //copying the contents of the string to char array

        addTObytes(bytesArr, username, index);
        bytesArr[*index] = 0;
        (*index)++;

        addTObytes(bytesArr, password, index);
        bytesArr[*index] = 0;
        (*index)++;

        *p_size = *index;
        return bytesArr;
    }
    if (first == "LOGIN") {
        shortToBytes(3, bytesArr);// 3 to bytes
        *index = 2;

        char username[split[1].size() + 0x10]; ///todo: what about pointers?
        strcpy(username, split[1].c_str()); //copying the contents of the string to char array

        char password[split[2].size() + 0x10];
        strcpy(password, split[2].c_str()); //copying the contents of the string to char array

        addTObytes(bytesArr, username, index);
        bytesArr[*index] = 0;
        (*index)++;

        addTObytes(bytesArr, password, index);
        bytesArr[*index] = 0;
        (*index)++;

        *p_size = *index;
        return bytesArr;

    }
    if (first == "LOGOUT") {
        shortToBytes(4, bytesArr);// 4 to bytes
        *index = 2;
        *p_size = *index;
        return bytesArr;
    }
    if (first == "COURSEREG") {
        memset(bytesArr, 0, 1024);
        shortToBytes(5, bytesArr);// 5 to bytes
        *index = 2;

        char coursenum[split[1].size() + 0x10];

        if (split[1].size() == 1) {
            string temporary='0'+split[1];
            strcpy(coursenum, temporary.c_str()); //copying the contents of the string to char array
        } else {
            strcpy(coursenum, split[1].c_str()); //copying the contents of the string to char array
        }
        addTObytes(bytesArr, coursenum, index);
        (*index)++;
        *p_size = *index;
        return bytesArr;
    }
    if (first == "KDAMCHECK") {
        shortToBytes(6, bytesArr);// 6 to bytes
        *index = 2;

        char coursenum[split[1].size() + 0x10];
        if (split[1].size() == 1) {
            string temporary='0'+split[1];
            strcpy(coursenum, temporary.c_str()); //copying the contents of the string to char array
        } else {
            strcpy(coursenum, split[1].c_str()); //copying the contents of the string to char array
        }
        addTObytes(bytesArr, coursenum, index);
        *p_size = *index;
        return bytesArr;
    }
    if (first == "COURSESTAT") {
        shortToBytes(7, bytesArr);// 7 to bytes
        *index = 2;

        char coursenum[split[1].size() + 0x10];
        if (split[1].size() == 1) {
            string temporary='0'+split[1];
            strcpy(coursenum, temporary.c_str()); //copying the contents of the string to char array
        } else {
            strcpy(coursenum, split[1].c_str()); //copying the contents of the string to char array
        }
        addTObytes(bytesArr, coursenum, index);
        *p_size = *index;
        return bytesArr;
    }
    if (first == "STUDENTSTAT") {
        shortToBytes(8, bytesArr);// 8 to bytes
        *index = 2;

        char username[split[1].size() + 0x10];
        strcpy(username, split[1].c_str()); //copying the contents of the string to char array

        addTObytes(bytesArr, username, index);
        bytesArr[*index] = 0;
        (*index)++;
        *p_size = *index;
        return bytesArr;
    }

    if (first == "ISREGISTERED") {
        shortToBytes(9, bytesArr);// 9 to bytes
        *index = 2;

        char coursenum[split[1].size() + 0x10];
        if (split[1].size() == 1) {
            string temporary='0'+split[1];
            strcpy(coursenum, temporary.c_str()); //copying the contents of the string to char array
        } else {
            strcpy(coursenum, split[1].c_str()); //copying the contents of the string to char array
        }
        addTObytes(bytesArr, coursenum, index);
        *p_size = *index;
        return bytesArr;
    }
    if (first == "UNREGISTER") {
        shortToBytes(10, bytesArr);// 10 to bytes
        *index = 2;

        char coursenum[split[1].size() + 0x10];
        if (split[1].size() == 1) {
            string temporary='0'+split[1];
            strcpy(coursenum, temporary.c_str()); //copying the contents of the string to char array
        } else {
            strcpy(coursenum, split[1].c_str()); //copying the contents of the string to char array
        }
        addTObytes(bytesArr, coursenum, index);
        *p_size = *index;
        return bytesArr;
    }
    if (first == "MYCOURSES") {
        shortToBytes(11, bytesArr);// 11 to bytes
        *index = 2;
        *p_size = *index;
        return bytesArr;
    }

    *p_size = *index;
    return 0;
}

//decode
std::string massageEncoderDecoder::decode(ConnectionHandler *connectionHandler) {
    string fullstr = "";
    char b[1024] = {0};
    connectionHandler->getBytes(b, 2); //recieve from the server 2 bytes -- the opcode
    short opcode = bytesToShort(b);

    if (opcode == 12) { //IF ACK
        connectionHandler->getBytes(&b[2], 2); //GIVE ME 2 BYTES
        short o = bytesToShort(&b[2]); //CONVERT TO SHORT THE OPCODE OF THE MESSAGE
        fullstr = "ACK " + std::to_string(o);


        if (o == 9) { //IF ISREGISTERED
            char c = 0;
            connectionHandler->getBytes(&c, 1);
            string str = "";
            if (c != '\0') {
                while (c != '\0') {
                    str += c;
                    connectionHandler->getBytes(&c, 1);

                }
            }
            fullstr = fullstr + "\n" + str;
        }


        if (o == 8)//STUDENTSTAT
        {
            char c = 0;
            string str = "Student: ";
            connectionHandler->getBytes(&c, 1);
            while (c != '\0') {
                str += c;
                connectionHandler->getBytes(&c, 1);
            }
            str = str + "\n" + "Courses: [";
            connectionHandler->getBytes(&c, 1);
            string courserslist = "";
            if (c != '\0') {
                while (c != '\0') {
                    courserslist += c;
                    connectionHandler->getBytes(&c, 1);

                }
                courserslist.erase(courserslist.size() - 1);
                boost::replace_all(courserslist, " ", ",");
            }

            fullstr = fullstr + "\n" + str + courserslist + "]";
        }

        if (o == 7)//COURSESTAT
        {
            char c = 0;
            string str = "Course: (";//Course: (num)
            connectionHandler->getBytes(&c, 1);
            while (c != '\0') {
                str += c;
                connectionHandler->getBytes(&c, 1);

            }
            str = str + ") ";
            connectionHandler->getBytes(&c, 1);
            while (c != '\0') {
                str += c;
                connectionHandler->getBytes(&c, 1);
            }
            str = str + "\n" + "Seats Available: ";
            char m[2] = {0};
            string numOfRegistered = "";
            for (int i = 0; i < 2; i++) {
                connectionHandler->getBytes(&c, 1);
                m[i] = c;
            }
            short t = bytesToShort(m);
            numOfRegistered += std::to_string(t);
            string numOfMax = "";
            for (int i = 0; i < 2; i++) {
                connectionHandler->getBytes(&c, 1);
                m[i] = c;
            }
            t = bytesToShort(m);
            numOfMax += std::to_string(t);

            str = str + numOfRegistered + "/" + numOfMax + "\n" + "Students Registered: [";
            connectionHandler->getBytes(&c, 1);
            string courserslist = "";
            if (c != '\0') {
                while (c != '\0') {
                    courserslist += c;
                    connectionHandler->getBytes(&c, 1);

                }
                courserslist.erase(courserslist.size() - 1);
                boost::replace_all(courserslist, " ", ",");
            }

            fullstr = fullstr + "\n" + str + courserslist + "]";
        }

        if (o == 11 || o == 6) {//KDAMCHECK or MYCOURSES
            char c = 0;
            connectionHandler->getBytes(&c, 1);
            string courserslist = "[";
            if (c != '\0') {
                while (c != '\0') {
                    courserslist += c;
                    connectionHandler->getBytes(&c, 1);

                }
                courserslist.erase(courserslist.size() - 1);
                boost::replace_all(courserslist, " ", ",");

            }
            fullstr = fullstr + "\n" + courserslist + "]";

        }
    }
    if (opcode == 13) {//if ERR
        connectionHandler->getBytes(&b[2], 2); //GIVE ME 2 BYTES
        short o = bytesToShort(&b[2]);
        fullstr = "ERROR " + std::to_string(o);
    }

    return fullstr;
}


short massageEncoderDecoder::bytesToShort(char *bytesArr) {
    short result = (short) ((bytesArr[0] & 0xff) << 8);
    result += (short) (bytesArr[1] & 0xff);
    return result;
}

std::vector<std::string> massageEncoderDecoder::split(const std::string &s, char delimiter) {
    std::vector<std::string> tokens;
    std::string token;
    std::istringstream tokenStream(s);
    while (std::getline(tokenStream, token, delimiter)) {
        tokens.push_back(token);
    }
    return tokens;
}

void massageEncoderDecoder::shortToBytes(short num, char *bytesArr) {
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

void massageEncoderDecoder::addTObytes(char *bytearry, char *add, int *index) {
    for (unsigned int i = 0; i < strlen(add); i++) {
        bytearry[*index] = add[i];
        (*index)++;
    }

}


