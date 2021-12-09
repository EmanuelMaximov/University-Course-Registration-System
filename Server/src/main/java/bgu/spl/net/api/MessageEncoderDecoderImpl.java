package bgu.spl.net.api;

import bgu.spl.net.api.Messages.*;

import java.util.Arrays;


public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Message> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k (2^10=1024)
    private int len = 0;//position of bytes array
    private State state;
    private Message message;

    private enum State { //the state of the message
        WAIT_OPCDOE, WAIT_BODY;
    }

    public MessageEncoderDecoderImpl() { //constructor
        state = State.WAIT_OPCDOE;
        message = null;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    @Override
    public Message decodeNextByte(byte nextByte) {
        Message cmd = null;
        //System.out.println(Arrays.toString(bytes));
        pushByte(nextByte); //push the byte to: bytes
        if (state == State.WAIT_OPCDOE) { //we haven't discovered opcode yet
            if (len >= 2) {  //we can find the opcode
                short s_opcode = bytesToShort(bytes);  //find opcode
                if (s_opcode < 1 || s_opcode > 13)
                    s_opcode = 0;
                short opcode = s_opcode;
                state = State.WAIT_BODY; //now we found opcode so we change to state body
                switch (opcode) { //checks
                    case 1:
                        message = new ADMINREG(bytes);
                        break;
                    case 2:
                        message = new STUDENTREG(bytes);
                        break;
                    case 3:
                        message = new LOGIN(bytes);
                        break;
                    case 4:
                        message = new LOGOUT(bytes);
                        cmd = message;
                    case 5:
                        message = new COURSEREG(bytes);
                        break;
                    case 6:
                        message = new KDAMCHECK(bytes);
                        break;
                    case 7:
                        message = new COURSESTAT(bytes);
                        break;
                    case 8:
                        message = new STUDENTSTAT(bytes);
                        break;
                    case 9:
                        message = new ISREGISTERED(bytes);
                        break;
                    case 10:
                        message = new UNREGISTER(bytes);
                        break;
                    case 11:
                        message = new MYCOURSES(bytes);
                        cmd = message;
                        break;
                    default:
                        len = 0;
                        state = State.WAIT_OPCDOE;
                        break;
                }
            }
        } else {
            cmd = message.decodeNextByte(nextByte); //decode the byte in the message
        }

        // restart if found command
        if (cmd != null) {
            len = 0;
            state = State.WAIT_OPCDOE;
        }
        return cmd; //not a line yet
    }

    @Override
    public byte[] encode(Message message) {
        byte[] arr = null;
        if (message instanceof ACK) {
            arr = message.encode();
        }

        if (message instanceof ERR) {
            arr = message.encode();
        }
        return arr;
    }
}
