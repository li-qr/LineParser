package net.leezw.lineparser.parsers;

import net.leezw.lineparser.ObjParser;

import java.io.Serializable;

public class LangParser<T> implements ObjParser<T>,Serializable {
    private static final long serialVersionUID = 8973668223021498425L;

    private Class clazz;

    public LangParser(Class cl) {
        clazz = cl;
    }

    @SuppressWarnings("unchecked")
    @Override public T parser(String value) {
        T t = null;
        try {
            t = (T) clazz.getConstructor(String.class).newInstance(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
