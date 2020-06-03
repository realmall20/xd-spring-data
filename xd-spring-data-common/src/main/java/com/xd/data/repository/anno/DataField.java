package com.xd.data.repository.anno;

/**
 * 数据库字段的额外命名，如果不符合驼峰做法的话，可以通过这个解决
 */
public @interface DataField {

     String value() default "";
}
