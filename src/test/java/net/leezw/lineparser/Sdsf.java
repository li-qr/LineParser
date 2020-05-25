package net.leezw.lineparser.quan;


public class Sdsf implements ObjParser<ETT> {


    @Override public ETT parser(String value) {
        return new ETT(value);
    }
}
