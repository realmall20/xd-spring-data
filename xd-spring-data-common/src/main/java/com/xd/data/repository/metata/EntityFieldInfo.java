package com.xd.data.repository.metata;

import com.xd.data.repository.anno.DataField;
import com.xd.data.repository.util.StringUtils;
import lombok.Getter;

import java.lang.reflect.Field;

/**
 * 字段属性映射
 *
 * @author xiaohei
 * @create 2020-04-16 下午2:21
 **/
@Getter
public class EntityFieldInfo {
    /**
     * 对应的字段名
     */
    private final String column;
    /**
     * 对应的属性名
     */
    private final String property;

    public EntityFieldInfo(Field field) {
        this.property = field.getName();
        DataField indexField = field.getAnnotation(DataField.class);
        String column = null;
        if (null == indexField || StringUtils.isBlank(indexField.value())) {
            column = this.property;
            column = StringUtils.camelToUnderline(column);
            column = column.toUpperCase();
        } else {
            column = indexField.value();
        }
        this.column = column;

    }
}
