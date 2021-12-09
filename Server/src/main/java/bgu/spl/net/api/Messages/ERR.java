package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ERR extends Message {


    public short err_opcode;


    public ERR(int err_opcode) {
        opcode = Message.Opcode.ERR;
        this.err_opcode = (short) err_opcode;

    }

    @Override
    public byte[] encode() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            outputStream.write(shortToBytes((short) opcode.ordinal()));
            outputStream.write(shortToBytes(err_opcode));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] msg = outputStream.toByteArray();

        return msg;
    }


    @Override
    public Message decodeNextByte(byte next) {
        return null;
    }

    public byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

}
