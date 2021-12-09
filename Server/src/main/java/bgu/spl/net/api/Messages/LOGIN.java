package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LOGIN extends Message {

    private enum State {
        GET_USERNAME, GET_PASSWORD;
    }

    private State state;

    private int user_off;
    private int pass_off;
    private int curr_off;

    public String username=null;
    public String password=null;

    public LOGIN()
    {
        super();
        opcode = Opcode.LOGIN;
    }

    public LOGIN(byte[] bytes)
    {
        super(bytes);
        opcode = Opcode.LOGIN;
        state = State.GET_USERNAME;
        curr_off = 1;
        user_off = curr_off+1;
        pass_off = curr_off+1;

    }


    @Override
    public byte[] encode() { //not important- just because the generics
       return null;
    }

    @Override
    public Message decodeNextByte(byte next) {
        curr_off+=1;
        if ( state== State.GET_USERNAME && next == 0 )
        {
            username = new String(bytes, user_off, curr_off-user_off, StandardCharsets.UTF_8);
            pass_off = curr_off+1;
            state = State.GET_PASSWORD;
        }
        else if (  state== State.GET_PASSWORD && next == 0 )
        {
            password = new String(bytes, pass_off, curr_off-pass_off, StandardCharsets.UTF_8);
            return this;
        }
        return null;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}

