package com.xd.data.jpa.repository.support;


import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;

/**
 * jpa通用方法的扩展
 *
 * @author xiaohei
 * @create 2020-05-25 上午10:45
 **/
public class XdJpaRepositoryFactory extends JpaRepositoryFactory {
    /**
     * Creates a new {@link JpaRepositoryFactory}.
     *
     * @param entityManager must not be {@literal null}
     */
    public XdJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        /**
         * 替换为我们自己的默认实现类
         */
        return XdJpaRepository.class;
    }


}
