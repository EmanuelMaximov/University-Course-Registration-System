package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.nio.charset.StandardCharsets;

public class ADMINREG extends REG {

    public ADMINREG(byte[] bytes){//constructor #1
    super(bytes);
    }

    @Override
    public boolean isAdmin(){
        return true;
    }

    @Override
    public Opcode getOpcode() {
        return Message.Opcode.ADMINREG;
    }


}
