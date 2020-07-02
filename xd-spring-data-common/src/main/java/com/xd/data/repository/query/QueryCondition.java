package com.xd.data.repository.query;

import com.xd.data.repository.function.FieldFunction;

import java.util.Collection;
import java.util.List;

public interface QueryCondition<O> {

     List<ISegment> getCondition();
    /**
     * 查找的字段
     * @param fields
     * @param <O>
     * @return
     */
    QueryCondition select(FieldFunction<O, ?>... fields);

    /**
     * 判断两者相等
     * @param filed
     * @param value
     * @param <O>
     * @param <F>
     * @return
     */
    <F> QueryCondition eq(FieldFunction<O, F> filed, Object value);

    /**
     * 大于某个值
     * @param filed
     * @param value
     * @param <F>
     * @return
     */
    <F> QueryCondition gt(FieldFunction<O, F> filed,Object value);

    <F> QueryCondition gte(FieldFunction<O, F> filed,Object value);

    <F> QueryCondition lt(FieldFunction<O, F> filed,Object value);

    <F> QueryCondition lte(FieldFunction<O, F> filed,Object value);

    <F> QueryCondition contains(FieldFunction<O, F> filed,String value);

    <F> QueryCondition start(FieldFunction<O, F> filed,String value);

    <F> QueryCondition end(FieldFunction<O, F> filed,String value);
    /**
     * 判断是否是包含
     * @param filed
     * @param values
     * @param <O>
     * @param <F>
     * @return
     */
     <F> QueryCondition in(FieldFunction<O, F> filed, Collection<?> values);




}
