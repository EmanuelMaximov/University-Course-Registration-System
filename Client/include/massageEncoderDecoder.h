#ifndef BOOST_ECHO_CLIENT_MASSAGEENCODERDECODER_H
#define BOOST_ECHO_CLIENT_MASSAGEENCODERDECODER_H

#include "./connectionHandler.h"

class massageEncoderDecoder {

public:
    massageEncoderDecoder ();
    std::string decode(ConnectionHandler *connectionHandler);
    char* encode(std::string s, int *);
    std::vector<std::string> split(const std::string& s, char delimiter);
    void shortToBytes(short num, char* bytesArr);
    void addTObytes(char bytearry[],char add[], int *index);
    short bytesToShort(char* bytesArr);

};


#endif