package com.xd.data.repository;

import com.xd.data.repository.query.QueryCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface XdRepository<T, ID> extends PagingAndSortingRepository<T, ID> {


    /**
     * 通过自己定义的condition查找对象
     * @param condition
     * @return
     */
    Iterable<T> findByCondition(QueryCondition<T> condition);

    /**
     * 通过自定义的condition查找对象
     * @param condition
     * @param pageable
     * @return
     */
    Page<T> findByCondition(QueryCondition<T> condition, Pageable pageable);
}
