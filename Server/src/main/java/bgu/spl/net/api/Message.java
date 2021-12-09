package bgu.spl.net.api;

import java.util.HashMap;
import java.util.Map;

public abstract class Message {
     public Opcode opcode;
     public byte[] bytes;
     public Message(byte[] bytes)
     {
          this.bytes = bytes;
     }
     public Message() {}

     public abstract byte[] encode();
     public abstract Message decodeNextByte(byte next);

     public enum Opcode {
          NONE(0),
          ADMINREG(1), STUDENTREG(2), LOGIN(3),
          LOGOUT(4), COURSEREG(5), KDAMCHECK(6),
          COURSESTAT(7), STUDENTSTAT(8), ISREGISTERED(9),
          UNREGISTER(10), MYCOURSES(11), ACK(12),ERR(13);

          private int value;
          private static Map map = new HashMap<>();

          private Opcode(int value) {
               this.value = value;
          }

          static {
               for (Opcode pageType : Opcode.values()) {
                    map.put(pageType.value, pageType);
               }
          }


     }


}
