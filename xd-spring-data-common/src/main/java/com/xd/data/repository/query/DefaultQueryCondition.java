package com.xd.data.repository.query;

import java.util.*;

/**
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

    @Override
    public <F> QueryCondition in(FieldFunction<O, F> filed, Collection<?> values) {
        InCondition eqCondition=new InCondition(filed,values);
        conditionFieldList.push(eqCondition);
        return this;
    }
}
