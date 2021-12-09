#include <iostream>
#include "../include/Task.h"


Task::Task(int number,ConnectionHandler &connectionHandler ,massageEncoderDecoder &m, bool &isLogin, bool &isTerminated):
        _id(number), connectionHandler(connectionHandler), m(m),isLogin(isLogin),isTerminated(isTerminated){};

void Task::operator()(){
    while (!isTerminated) {
        const short bufsize = 1024;
        char buf[bufsize];

        std::cin.getline(buf, bufsize);

        std::string line(buf);

        if(isLogin && line == "LOGOUT"){
            isTerminated = true;
        }

        int m_size=0;
        char *c = m.encode(line,&m_size);

        if (!connectionHandler.sendBytes(c, m_size)) {
            std::cout << "Disconnected. Exiting..." << std::endl;
            break;
        }
    }
};
