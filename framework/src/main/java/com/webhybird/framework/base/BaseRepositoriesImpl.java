package com.webhybird.framework.base;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by wangzhongfu on 2015/5/10.
 */
@Transactional
public class BaseRepositoriesImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseRepositories<T, ID> {

    /**
     *
     */
    private final EntityManager entityManager;
    /**
     *
     * @param domainClass
     * @param entityManager
     */
    public BaseRepositoriesImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);

        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;

    }

    /**
     * 根据sql查询 args里面的参数顺序要与sql中的条件顺序一致
     * @param hql
     * @param args
     * @return
     */
/*    protected List<T> findListByHql(String hql,List<Object> args){
        TypedQuery<T> query = entityManager.createQuery(hql, this.getDomainClass());
        pargrames(args,query);
        return query.getResultList();
    }

    private void pargrames(List<Object> args,TypedQuery<T> query){
        if(args != null && args.size() > 0){
            for(int i = 0 ; i < args.size() ; i++){
                query.setParameter(i + 1, args.get(i));
            }
        }
    }*/

    /**
     * 更新对象
     * @param t
     */
    @Override
    public void update(T t) {
        this.entityManager.merge(t);
    }

}
