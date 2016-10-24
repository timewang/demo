package com.webhybird.framework.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by wangzhongfu on 2015/5/10.
 */
@NoRepositoryBean
public interface BaseRepositories<T, ID extends Serializable> extends JpaRepository<T, ID> ,JpaSpecificationExecutor<T> {
    /**
     * 更新对象
     * @param t
     */
    void update(T t);

}
