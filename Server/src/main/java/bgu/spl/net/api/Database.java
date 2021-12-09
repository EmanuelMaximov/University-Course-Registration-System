package bgu.spl.net.api;


import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
//public methods are allowed too
public class Database {
    private ConcurrentHashMap<String, String> usernameANDpassword; //user name, password- when register
    private ConcurrentHashMap<String, Course> CoursesList;//course number & course deteils
    private ConcurrentLinkedQueue<String> LoggedInUsers;//the names of logged in students
    private ConcurrentHashMap<String, LinkedList<String>> StudentKdamCourses;//student name & the kdamcourses he's registered to
    private ConcurrentHashMap<String, String> AdminsusernameANDpassword;
    /**
     * Retrieves the single instance of this class.
     */
    private static class DatabaseHolder {//singleton
        private static Database instance = new Database();
    }

    public static Database getInstance() {
        return DatabaseHolder.instance;
    }

    //to prevent user from creating new Database
    private Database() {
        this.usernameANDpassword = new ConcurrentHashMap<>();
        this.CoursesList = new ConcurrentHashMap<>();
        this.LoggedInUsers = new ConcurrentLinkedQueue<>();
        this.StudentKdamCourses = new ConcurrentHashMap<>();
        this.AdminsusernameANDpassword=new ConcurrentHashMap<>();
    }

    /**
     * Getters
     */
    public ConcurrentHashMap<String, String> getUsernameANDpassword() {
        return usernameANDpassword;
    }
    public ConcurrentHashMap<String, String> getAdminsusernameANDpassword() {
        return AdminsusernameANDpassword;
    }

    public ConcurrentHashMap<String, Course> getCoursesList() {
        return CoursesList;
    }

    public ConcurrentLinkedQueue<String> getLoggedInUsers() {
        return LoggedInUsers;
    }

    public void init(String filePath) {
        initialize(filePath);
    }

    public ConcurrentHashMap<String, LinkedList<String>> getStudentKdamCourses() {
        return StudentKdamCourses;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        String textFileSTR = "";
        try (BufferedReader br = new BufferedReader(new FileReader(coursesFilePath));) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
                if (line != null) {
                    sb.append("\n");
                }
            }
            textFileSTR = sb.toString();

        } catch (IOException ex) {
        }
        String[] Lines = textFileSTR.split("\n");
        for (String line : Lines) {
            String coursenum = "";
            String coursename = "";
            LinkedList<String> kdamCoursesList = new LinkedList<String>();
            int numOfMaxStudents = 0;
            String[] fields = line.split("\\|");
            for (int i = 0; i < fields.length; i++) {
                if (i == 0) {
                    coursenum = fields[i];
                }
                if (i == 1) {
                    coursename = fields[i];
                }
                if (i == 2) {
                    String[] kdamcourses = fields[i].substring(1, (fields[i].length()) - 1).split(",");
                    if (!kdamcourses[0].isEmpty()) {
                        for (String c : kdamcourses) {
                            kdamCoursesList.add(c);
                        }
                    }
                }
                if (i == 3) {
                    numOfMaxStudents = Integer.parseInt(fields[i]);
                }

            }
            CoursesList.put(coursenum, new Course(coursenum, coursename, kdamCoursesList, numOfMaxStudents));
        }


        return true;
    }


}
