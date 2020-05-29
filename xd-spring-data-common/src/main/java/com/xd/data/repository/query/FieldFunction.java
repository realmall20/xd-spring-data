package com.xd.data.repository.query;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 字段查询的函数
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface FieldFunction<T, R> extends Function<T, R>, Serializable {

}
