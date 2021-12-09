#University Course Registration System
##General Description

We all know how bad the university’s “Course Registration System” is, so we implemented a simple “Course Registration System” server and client.

The communication between the server and the client(s) is performed using a binary communication protocol.

The implementation of the server is based on the Thread-Per-Client (TPC) and Reactor servers.
___________________________________________________________________________________________
##Commands

The messages in this protocol are binary numbers, composed of an opcode (short number of two bytes) which indicates the command,
and the data needed for this command (in various lengths).

###The commands supported by the protocol:

Opcode | Operation

1 Admin register (ADMINREG)

2 Student register (STUDENTREG)

3 Login request (LOGIN)

4 Logout request (LOGOUT)

5 Register to course (COURSEREG)

6 Check Kdam course (KDAMCHECK)

7 (Admin)Print course status (COURSESTAT)

8 (Admin)Print student status (STUDENTSTAT)

9 check if registered (ISREGISTERED)

10 Unregister to course (UNREGISTER)

11 Check my current courses (MYCOURSES)

12 Acknowledgement (ACK)

13 Error (ERR)



___________________________________________________________________________________________

##Courses File
Unlike real course registration systems, the courses are specified in one file, according to a specific format (shown below).
The data which the server and clients get during their running are saved in the RAM (in data structures)

###File Structure
The data about the courses (for the server use) are given by a text file which is defined beforehand.
The file name is Courses.txt, and it should be located in the main folder (the project folder).
The file consists of lines, where every line refers to a specific course.
The format of a single line is as follows:

courseNum|courseName|KdamCoursesList|numOfMaxStudents

courseNum: the number of the course (100>= int >= 0)

courseName: the course name (non-empty string)

KdamCoursesList: the list of the Kdam courses. (format: [course1Num, course2Num,...])

numOfMaxStudents: the maximum number of students allowed to register to this course(int >= 5)

Example:

42|How to Train Your Dragon|[43,2,32,39]|25

___________________________________________________________________________________________

##Client Connection
Establishing a client/server connection:
Upon connecting, a client must identify himself to the service.
A new client will issue a Register command with the requested user name and password.
A registered client can then login using the Login command. Once the command is sent,
the server will acknowledge the validity of the username and password.
Once a user is logged in successfully, he can submit other commands.


