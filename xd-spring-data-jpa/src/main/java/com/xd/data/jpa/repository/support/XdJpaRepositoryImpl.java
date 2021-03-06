package com.xd.data.jpa.repository.support;

import com.xd.data.repository.XdRepository;
import com.xd.data.repository.function.FieldLambdaUtil;
import com.xd.data.repository.query.ISegment;
import com.xd.data.repository.query.KVCondition;
import com.xd.data.repository.query.QueryCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * jpa实现类的扩展
 *
 * @author xiaohei
 * @create 2020-05-25 上午10:47
 **/
@Transactional(readOnly = true)
public class XdJpaRepositoryImpl<T, ID> implements XdRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    private final PersistenceProvider provider;

    public XdJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {

        Assert.notNull(entityInformation, "JpaEntityInformation must not be null!");
        Assert.notNull(entityManager, "EntityManager must not be null!");

        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }

    /**
     * 通过id 批量删除数据
     *
     * @param ids
     */
    public void deleteById(Iterable<ID> ids) {
        StringBuilder builder=new StringBuilder();
        builder.append("delete from ");
        builder.append(entityInformation.getEntityName());
        builder.append(" where ");
        builder.append(entityInformation.getIdAttributeNames());
        builder.append(" in ( ");
        for(ID id:ids){
            builder.append(id.toString()).append(",");
        }
        builder.replace(builder.length()-1,builder.length(),")");
        Query query=em.createQuery(builder.toString());
        query.executeUpdate();
    }


    public Iterable<T> findByCondition(QueryCondition condition) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(getDomainClass());
        List<ISegment> segments= condition.getCondition();
        List<Predicate> predicates = new ArrayList();
        Root<T> root = query.from(entityInformation.getJavaType());
        //TODO 转换成 hibernate
        for(ISegment iSegment:segments){
            if(iSegment instanceof KVCondition){
                KVCondition cond=(KVCondition)iSegment;
                String field = FieldLambdaUtil.getCacheKey(cond.getField());
                switch (cond.getOperate()){
                    case EQ:
                        Path<?> path = root.get(field);
                        Predicate predicate= builder.equal(path,cond.getValue());
                        predicates.add(predicate);
                        break;
                    case GE:
                        Path<Number> pathNumber = root.get(field);
                        predicates.add(builder.ge(pathNumber,(Number) cond.getValue()));
                        break;
                    case GT:
                        pathNumber = root.get(field);
                        predicates.add(builder.gt(pathNumber,(Number) cond.getValue()));
                        break;
                    case LE:
                        pathNumber = root.get(field);
                        predicates.add(builder.le(pathNumber,(Number) cond.getValue()));
                        break;
                    case LT:
                        pathNumber = root.get(field);
                        predicates.add(builder.lt(pathNumber,(Number) cond.getValue()));
                        break;
                }
            }
        }
        return null;
    }

    public Page<T> findByCondition(QueryCondition condition, Pageable pageable) {
        return null;
    }

    public Iterable<T> findAll(Sort sort) {
        return null;
    }

    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    public <S extends T> S save(S entity) {
        return null;
    }

    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    public boolean existsById(ID id) {
        return false;
    }

    public Iterable<T> findAll() {
        return null;
    }

    public Iterable<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void deleteById(ID id) {

    }

    public void delete(T entity) {

    }

    public void deleteAll(Iterable<? extends T> entities) {

    }

    public void deleteAll() {

    }

    protected Class<T> getDomainClass() {
        return entityInformation.getJavaType();
    }
}
