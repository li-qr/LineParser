import net.leezw.lineparser.*;
import net.leezw.lineparser.exception.FileCanNotOpenException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTest {

    @Ignore
    public void loadFile() throws IOException {
        InputStream is = getClass().getResourceAsStream("conf/custom/env/testfile");
        InputStreamReader isr = new InputStreamReader(is,"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String l1 = br.readLine();
        String[] l1a = l1.split("\\t");
        String l2=  br.readLine();
        String[] l2a = l2.split("\\t");
        ArrayList<String> s = new ArrayList<>(Arrays.asList(l2a));
        ArrayList<String> s2 = new ArrayList<>(Arrays.asList(l1a));
        s.remove("");
        s2.remove("");
//        Long e = Array.getLong(s,3);
    }

    @Ignore

    public void t() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
        InstantiationException {
        LineParserRow row = LineParserRow.class.getConstructor().newInstance();
    }

    public Object s(){
        return new Integer(12121);
    }

    @Ignore
    public void sd() throws FileCanNotOpenException {
        LineParser LineParser = new LineParser();
        LineParser.loadStream(getClass().getResourceAsStream("conf/custom/env/testfile"));
    }

    @Ignore
    public void sfw() throws FileCanNotOpenException {
       LineParser LineParser = new LineParser(getClass().getResourceAsStream("conf/custom/env/testfile"));
        LineParser.registerParser(Integer.class,new Sdsf());
        List<LineParserRow> l = null;
        LineParserRow r = l.get(0);
        Boolean d =r.get(3,Boolean.class);
        System.out.println(d);
    }

    @Ignore
    public void gg(){
        snn(Integer.class);
    }

    public void snn(Class... clazz){
        System.out.println(Class[].class);
    }

    @Ignore
    public void allTest() throws FileCanNotOpenException {
        LineParser LineParser = new LineParser(getClass().getResourceAsStream("conf/custom/env/testfile"));
        Integer a = LineParser.get(0,1,Integer.class);
        System.out.println(a);
        Float b = LineParser.get(0,3,Float.class);
        System.out.println(b);
        Double c = LineParser.get(0,3,Double.class);
        System.out.println(c);
        Boolean d = LineParser.get(1,0,Boolean.class);
        System.out.println(d);
        boolean e = LineParser.get(1,1,boolean.class);
        System.out.println(e);
        Long f = LineParser.get(1,3,Long.class);
        System.out.println(f);
        LineParser.registerParser(Integer.class,new Sdsf());
        Integer g = LineParser.get(0,1,Integer.class);
        System.out.println(g);
    }

    @Ignore
    public void allTest1() throws FileCanNotOpenException {
        LineParser LineParser = new LineParser(getClass().getResourceAsStream("conf/custom/env/testfile"));
        LineParser.setSchema(int.class,String.class,boolean.class,float.class);
        int a = LineParser.getBySchema(0,0);
        System.out.println(a);
        String b = LineParser.getBySchema(0,1);
        System.out.println(b);
        boolean c = LineParser.getBySchema(0,2);
        System.out.println(c);
        float d = LineParser.getBySchema(0,3);
        System.out.println(d);
    }

    @Ignore
    public void arrayTest() throws FileCanNotOpenException {
        LineParser LineParser = new LineParser(getClass().getResourceAsStream("conf/custom/env/testfile"));
        Boolean[] a = LineParser.get(0,4,Boolean[].class);
        boolean[] b = LineParser.get(0,4,boolean[].class);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
    }

    @Ignore
    public void classTest(){
        Class z = int[].class;
        //System.out.println(z.toString().indexOf("[")>0);
        //System.out.println(z);
        //System.out.println(new ArrayList<Integer>().getClass());
        System.out.println("int"+int[].class);
        //System.out.println(Integer[].class);
        System.out.println("boolean"+boolean[].class);
        System.out.println("double"+double[][].class);
        System.out.println("float"+float[].class);
        System.out.println("long"+long[].class);
        System.out.println("byte"+byte[].class);
        System.out.println("char"+char[].class);
        System.out.println("short"+short[].class);
        System.out.println(Short.TYPE);
        System.out.println(Short.class);
        String d = Integer[].class.toString();
       //System.out.println(d.substring(d.indexOf("[")+1,d.indexOf("[")+2));
        //System.out.println(d.substring(d.indexOf("[")+2));
    }

    @Ignore
    public void tge(){
        Integer[] d;
        d= (Integer[]) Array.newInstance(Integer.class,3);
    }
    @Ignore
    public void sdfsdf() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class f = Class.forName("[I");
        System.out.println(f);
    }

    @Ignore
    public void wet() throws ClassNotFoundException, FileCanNotOpenException {
        LineParser LineParser = new LineParser(getClass().getResourceAsStream("conf/custom/env/testfile"));
        Boolean[] d = LineParser.get(0,4,Boolean[].class);
        System.out.println(Arrays.toString(d));
        LineParser.registerParser(ETT.class,new Sdsf());
        //LineParser.registerParser(ETT.class,new LangParser<ETT>(ETT.class));
        ETT dd = LineParser.get(0,0,ETT.class);
        System.out.println(dd.toString());
        ETT[] e = LineParser.get(0,4,ETT[].class);
        System.out.println(Arrays.toString(e));
    }

    @Test
    public void weww(){
        Field[] s = TestEntity.class.getDeclaredFields();
        Class s3 = s[0].getType();
        Class s4 = int.class;
    }

    @Test
    public void sdss(){

    }

    private <T> List<T> de(){
        List<T> list = new ArrayList<>();
        list.add((T) new Integer(1));
        return list;
    }
}
