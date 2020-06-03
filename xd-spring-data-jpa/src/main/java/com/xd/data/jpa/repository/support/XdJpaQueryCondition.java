package com.xd.data.jpa.repository.support;

import com.xd.data.repository.metata.DataInfoHelper;
import com.xd.data.repository.query.FieldFunction;
import com.xd.data.repository.query.QueryCondition;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * query condition的具体实现
 *
 * @author xiaohei
 * @create 2020-05-28 下午2:33
 **/
public class XdJpaQueryCondition<T> implements QueryCondition<T> {

    private CriteriaBuilder builder;

    private List<Predicate> predicateList;

    private final JpaEntityInformation<T, ?> entityInformation;

    public XdJpaQueryCondition(CriteriaBuilder builder) {
        this.builder = builder;
        predicateList = new ArrayList();
        Class clz = null;
        CriteriaQuery query = builder.createQuery(DataInfoHelper.getClass(filed));
        Root root = query.from(clz);
    }

    public QueryCondition select(FieldFunction<T, ?>... fields) {
        return null;
    }

    public <F> QueryCondition eq(FieldFunction<T, F> filed, Object value) {
        Class clz = DataInfoHelper.getClass(filed);
        CriteriaQuery query = builder.createQuery(DataInfoHelper.getClass(filed));
        Root root = query.from(clz);
        String column = DataInfoHelper.getColumn(filed);
        predicateList.add(builder.equal(root.get(column), value));
        return null;
    }

    public <F> QueryCondition in(FieldFunction<T, F> filed, Collection<?> values) {

        String column = DataInfoHelper.getColumn(filed);
        CriteriaBuilder.In<Object> in = cb.in(root.get("suitFor"));
        for(Objects obj:values){

        }
        predicateList.add(builder.in(root.get(column), value));
    }
}
