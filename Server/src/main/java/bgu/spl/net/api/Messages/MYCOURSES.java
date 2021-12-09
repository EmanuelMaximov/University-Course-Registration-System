package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

public class MYCOURSES extends Message {

    public MYCOURSES()
    {
        super();
        opcode = Opcode.MYCOURSES;
    }

    public MYCOURSES(byte[] bytes)
    {
        super(bytes);
        opcode = Opcode.MYCOURSES;
    }

    @Override
    public byte[] encode() {
        return null;

    }

    @Override
    public Message decodeNextByte(byte next) {
        return this;
    }

}
