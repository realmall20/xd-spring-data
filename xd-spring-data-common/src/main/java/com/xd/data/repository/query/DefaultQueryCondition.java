package com.xd.data.repository.query;

import com.xd.data.repository.enums.Like;
import com.xd.data.repository.enums.Keyword;
import com.xd.data.repository.function.FieldFunction;

import java.util.*;

/**
 * 查询条件
 *
 * @author xiaohei
 * @create 2020-07-01 下午3:00
 **/
public class DefaultQueryCondition<O> implements QueryCondition<O> {
    /**
     * 查询的信息，保证查询条件的顺序
     */
    private LinkedList<ISegment> conditionFieldList = new LinkedList<>();

    @Override
    public List<ISegment> getCondition() {
        return conditionFieldList;
    }

    @Override
    public QueryCondition select(FieldFunction<O, ?>... fields) {
        return null;
    }

    @Override
    public <F> QueryCondition eq(FieldFunction<O, F> filed, Object value) {
        KVCondition condition = new KVCondition(filed, value, Keyword.EQ);
        conditionFieldList.push(condition);
        return this;
    }

    public <F> QueryCondition gt(FieldFunction<O, F> filed, Number value) {
        KVCondition condition = new KVCondition(filed, value, Keyword.GT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition gte(FieldFunction<O, F> filed, Number value) {
        KVCondition condition = new KVCondition(filed, value, Keyword.GE);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition lt(FieldFunction<O, F> filed, Number value) {
        KVCondition condition = new KVCondition(filed, value, Keyword.LT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition lte(FieldFunction<O, F> filed, Number value) {
        KVCondition condition = new KVCondition(filed, value, Keyword.LE);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition contains(FieldFunction<O, F> filed, String value) {
        LikeCondition condition = new LikeCondition(filed, value, Like.DEFAULT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition startWith(FieldFunction<O, F> filed, String value) {
        LikeCondition condition = new LikeCondition(filed, value, Like.LEFT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition endWith(FieldFunction<O, F> filed, String value) {
        LikeCondition condition = new LikeCondition(filed, value, Like.LEFT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition in(FieldFunction<O, F> filed, Collection<?> values) {
        InCondition condition = new InCondition(filed, values);
        conditionFieldList.push(condition);
        return this;
    }

}
