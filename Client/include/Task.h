#ifndef BOOST_ECHO_CLIENT_TASK_H
#define BOOST_ECHO_CLIENT_TASK_H
#include <iostream>
#include "connectionHandler.h"
#include "massageEncoderDecoder.h"


class Task {
private:
    int _id;
    ConnectionHandler &connectionHandler;
    massageEncoderDecoder &m;
    bool &isLogin;
    bool &isTerminated;

public:
    Task(int number,ConnectionHandler &connectionHandler,massageEncoderDecoder &m,bool &isLogin,bool &isTerminated);

    void operator()();
};
#endif //BOOST_ECHO_CLIENT_TASK_H