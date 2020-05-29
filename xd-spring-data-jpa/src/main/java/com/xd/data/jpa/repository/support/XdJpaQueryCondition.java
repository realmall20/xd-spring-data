package com.xd.data.jpa.repository.support;

import com.xd.data.repository.query.FieldFunction;
import com.xd.data.repository.query.QueryCondition;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;

/**
 * query condition的具体实现
 *
 * @author xiaohei
 * @create 2020-05-28 下午2:33
 **/
public class XdJpaQueryCondition<T> implements QueryCondition<T> {

    private CriteriaBuilder builder;

    public XdJpaQueryCondition(CriteriaBuilder builder){
        this.builder=builder;
    }

    public QueryCondition select(FieldFunction<T, ?>... fields) {
        return null;
    }

    public <F> QueryCondition eq(FieldFunction<T, F> filed, Object value) {
        return null;
    }

    public <F> QueryCondition in(FieldFunction<T, F> filed, Collection<?> values) {
        return null;
    }
}
