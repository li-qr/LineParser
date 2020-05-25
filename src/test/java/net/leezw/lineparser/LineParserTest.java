import net.leezw.lineparser.LineParser;
import net.leezw.lineparser.LineParserRow;
import net.leezw.lineparser.TestEntity;
import net.leezw.lineparser.TestParser;
import net.leezw.lineparser.parsers.LangParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class LineParserTest {

    private LineParser LineParser;

    @Before public void setUp() throws Exception {
        LineParser = new LineParser(getClass().getResourceAsStream("testfile"));
    }

    @Test public void getRow() throws Exception {
        LineParserRow LineParserRow = LineParser.getRow(0);
        assert LineParserRow != null;
    }

    @Test public void get() throws Exception {
        double d = LineParser.get(0, 3, double.class);
        assert d == 4.33;
        Double D = LineParser.get(0, 3, Double.class);
        assert D == 4.33;
    }

    @Test public void get1() throws Exception {
        LineParser.setSchema(Integer.class, String.class, boolean.class, Double.class, TestEntity[].class,
            TestEntity.class);
        //LineParser.registerParser(TestEntity.class,new LangParser<TestEntity>(TestEntity.class));
        Double D = LineParser.getBySchema(1, 3);
        assert D == 64.4;
        TestEntity[] tea = LineParser.getBySchema(2, 4);
        System.out.println(Arrays.toString(tea));
//        assert tea==null;
       // TestEntity te = LineParser.getBySchema(2, 5);
        //System.out.println(te);
        //assert tea != null;
    }

    @Test public void rowSize() throws Exception {
        assert LineParser.rowSize() == 3;
    }


    @Test public void iter(){
        LineParserRow row;
        for(int i=0;i<LineParser.rowSize();row=LineParser.getRow(i),i++){
            System.out.println(i);
        }
    }

    @Ignore public void loadFile() throws Exception {
        LineParser.loadFile(new File("/Users/user/LineParser/src/main/resources/conf/custom/env/testfile"));
    }

    @Test public void loadStream() throws Exception {
        LineParser.loadStream(getClass().getResourceAsStream("conf/custom/env/testfile"));
    }

    @Test public void registerParser() throws Exception {
        LineParser.registerParser(TestEntity.class, new LangParser<TestEntity>(TestEntity.class));
        TestEntity t1 = LineParser.get(0, 5, TestEntity.class);
        assert t1 != null;
        LineParser.registerParser(TestEntity.class, new TestParser());
        TestEntity t2 = LineParser.get(1, 5, TestEntity.class);
        assert t2 != null;
    }

    @Test public void setSchema() throws Exception {
        LineParser.setSchema(Integer.class, String.class, boolean.class, Double.class, TestEntity[].class,
            TestEntity.class);
    }

    @Test
    public void setSc(){
        LineParser.setSchemaBean(TestEntity.class);
        LineParser.registerParser(TestEntity.class,new LangParser<TestEntity>(TestEntity.class));
        TestEntity ts = LineParser.getBySchema(1,5);
    }

    @Test
    public void dfds(){
        LineParser.registerParser(TestEntity.class,new LangParser<TestEntity>(TestEntity.class));
        List<TestEntity> list = LineParser.wrapperBeans(TestEntity.class);
    }

    @Ignore public void i() {
        LineParser.registerParser(TestEntity.class, new LangParser<TestEntity>(TestEntity.class));
        TestEntity[] w = LineParser.get(1, 4, TestEntity[].class);
        System.out.println(Arrays.toString(w));
    }

}
