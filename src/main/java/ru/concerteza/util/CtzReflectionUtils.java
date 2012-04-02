package ru.concerteza.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* User: alexey
* Date: 3/18/12
*/
public class CtzReflectionUtils {
     // http://stackoverflow.com/questions/1042798/retrieving-the-inherited-attribute-names-values-using-java-reflection/1042827#1042827
    public static List<Field> allFields(Class<?> type) {
        List<Field> res = new ArrayList<Field>();
        // own fields
        Collections.addAll(res, type.getDeclaredFields());
        // parent fields
        if (null != type.getSuperclass()) {
            List<Field> parents = allFields(type.getSuperclass());
            res.addAll(parents);
        }
        return res;
    }

    public static boolean isInner(Class<?> clazz) {
        if(null == clazz.getEnclosingClass()) return false;
        return (clazz.getModifiers() & Modifier.STATIC) == 0;
    }
}
