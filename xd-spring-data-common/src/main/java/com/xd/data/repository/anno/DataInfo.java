package com.xd.data.repository.anno;

/**
 * 数据库 表名 或者文档名字
 */
public @interface DataInfo {

    /**
     * 对应的数据库名字,如果名字一样或者符合驼峰规则，可以不写，
     *
     */
    String value() default "";
}
