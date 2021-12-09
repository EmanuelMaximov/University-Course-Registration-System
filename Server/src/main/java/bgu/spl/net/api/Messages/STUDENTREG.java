package bgu.spl.net.api.Messages;

public class STUDENTREG extends REG {
    public STUDENTREG(byte[] bytes){//constructor #1
        super(bytes);
    }
    @Override
    public boolean isAdmin(){
        return false;
    }

    @Override
    public Opcode getOpcode() {
        return Opcode.STUDENTREG;
    }


}
