CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lpthread

all: EchoClient
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/Task.o bin/massageEncoderDecoder.o bin/echoClient.o $(LDFLAGS)

EchoClient: bin/connectionHandler.o bin/Task.o bin/massageEncoderDecoder.o bin/echoClient.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/massageEncoderDecoder.o: src/massageEncoderDecoder.cpp
	g++ $(CFLAGS) -o bin/massageEncoderDecoder.o src/massageEncoderDecoder.cpp

bin/Task.o: src/Task.cpp
	g++ $(CFLAGS) -o bin/Task.o src/Task.cpp

bin/echoClient.o: src/echoClient.cpp
	g++ $(CFLAGS) -o bin/echoClient.o src/echoClient.cpp
	
.PHONY: clean
clean:
	rm -f bin/*
