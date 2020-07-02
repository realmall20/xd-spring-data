package com.xd.data.repository.function;

import com.xd.data.repository.reflect.PropertyNamer;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohei
 * @create 2020-07-01 下午11:32
 **/
public class FieldLambdaUtil {
    /**
     * 函数和缓存key值的映射
     */
    private static final Map<FieldFunction, String> FUNCTION_CACHE_KEY_MAP = new ConcurrentHashMap<>();
    /**
     * 类属性和redis hash key 的映射。
     */
    private static final Map<Field, String> FIELD_CACHE_KEY_MAP = new ConcurrentHashMap<>(100);


    public static <T> String getCacheKey(FieldFunction<T, ?> fn) {
        if (FUNCTION_CACHE_KEY_MAP.containsKey(fn)) {
            return FUNCTION_CACHE_KEY_MAP.get(fn);
        }
        String key = getFunctionCacheKey(fn);
        FUNCTION_CACHE_KEY_MAP.put(fn, key);
        return key;
    }

    private static <T> String getFunctionCacheKey(FieldFunction<T, ?> fn) {
        try {
            SerializedLambda serializedLambda = getSerializedLambda(fn);
            String getter = serializedLambda.getImplMethodName();
            Class cla = Class.forName(serializedLambda.getImplClass().replace("/", "."));
            String name = PropertyNamer.methodToProperty(getter);
            return getFieldCacheName(cla, name);
        } catch (Exception e) {
            throw new UnsupportedOperationException("method can not find cache key");
        }
    }

    public static Map getClassFieldCacheKeyMap(Class cla) {
        Field[] fields = cla.getDeclaredFields();
        Map<Field, String> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            map.put(field, getFieldCacheName(field));
        }
        return map;
    }

    private static String getFieldCacheName(Class cla, String name) throws NoSuchFieldException {
        Field field = cla.getDeclaredField(name);
        return getFieldCacheName(field);
    }

    public static String getFieldCacheName(Field field) {
        String key;
        if (FIELD_CACHE_KEY_MAP.containsKey(field)) {
            key = FIELD_CACHE_KEY_MAP.get(field);
        } else {
            key = field.getName();
            FIELD_CACHE_KEY_MAP.put(field, key);
        }
        return key;
    }

    private static <T> SerializedLambda getSerializedLambda(FieldFunction<T, ?> fn) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = fn.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        return (SerializedLambda) method.invoke(fn);
    }
}
