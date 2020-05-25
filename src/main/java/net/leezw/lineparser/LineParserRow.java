package com.elong.hotel.LineParser;

import com.elong.hotel.LineParser.exception.ParserException;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class LineParserRow {

    private ArrayList<LineParserCol> cols = new ArrayList<>();

    private LineParserRow() {
    }

    private void addCol(LineParserCol col) {
        this.cols.add(col);
    }

    public <T> T get(int index, Class<T> cl) {
        return cols.get(index).objectType(cl);
    }

    public <T> T getBySchema(int index) {
        return (T) cols.get(index).objectTypeBySchema();
    }

    public LineParserCol getCol(int index){
        return cols.get(index);
    }

    public int size() {
        return cols.size();
    }

    public <T> T wrapperBean(Class<T> cl){
        Field[] fields = cl.getDeclaredFields();
        T t;
        try {
            t = (T) cl.newInstance();
            for (int j = 0; j < this.size(); j++) {
                LineParserCol col = this.getCol(j);
                try {
                    fields[j].set(t, col.objectType(fields[j].getType()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            throw  new ParserException("不能包装Bean.");
        }
        return t;
    }

}
