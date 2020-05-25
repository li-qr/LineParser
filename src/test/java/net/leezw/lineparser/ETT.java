package net.leezw.lineparser.quan;


public class ETT {
    String dw = "EEEEEE";
    public ETT(String d){
        dw += d;
    }

    @Override
    public String toString(){
        return dw;
    }
}
