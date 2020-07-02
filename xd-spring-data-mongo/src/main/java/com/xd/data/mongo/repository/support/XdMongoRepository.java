package com.xd.data.mongo.repository.support;

import com.xd.data.repository.function.FieldLambdaUtil;
import com.xd.data.repository.query.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.util.List;

/**
 * @author xiaohei
 * @create 2020-07-01 下午5:41
 **/
public class XdMongoRepository<T, ID> extends SimpleMongoRepository<T, ID> {
    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, ID> entityInformation;
    /**
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public XdMongoRepository(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations=mongoOperations;
        this.entityInformation = metadata;
    }

    public Iterable<T> findByCondition(QueryCondition condition) {
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
        Query query = new Query(criteria);
        return mongoOperations.find(query,entityInformation.getJavaType(),entityInformation.getCollectionName());
    }

}
