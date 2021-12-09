package bgu.spl.net.api.Messages;


import bgu.spl.net.api.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ACK extends Message {

    public short msg_opcode;

    public LinkedList<String> courses_list = null;

    public String course_name;
    public String course_number;
    public short numOfSeatsAvailable;
    public short maxNumOfSeats;
    public String[] students_list;

    public String isregistered;
    String StudentName;

    public ACK(int msg_opcode)
    {
        opcode = Opcode.ACK;
        this.msg_opcode =(short) msg_opcode;
    }

    public ACK(int msg_opcode, LinkedList<String> courses_list){//FOR KDAMCHECK/STUDENTSTAT/MYCOURSES
        opcode = Opcode.ACK;
        this.msg_opcode = (short)msg_opcode;
        this.courses_list = courses_list;
    }
    public ACK(int msg_opcode, LinkedList<String> courses_list,String StudentName ){//FOR KDAMCHECK/STUDENTSTAT/MYCOURSES
        opcode = Opcode.ACK;
        this.msg_opcode = (short)msg_opcode;
        this.courses_list = courses_list;
        this.StudentName=StudentName;
    }

    public ACK(int msg_opcode, String isregistered){//FOR ISREGISTERED
        opcode = Opcode.ACK;
        this.msg_opcode = (short)msg_opcode;
        this.isregistered = isregistered;
    }

    public ACK (int msg_opcode, int numOfSeatsAvailable,
                int maxNumOfSeats, String[] students_list, String course_name, String course_number){//for COURSESTAT
        opcode = Opcode.ACK;
        this.msg_opcode =(short) msg_opcode;
        this.numOfSeatsAvailable = (short)numOfSeatsAvailable;
        this.maxNumOfSeats = (short)maxNumOfSeats;
        this.students_list = students_list;
        this.course_name=course_name;
        this.course_number=course_number;

    }

    public byte[] encode(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        boolean check=false;
        try {
            outputStream.write(shortToBytes((short)opcode.ordinal()));
            outputStream.write(shortToBytes(msg_opcode));
            if ( msg_opcode == Opcode.KDAMCHECK.ordinal()
                    || msg_opcode == Opcode.MYCOURSES.ordinal() )
            {
                for ( String number : courses_list)
                {
                    outputStream.write(number.getBytes());
                    outputStream.write(0x20);
                }
                check=true;
            }
            if (msg_opcode == Opcode.STUDENTSTAT.ordinal()){
                outputStream.write(StudentName.getBytes());
                outputStream.write(0);
                for ( String number : courses_list)
                {
                    outputStream.write(number.getBytes());
                    outputStream.write(0x20);
                }
                check=true;
            }


            if ( msg_opcode == Opcode.ISREGISTERED.ordinal())
            {
                    outputStream.write(isregistered.getBytes());
                    check=true;
            }
            if ( msg_opcode == Opcode.COURSESTAT.ordinal() )
            {
                outputStream.write(course_number.getBytes());
                outputStream.write(0);
                outputStream.write(course_name.getBytes());
                outputStream.write(0);
                outputStream.write(shortToBytes(numOfSeatsAvailable));
                outputStream.write(shortToBytes(maxNumOfSeats));
                for ( String name : students_list)
                {
                    outputStream.write(name.getBytes());
                    outputStream.write(0x20);
                }
                check=true;
            }
            if (check) {
                outputStream.write(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] msg = outputStream.toByteArray( );

        return msg;
    }



    @Override
    public Message decodeNextByte(byte next) {
        return null;
    }

    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }



}
