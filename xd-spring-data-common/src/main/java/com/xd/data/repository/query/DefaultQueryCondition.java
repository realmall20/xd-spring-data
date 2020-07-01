package com.xd.data.repository.query;

import java.util.*;

/**
 * 查询条件
 * @author xiaohei
 * @create 2020-07-01 下午3:00
 **/
public class DefaultQueryCondition<O> implements QueryCondition<O>{


    /**
     * 查询的信息，保证查询条件的顺序
     */
    private Stack<ISegment> conditionFieldList=new Stack<>();

    @Override
    public QueryCondition select(FieldFunction<O, ?>... fields) {
        return null;
    }

    @Override
    public <F> QueryCondition eq(FieldFunction<O, F> filed, Object value) {
        EqCondition eqCondition=new EqCondition(filed,value);
        conditionFieldList.push(eqCondition);
        return this;
    }

    public <F> QueryCondition gt(FieldFunction<O, F> filed,Object value){
        GtCondition condition=new GtCondition(filed,value);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition gte(FieldFunction<O, F> filed, Object value) {
        return null;
    }

    @Override
    public <F> QueryCondition lt(FieldFunction<O, F> filed, Object value) {
        return null;
    }

    @Override
    public <F> QueryCondition lte(FieldFunction<O, F> filed, Object value) {
        return null;
    }

    @Override
    public <F> QueryCondition contains(FieldFunction<O, F> filed, String value) {
        return null;
    }

    @Override
    public <F> QueryCondition start(FieldFunction<O, F> filed, String value) {
        return null;
    }

    @Override
    public <F> QueryCondition end(FieldFunction<O, F> filed, String value) {
        return null;
    }

    @Override
    public <F> QueryCondition in(FieldFunction<O, F> filed, Collection<?> values) {
        InCondition eqCondition=new InCondition(filed,values);
        conditionFieldList.push(eqCondition);
        return this;
    }


    public <F> QueryCondition like(FieldFunction<O, F> filed,String value){
        LikeCondition condition=new LikeCondition(filed,value);
        conditionFieldList.push(condition);
        return this;
    }





}
