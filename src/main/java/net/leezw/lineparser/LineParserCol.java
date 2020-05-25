package net.leezw.lineparser.LineParser;

import net.leezw.lineparser.exception.ParserException;
import net.leezw.lineparser.parsers.LangParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LineParserCol implements Serializable{

    private static final long serialVersionUID = 2276707159164332818L;
    private static final Logger logger = LoggerFactory.getLogger(LineParser.LOGGER);
    private static ArrayList<Class> schema = new ArrayList<>();
    private static Map<Class, ObjParser> parserMap = new HashMap<>();

    private String value;
    private Integer colIndex;

    static {
        parserMap.put(String.class, new LangParser<String>(String.class));
        LangParser<Boolean> booleanParser = new LangParser<>(Boolean.class);
        parserMap.put(Boolean.class, booleanParser);
        parserMap.put(Boolean.TYPE, booleanParser);
        LangParser<Float> floatParser = new LangParser<>(Float.class);
        parserMap.put(Float.class, floatParser);
        parserMap.put(Float.TYPE, floatParser);
        LangParser<Double> doubleParser = new LangParser<>(Double.class);
        parserMap.put(Double.class, doubleParser);
        parserMap.put(Double.TYPE, doubleParser);
        LangParser<Integer> integerParser = new LangParser<>(Integer.class);
        parserMap.put(Integer.class, integerParser);
        parserMap.put(Integer.TYPE, integerParser);
        LangParser<Long> longParser = new LangParser<>(Long.class);
        parserMap.put(Long.class, longParser);
        parserMap.put(Long.TYPE, longParser);
        LangParser<Byte> byteParser = new LangParser<>(Byte.class);
        parserMap.put(Byte.class, byteParser);
        parserMap.put(Byte.TYPE, byteParser);
        LangParser<Character> charParser = new LangParser<>(Character.class);
        parserMap.put(Character.class, charParser);
        parserMap.put(Character.TYPE, charParser);
        LangParser<Short> shortParser = new LangParser<>(Short.class);
        parserMap.put(Short.class, shortParser);
        parserMap.put(Short.TYPE, shortParser);
    }

    private LineParserCol() {
    }

    private LineParserCol(String value, Integer colIndex) {
        this.setValue(value);
        this.colIndex = colIndex;
    }

    private void setValue(String value) {
        this.value = value;
    }

    private static <T> void register(Class cl, ObjParser<T> parser) {
        parserMap.put(cl, parser);
    }

    @SuppressWarnings("unchecked")
    public <T> T objectTypeBySchema() {
        if (schema.size() < 1) {
            throw new ParserException("没有配置schema。");
        }
        if (colIndex + 1 > schema.size()) {
            throw new IndexOutOfBoundsException("index超出schema范围。");
        }
        return (T) this.objectType(schema.get(colIndex));
    }

    @SuppressWarnings("unchecked")
    public <T> T objectType(Class<T> cl) {
        T obj = null;
        try {
            if (cl.toString().indexOf("[") != -1) {
                obj = parserList(cl);
            } else if (parserMap.containsKey(cl)) {
                obj = (T) parserMap.get(cl).parser(value);
            } else {
                throw new ParserException("没有找到合适的解析类。"+cl.toString());
            }
        } catch (Exception e) {
            logger.error("类型解析错误：{}", e);
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    private <U> U parserList(Class<U> cl) {
        U result = null;
        try {
            String clString = cl.toString();
            int index = clString.indexOf("[");
            Integer length = null;
            try {
                length = new Integer(value.substring(0, value.indexOf(":")));
            } catch (Exception e) {
                logger.error("{}不能转换为int。", value);
            }
            if (null == length) {
                length = 0;
            }
            String type = clString.substring(index + 1, index + 2);
            switch (type) {
                case "I":
                    Integer[] temp0 = parserArray(Integer.class, length);
                    int[] ints = new int[temp0.length];
                    int ind0 = 0;
                    for (Integer i : temp0) {
                        ints[ind0++] = i;
                    }
                    result = (U) ints;
                    break;
                case "Z":
                    Boolean[] temp1 = parserArray(Boolean.class, length);
                    boolean[] boos = new boolean[temp1.length];
                    int ind1 = 0;
                    for (Boolean i : temp1) {
                        boos[ind1++] = i;
                    }
                    result = (U) boos;
                    break;
                case "D":
                    Double[] temp2 = parserArray(Double.class, length);
                    double[] dous = new double[temp2.length];
                    int ind2 = 0;
                    for (Double i : temp2) {
                        dous[ind2++] = i;
                    }
                    result = (U) dous;
                    break;
                case "F":
                    Float[] temp3 = parserArray(Float.class, length);
                    float[] flos = new float[temp3.length];
                    int ind3 = 0;
                    for (Float i : temp3) {
                        flos[ind3++] = i;
                    }
                    result = (U) flos;
                    break;
                case "J":
                    Long[] temp4 = parserArray(Long.class, length);
                    long[] lons = new long[temp4.length];
                    int ind4 = 0;
                    for (Long i : temp4) {
                        lons[ind4++] = i;
                    }
                    result = (U) lons;
                    break;
                case "L":
                    Class clazz = Class.forName(clString.substring(index + 2, clString.length() - 1));
                    result = (U) parserArray(clazz, length);
            }
        } catch (Exception e) {
            logger.error("类型识别错误。{}", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T> T[] parserArray(Class<T> cl, int length) {
        if(!parserMap.containsKey(cl)){
            throw new ParserException("没有找到合适的解析类。"+cl.toString());
        }
        T[] result;
        try {
            result = (T[]) Array.newInstance(cl, length);
            String[] tempArray = value.substring(value.indexOf(":") + 1).split(",");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < tempArray.length; j++) {
                    try {
                            Array.set(result, i, parserMap.get(cl).parser(tempArray[j]));
                    } catch (Exception e) {
                        sb.append(tempArray[i]).append(",");
                    }
                    i++;
                }
            }
            if (sb.length() > 0) {
                logger.error("{}转换到{}失败", sb, cl.toString());
            }
        } catch (Exception e) {
            throw new ParserException(value, cl, e.getMessage());
        }
        return result;
    }

    private static void setSchema(Class... clazz) {
        schema = new ArrayList<>(Arrays.asList(clazz));
    }
}
