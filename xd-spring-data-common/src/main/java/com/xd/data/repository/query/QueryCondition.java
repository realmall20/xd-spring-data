package com.xd.data.repository.query;

import java.util.Collection;

public interface QueryCondition<O> {
    /**
     * 查找的字段
     * @param fields
     * @param <O>
     * @return
     */
    public   QueryCondition select(FieldFunction<O, ?>... fields);

    /**
     * 判断两者相等
     * @param filed
     * @param value
     * @param <O>
     * @param <F>
     * @return
     */
    public <F> QueryCondition eq(FieldFunction<O, F> filed, Object value);

    /**
     * 判断是否是包含
     * @param filed
     * @param values
     * @param <O>
     * @param <F>
     * @return
     */
    public <F> QueryCondition in(FieldFunction<O, F> filed, Collection<?> values);
}
