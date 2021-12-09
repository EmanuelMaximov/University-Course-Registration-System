package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.nio.charset.StandardCharsets;

public class ISREGISTERED extends Message {

    private int curr_off;

    public String content;

    public ISREGISTERED()
    {
        super();
        opcode = Opcode.ISREGISTERED;
    }

    public ISREGISTERED(byte[] bytes)
    {
        super(bytes);
        opcode = Opcode.ISREGISTERED;
        curr_off = 1;
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
        content = new String(bytes, 2, 1, StandardCharsets.UTF_8);
        if (curr_off== 3 && !content.equals("0"))
        {
            content = new String(bytes, 2, 2, StandardCharsets.UTF_8);

            return this;
        }
        if (curr_off== 3 && content.equals("0")){
            content = new String(bytes, 3, 1, StandardCharsets.UTF_8);

            return this;
        }

        return null;
    }

}

