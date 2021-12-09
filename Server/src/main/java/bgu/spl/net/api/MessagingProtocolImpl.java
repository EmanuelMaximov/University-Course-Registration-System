package bgu.spl.net.api;

import bgu.spl.net.api.Messages.ACK;
import bgu.spl.net.api.Messages.ADMINREG;
import bgu.spl.net.api.Messages.*;

import java.util.Arrays;
import java.util.LinkedList;

public class MessagingProtocolImpl implements MessagingProtocol<Message> {

    String ConnectedUserName = "";
    private boolean isLoggedin;
    private boolean isAdmin;
    private Database database;

    public MessagingProtocolImpl(Database database) {
        this.database = database;
        isLoggedin = false;
        isAdmin = false;
    }

    @Override
    public Message process(Message msg) {
        //ADMINREG
        if (msg instanceof ADMINREG) {
            String userName = ((REG) msg).getUsername();
            String password = ((REG) msg).getPassword();
            synchronized (database.getAdminsusernameANDpassword()) {
                synchronized (database.getUsernameANDpassword()) {
                    if (!isLoggedin && !database.getAdminsusernameANDpassword().containsKey(userName) && !database.getUsernameANDpassword().containsKey(userName)) {// check if the admin's username exists
                        database.getAdminsusernameANDpassword().put(userName, password);// if not, put username and password in map
                        ACK ack = new ACK(1); //then send ack
                        return ack;
                    } else {
                        ERR error = new ERR(1);
                        return error;
                    }
                }
            }
        }


        //STUDENTREG
        if (msg instanceof STUDENTREG) {
            String userName = ((REG) msg).getUsername();
            String password = ((REG) msg).getPassword();
            synchronized (database.getUsernameANDpassword()) {
                synchronized (database.getAdminsusernameANDpassword()) {
                    if (!isLoggedin && !database.getAdminsusernameANDpassword().containsKey(userName) && !database.getUsernameANDpassword().containsKey(userName)) {// check if the username exists
                        database.getUsernameANDpassword().put(userName, password);// if not, put username and password in map
                        database.getStudentKdamCourses().put(userName, new LinkedList<String>());
                        ACK ack = new ACK(2);
                        return ack;
                    } else {
                        ERR error = new ERR(2);
                        return error;
                    }
                }
            }
        }

        //LOGIN
        if (msg instanceof LOGIN) {
            String userName = ((LOGIN) msg).getUsername();
            String password = ((LOGIN) msg).getPassword();

            synchronized (database.getLoggedInUsers()) {
                if (isLoggedin || database.getLoggedInUsers().contains(userName)) { //if username is already logged return ERR
                    ERR error = new ERR(3);
                    return error;
                }
            }

            synchronized (database.getUsernameANDpassword()) {
                synchronized (database.getAdminsusernameANDpassword()) {
                    boolean check = false;
                    if (database.getUsernameANDpassword().containsKey(userName)) {//if username is not registered
                        if (database.getUsernameANDpassword().get(userName).equals(password)) {
                            check = true;
                        }
                    }
                    if (database.getAdminsusernameANDpassword().containsKey(userName)) {//if username is not registered
                        if (database.getAdminsusernameANDpassword().get(userName).equals(password)) {
                            check = true;
                            isAdmin = true;
                        }
                    }
                    synchronized (database.getLoggedInUsers()) {
                        if (check) {
                            ACK ack = new ACK(3);
                            database.getLoggedInUsers().add(userName);
                            isLoggedin = true;
                            ConnectedUserName = userName;
                            return ack;
                        } else {
                            ERR error = new ERR(3);
                            return error;
                        }
                    }

                }
            }
        }
        //LOGOUT
        if (msg instanceof LOGOUT) {
            synchronized (database.getLoggedInUsers()) {
                if (!isLoggedin) { //if username is not logged in return ERR
                    ERR error = new ERR(4);
                    return error;
                } else {
                    database.getLoggedInUsers().remove(ConnectedUserName);//remove user from login list
                    isLoggedin = false;
                    isAdmin = false;
                    ACK ack = new ACK(4); //new ack message
                    return ack;
                }
            }
        }
        //COURSEREG
        if (msg instanceof COURSEREG) {
            String courseNumber = ((COURSEREG) msg).getContent();
            if (!isLoggedin || isAdmin) { //if username is not logged in or this is the admin, return ERR
                ERR error = new ERR(5);
                return error;
            }
            synchronized (database.getCoursesList()) {
                synchronized (database.getStudentKdamCourses()) {
                    if (database.getCoursesList().containsKey(courseNumber)
                            && !database.getCoursesList().get(courseNumber).getRegisteredUsersName().contains(ConnectedUserName)) {//check if course exists and the student is not already regstered to the course
                        if (database.getCoursesList().get(courseNumber).getnumOfRegisteredStudents()
                                < database.getCoursesList().get(courseNumber).getnumOfMaxStudents()) {//checks if there are free seats
                            boolean check = true;
                            if (database.getCoursesList().get(courseNumber).getKdamCoursesList() != null) {
                                for (int i = 0; i < database.getCoursesList().get(courseNumber).getKdamCoursesList().size() && check; i++) {
                                    if (!database.getStudentKdamCourses().get(ConnectedUserName).contains(database.getCoursesList().get(courseNumber).getKdamCoursesList().get(i))) {
                                        check = false;
                                    }

                                }
                            }
                            if (check) {//if the student have all kdamcourses
                                database.getCoursesList().get(courseNumber).incrementNumOfRegisteredStudents();
                                database.getCoursesList().get(courseNumber).getRegisteredUsersName().add(ConnectedUserName);
                                database.getStudentKdamCourses().get(ConnectedUserName).add(courseNumber);
                                ACK ack = new ACK(5);
                                return ack;
                            } else {
                                ERR error = new ERR(5);
                                return error;
                            }

                        } else {
                            ERR error = new ERR(5);
                            return error;
                        }
                    } else {
                        ERR error = new ERR(5);
                        return error;
                    }
                }
            }
        }

        //KDAMCHECK
        if (msg instanceof KDAMCHECK) {
            String courseNumber = ((KDAMCHECK) msg).getContent();
            if (!isLoggedin || isAdmin) { //if username is not logged in, return ERR
                ERR error = new ERR(6);
                return error;
            }
            synchronized (database.getCoursesList()) {
                if (database.getCoursesList().containsKey(courseNumber)) {
                    if (database.getCoursesList().get(courseNumber).getKdamCoursesList() != null) {//if there are kdamcourses to this course
                        ACK ack = new ACK(6, database.getCoursesList().get(courseNumber).getKdamCoursesList());
                        return ack;
                    } else {
                        ACK ack = new ACK(6);
                        return ack;
                    }
                }
                else{
                    ERR error = new ERR(6);
                    return error;
                }
            }
        }
        //COURSESTAT
        if (msg instanceof COURSESTAT) {
            String courseNumber = ((COURSESTAT) msg).getContent();
            if (!isLoggedin || !isAdmin) { //if username is not logged in or this is *not* the admin, return ERR
                ERR error = new ERR(7);
                return error;
            }
            synchronized (database.getCoursesList()) {
                String[] RegisteredStudentsNames = database.getCoursesList().get(courseNumber).getRegisteredUsersName().toArray(new String[database.getCoursesList().get(courseNumber).getRegisteredUsersName().size()]);
                Arrays.sort(RegisteredStudentsNames);//ordered alphabetically
                ACK ack = new ACK(7, database.getCoursesList().get(courseNumber).getnumOfRegisteredStudents(),
                        database.getCoursesList().get(courseNumber).getnumOfMaxStudents(),
                        RegisteredStudentsNames,
                        database.getCoursesList().get(courseNumber).getcourseName(),
                        database.getCoursesList().get(courseNumber).getcourseNum());
                return ack;
            }

        }
        //STUDENTSTAT
        if (msg instanceof STUDENTSTAT) {
            String studentName = ((STUDENTSTAT) msg).getContent();
            if (!isLoggedin || !isAdmin) { //if username is not logged in or this is *not* the admin, return ERR
                ERR error = new ERR(8);
                return error;
            }
            synchronized (database.getStudentKdamCourses()) {
                ACK ack = new ACK(8, database.getStudentKdamCourses().get(studentName), studentName);
                return ack;
            }
        }
        //ISREGISTERED
        if (msg instanceof ISREGISTERED) {
            String courseNumber = ((ISREGISTERED) msg).getContent();
            if (!isLoggedin || isAdmin) { //if username is not logged in or this is the admin, return ERR
                ERR error = new ERR(9);
                return error;
            }

            synchronized (database.getCoursesList()) {
                if (database.getCoursesList().get(courseNumber).getRegisteredUsersName().contains(ConnectedUserName)) {
                    ACK ack = new ACK(9, "REGISTERED");
                    return ack;
                } else {
                    ACK ack = new ACK(9, "NOT REGISTERED");
                    return ack;
                }
            }
        }
        //UNREGISTER
        if (msg instanceof UNREGISTER) {
            String courseNumber = ((UNREGISTER) msg).getContent();
            if (!isLoggedin || isAdmin) { //if username is not logged in or this is the admin, return ERR
                ERR error = new ERR(10);
                return error;
            }
            synchronized (database.getCoursesList()) {
                synchronized (database.getStudentKdamCourses()) {
                    if (database.getCoursesList().containsKey(courseNumber)
                            && database.getCoursesList().get(courseNumber).getRegisteredUsersName().contains(ConnectedUserName)) {//check if course exists and the student is registered to the course
                        database.getCoursesList().get(courseNumber).getRegisteredUsersName().remove(ConnectedUserName);
                        database.getCoursesList().get(courseNumber).decrementNumOfRegisteredStudents();
                        database.getStudentKdamCourses().get(ConnectedUserName).remove(courseNumber);
                        ACK ack = new ACK(10);
                        return ack;
                    } else {
                        ERR error = new ERR(10);
                        return error;
                    }
                }
            }
        }
        //MYCOURSES
        if (msg instanceof MYCOURSES) {
            if (!isLoggedin || isAdmin) { //if username is not logged in or this is the admin, return ERR
                ERR error = new ERR(11);
                return error;
            }
            synchronized (database.getStudentKdamCourses()) {
                if (database.getStudentKdamCourses().get(ConnectedUserName) == null) {
                    ACK ack = new ACK(11, new LinkedList<String>());
                    return ack;
                } else {
                    ACK ack = new ACK(11, database.getStudentKdamCourses().get(ConnectedUserName));
                    return ack;
                }
            }
        }
        ERR error = new ERR(13);//if the message is not an instance of one of the 11 messages
        return error;
    }


    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
