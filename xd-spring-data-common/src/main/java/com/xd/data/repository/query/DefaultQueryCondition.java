package com.xd.data.repository.query;

import com.xd.data.repository.enums.Like;
import com.xd.data.repository.enums.Operate;

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
    private LinkedList<ISegment> conditionFieldList=new LinkedList<>();

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
        KVCondition condition=new KVCondition(filed,value, Operate.EQ);
        conditionFieldList.push(condition);
        return this;
    }

    public <F> QueryCondition gt(FieldFunction<O, F> filed,Object value){
        KVCondition condition=new KVCondition(filed,value, Operate.GT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition gte(FieldFunction<O, F> filed, Object value) {
        KVCondition condition=new KVCondition(filed,value, Operate.GE);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition lt(FieldFunction<O, F> filed, Object value) {
        KVCondition condition=new KVCondition(filed,value, Operate.LT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition lte(FieldFunction<O, F> filed, Object value) {
        KVCondition condition=new KVCondition(filed,value, Operate.LE);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition contains(FieldFunction<O, F> filed, String value) {
        LikeCondition condition=new LikeCondition(filed,value, Like.DEFAULT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition start(FieldFunction<O, F> filed, String value) {
        LikeCondition condition=new LikeCondition(filed,value, Like.LEFT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition end(FieldFunction<O, F> filed, String value) {
        LikeCondition condition=new LikeCondition(filed,value, Like.RIGHT);
        conditionFieldList.push(condition);
        return this;
    }

    @Override
    public <F> QueryCondition in(FieldFunction<O, F> filed, Collection<?> values) {
        InCondition condition=new InCondition(filed,values);
        conditionFieldList.push(condition);
        return this;
    }

}
