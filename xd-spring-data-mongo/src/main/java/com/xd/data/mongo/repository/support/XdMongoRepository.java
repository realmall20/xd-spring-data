package com.xd.data.mongo.repository.support;

import com.mongodb.client.result.DeleteResult;
import com.xd.data.repository.XdRepository;
import com.xd.data.repository.function.FieldLambdaUtil;
import com.xd.data.repository.query.*;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.util.StreamUtils;
import org.springframework.data.util.Streamable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author xiaohei
 * @create 2020-07-01 下午5:41
 **/
public class XdMongoRepository<T, ID> implements XdRepository<T, ID> {
    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, ID> entityInformation;

    /**
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public XdMongoRepository(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
        this.entityInformation = metadata;
    }

    public List<T> findByCondition(QueryCondition<T> condition) {
        Criteria criteria = convertConditionToCriteria(condition);
        Query query = new Query(criteria);
        return mongoOperations.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }

    @Override
    public Page findByCondition(QueryCondition<T> condition, Pageable pageable) {
        Criteria criteria = convertConditionToCriteria(condition);
        Query query = new Query(criteria);
        List<T> list = findByCondition(condition);
        long count = mongoOperations.count(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
        return new PageImpl(list, pageable, count);
    }

    @Override
    public Iterable findAll(Sort sort) {
        Query query = new Query().with(sort);
        return findAll(query);
    }

    @Override
    public Page findAll(Pageable pageable) {
        Query query = new Query().with(pageable);
        List list = findAll(query);
        long count = count();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        Streamable<S> source = Streamable.of(entities);
        boolean allNew = source.stream().allMatch(it -> entityInformation.isNew(it));

        if (allNew) {
            List<S> result = source.stream().collect(Collectors.toList());
            return new ArrayList<>(mongoOperations.insert(result, entityInformation.getCollectionName()));
        }
        return source.stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        return Optional.ofNullable(
                mongoOperations.findById(id, entityInformation.getJavaType(), entityInformation.getCollectionName()));
    }

    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        return mongoOperations.exists(getIdQuery(id), entityInformation.getJavaType(),
                entityInformation.getCollectionName());
    }


    @Override
    public <S extends T> S save(S entity) {
        Assert.notNull(entity, "Entity must not be null!");
        if (entityInformation.isNew(entity)) {
            return mongoOperations.insert(entity, entityInformation.getCollectionName());
        }
        return mongoOperations.save(entity, entityInformation.getCollectionName());
    }

    @Override
    public Iterable findAll() {
        return findAll(new Query());
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return findAll(new Query(new Criteria(entityInformation.getIdAttribute())
                .in(Streamable.of(ids).stream().collect(StreamUtils.toUnmodifiableList()))));
    }


    @Override
    public long count() {
        return mongoOperations.count(new Query(), entityInformation.getCollectionName());
    }

    @Override
    public void deleteById(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        mongoOperations.remove(getIdQuery(id), entityInformation.getJavaType(), entityInformation.getCollectionName());
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "The given entity must not be null!");
        DeleteResult deleteResult = mongoOperations.remove(entity, entityInformation.getCollectionName());
        if (entityInformation.isVersioned() && deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() == 0) {
            throw new OptimisticLockingFailureException(String.format(
                    "The entity with id %s with version %s in %s cannot be deleted! Was it modified or deleted in the meantime?",
                    entityInformation.getId(entity), entityInformation.getVersion(entity),
                    entityInformation.getCollectionName()));
        }
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        entities.forEach(this::delete);
    }


    @Override
    public void deleteAll() {
        mongoOperations.remove(new Query(), entityInformation.getCollectionName());
    }

    private List<T> findAll(Query query) {
        if (query == null) {
            return Collections.emptyList();
        }
        return mongoOperations.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }

    private Query getIdQuery(Object id) {
        return new Query(getIdCriteria(id));
    }

    private Criteria getIdCriteria(Object id) {
        return where(entityInformation.getIdAttribute()).is(id);
    }

    private Criteria convertConditionToCriteria(QueryCondition condition) {
        List<ISegment> conditions = condition.getCondition();
        Criteria criteria = new Criteria("and");
        for (ISegment iSegment : conditions) {
            if (iSegment instanceof KVCondition) {
                KVCondition kvCondition = (KVCondition) iSegment;
                String field = FieldLambdaUtil.getCacheKey(kvCondition.getField());
                switch (kvCondition.getOperate()) {
                    case EQ:
                        criteria.and(field).is(kvCondition.getValue());
                        break;
                    case GE:
                        criteria.and(field).gte(kvCondition.getValue());
                        break;
                    case GT:
                        criteria.and(field).gt(kvCondition.getValue());
                        break;
                    case LE:
                        criteria.and(field).lte(kvCondition.getValue());
                        break;
                    case LT:
                        criteria.and(field).lt(kvCondition.getValue());
                        break;
                }
            } else if (iSegment instanceof InCondition) {
                InCondition inCondition = (InCondition) iSegment;
                String field = FieldLambdaUtil.getCacheKey(inCondition.getField());
                criteria.and(field).in(inCondition.getValue());
            } else if (iSegment instanceof LikeCondition) {
                //模糊查询，内置的查询
                LikeCondition likeCondition = (LikeCondition) iSegment;
                String field = FieldLambdaUtil.getCacheKey(likeCondition.getField());
                switch (likeCondition.getLike()) {
                    case DEFAULT:
                        criteria.and(field).is("/" + likeCondition.getValue() + "/");
                        break;
                    case LEFT:
                        criteria.and(field).is(likeCondition.getValue() + "/");
                        break;
                    case RIGHT:
                        criteria.and(field).is("/" + likeCondition.getValue());
                        break;
                }

            }
        }
        return criteria;
    }

}
