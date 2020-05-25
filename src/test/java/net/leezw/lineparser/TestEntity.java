package net.leezw.lineparser;


public class TestEntity {

    int a;
    String s;
    Boolean e;
    float sf;
    TestEntity[] t;
    TestEntity ts;

    public TestEntity(){}
    public TestEntity(String s){
        String [] ar = s.split("\\|");
        a = new Integer(ar[0]);
    }

    @Override
    public String toString(){
        return "testSuccess "+a+",";
    }

}
