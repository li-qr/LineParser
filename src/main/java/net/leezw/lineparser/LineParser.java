package com.elong.hotel.LineParser;

import com.elong.hotel.LineParser.exception.FileCanNotOpenException;
import com.elong.hotel.LineParser.exception.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class LineParser implements Serializable{

    public static final String LOGGER = "LineParser";
    private static final long serialVersionUID = 4056745446860860300L;
    private static final Logger logger = LoggerFactory.getLogger(LineParser.LOGGER);

    private final ArrayList<LineParserRow> rows = new ArrayList<>();

    public LineParser() {
    }

    public LineParser(File file) throws FileCanNotOpenException {
        this.loadFile(file);
    }

    public LineParser(InputStream stream) throws FileCanNotOpenException {
        this.loadStream(stream);
    }

    public LineParserRow getRow(int index) {
        return rows.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int rindex, int cindex, Class cl) {
        return (T) rows.get(rindex).get(cindex, cl);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBySchema(int rindex, int cindex) {
        return (T) rows.get(rindex).getBySchema(cindex);
    }

    public int rowSize() {
        return rows.size();
    }


    public void loadFile(File file) throws FileCanNotOpenException {
        try {
            loadStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new FileCanNotOpenException(e.getMessage());
        }
    }

    public void loadStream(InputStream stream) throws FileCanNotOpenException {
        InputStreamReader isr;
        BufferedReader br = null;
        String line;
        try {
            Constructor<LineParserRow> rowConstructor = LineParserRow.class.getDeclaredConstructor();
            Constructor<LineParserCol> colConstructor = LineParserCol.class.getDeclaredConstructor(String.class,Integer.class);
            rowConstructor.setAccessible(true);
            colConstructor.setAccessible(true);
            Method method = LineParserRow.class.getDeclaredMethod("addCol", LineParserCol.class);
            method.setAccessible(true);
            isr = new InputStreamReader(stream, "UTF-8");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                LineParserRow row = rowConstructor.newInstance();
                String[] items = line.split("\\t");
                for (int index = 0; index < items.length; index++) {
                    if (!"".equals(items[index])) {
                        LineParserCol col = colConstructor.newInstance(items[index],row.size());
                        method.invoke(row, col);
                    }
                }
                rows.add(row);
            }
        } catch (Exception e) {
            throw new FileCanNotOpenException(e.getMessage());
        } finally {
            closeConnection(br);
        }
    }


    public static <T> void registerParser(Class cl, ObjParser<T> parser) {
        try {
            Method method = LineParserCol.class.getDeclaredMethod("register", Class.class, ObjParser.class);
            method.setAccessible(true);
            method.invoke(null, cl, parser);
        } catch (Exception e) {
            logger.error("注册解析异常.{}", e.getMessage());
        }
    }

    public static void setSchema(Class... clazz) {
        try {
            Method method = LineParserCol.class.getDeclaredMethod("setSchema", Class[].class);
            method.setAccessible(true);
            method.invoke(null, (Object) clazz);
        } catch (Exception e) {
            logger.error("配置Schema失败。{}", e.getMessage());
        }
    }

    public static void setSchemaBean(Class cl){
        try{
            Field[] fields = cl.getDeclaredFields();
            Class[] clazz = new Class[fields.length];
            for(int i=0;i<fields.length;i++){
                clazz[i] = fields[i].getType();
            }
            setSchema(clazz);
        }catch (Exception e){
            logger.error("配置Schema失败。{}", e.getMessage());
        }
    }

    public <T> List<T> wrapperBeans(Class<T> cl){
        List<T> list = new ArrayList<>();
        try {
            for (int i = 0; i < this.rowSize(); i++) {
                LineParserRow row = this.getRow(i);
                list.add(row.wrapperBean(cl));
            }
        }catch (Exception e){
            throw new ParserException("不能包装Beans.");
        }
        return list;
    }

    private void closeConnection(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            logger.error("关闭流失败。{}", e.getMessage());
        }
    }
}
