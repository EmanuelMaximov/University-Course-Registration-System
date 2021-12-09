package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.nio.charset.StandardCharsets;

public class STUDENTSTAT extends Message {

    private enum State {
        GET_USERNAME;
    }

    private State state;

    private int cont_off;
    private int curr_off;

    public String content;

    public STUDENTSTAT()
    {
        super();
        opcode = Opcode.STUDENTSTAT;
    }

    public STUDENTSTAT(byte[] bytes)
    {
        super(bytes);
        opcode = Opcode.STUDENTSTAT;
        state = State.GET_USERNAME;
        curr_off = 1;
        cont_off = curr_off+1;
    }

    @Override
    public byte[] encode() {
       return null;
    }

    public String getContent(){
        return this.content;
    }

    @Override
    public Message decodeNextByte(byte next) {
        curr_off+=1;
        if ( state== State.GET_USERNAME && next == 0 )
        {
            content = new String(bytes, cont_off, curr_off-cont_off, StandardCharsets.UTF_8);
            return this;
        }
        return null;
    }

}

