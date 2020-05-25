import net.leezw.lineparser.LineParser;
import net.leezw.lineparser.LineParserCol;
import net.leezw.lineparser.LineParserRow;
import net.leezw.lineparser.TestEntity;
import net.leezw.lineparser.exception.FileCanNotOpenException;
import org.junit.Before;
import org.junit.Test;

public class LineParserRowTest {

    LineParser LineParser;
    LineParserRow row;
    @Before
    public void setUp() throws FileCanNotOpenException {
     LineParser = new LineParser(getClass().getResourceAsStream("testfile"));
     row = LineParser.getRow(0);
        LineParser.setSchema(Integer.class, String.class, boolean.class, Double.class, TestEntity[].class,
                TestEntity.class);
    }

    @Test public void get() throws Exception {
        Integer d =row.get(0,Integer.class);
        assert d!=null;
    }

    @Test public void get1() throws Exception {
        Integer d = row.getBySchema(0);
        assert d!=null;
    }

    @Test public void size() throws Exception {
        assert row.size()>0;
    }


    @Test public void get2(){
        LineParserCol q = row.getCol(0);
        assert q!=null;
    }

}
