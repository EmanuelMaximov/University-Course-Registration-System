package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LOGOUT extends Message {

    public LOGOUT()
    {
        super();
        opcode = Message.Opcode.LOGOUT;
    }

    public LOGOUT(byte[] bytes)
    {
        super(bytes);
        opcode = Message.Opcode.LOGOUT;
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
