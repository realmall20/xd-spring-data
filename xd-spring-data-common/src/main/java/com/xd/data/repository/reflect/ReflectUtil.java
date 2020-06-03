package com.xd.data.repository.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通过反射获取类的属性
 *
 * @author xiaohei
 * @create 2019-12-10 上午9:35
 **/
public class ReflectUtil {

    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();


    /**
     * 获取对象的所有属性
     * @param cla
     * @return
     */
    public static List<Field> getField(Class<?> cla){
        if(cla.getSuperclass()!=null){
           List<Field> fieldList= getCurrentClassFeild(cla);
           Class<?> supperClass=   cla.getSuperclass();
           return excludeOverrideSuperField(fieldList,getCurrentClassFeild(supperClass));
        }else{
            return getCurrentClassFeild(cla);
        }
    }

    /**
     * 排除重载的属性
     * @param fieldList
     * @param supperFieldList
     * @return
     */
    public static List<Field> excludeOverrideSuperField(List<Field> fieldList,List<Field> supperFieldList){
      Map<String,Field> fieldMap=  fieldList.stream().collect(Collectors.toMap(Field::getName, Function.identity()));
      supperFieldList.stream().filter(a->!fieldMap.containsKey(a.getName())).filter(fieldList::add);
      return fieldList;
    }

    /**
     * 获取当前对象的属性，排除父类的属性
     * @param cla
     * @return
     * @author xiaohei
     * @create 2019/12/10
     */
    public static List<Field> getCurrentClassFeild(Class<?> cla) {
        if (Objects.isNull(cla)) {
            return Collections.emptyList();
        }
        List<Field> list;
        if (!CLASS_FIELD_CACHE.containsKey(cla)) {
            list = Stream.of(cla.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(field -> !Modifier.isTransient(field.getModifiers()))
                    .collect(Collectors.toList());
            CLASS_FIELD_CACHE.put(cla, list);
        } else {
            list = CLASS_FIELD_CACHE.get(cla);
        }
        return list;
    }
}
