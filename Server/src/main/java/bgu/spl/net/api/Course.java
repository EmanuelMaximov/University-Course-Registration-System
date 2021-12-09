package bgu.spl.net.api;

import java.util.LinkedList;

public class Course {
    private String courseNum;
    private String courseName;
    private LinkedList<String> KdamCoursesList;
    private int numOfMaxStudents;

    private int numOfRegisteredStudents=0;
    private LinkedList<String> RegisteredUsersName;

    Course(String courseNum,
            String courseName,
            LinkedList<String> KdamCoursesList,
            int numOfMaxStudents){
        this.courseName=courseName;
        this.courseNum=courseNum;
        this.KdamCoursesList=KdamCoursesList;
        this.numOfMaxStudents=numOfMaxStudents;
        this.RegisteredUsersName=new LinkedList<String>();
    }

    public String getcourseNum(){return this.courseNum;}
    public String getcourseName(){return this.courseName;}
    public LinkedList<String> getKdamCoursesList(){return this.KdamCoursesList;}
    public int getnumOfMaxStudents(){return this.numOfMaxStudents;}
    public int getnumOfRegisteredStudents(){return this.numOfRegisteredStudents;}
    public LinkedList<String> getRegisteredUsersName(){return this.RegisteredUsersName;}
    public void incrementNumOfRegisteredStudents(){this.numOfRegisteredStudents++;}
    public void decrementNumOfRegisteredStudents(){this.numOfRegisteredStudents--;}


}
