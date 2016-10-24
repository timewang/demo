package com.webhybird.framework.base;

import com.webhybird.framework.util.SQLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/10.
 */
public class BaseJpaDao<T> {

    @PersistenceContext
    protected EntityManager em;
    @Autowired
    protected MyJdbcTemplate myJdbcTemplate;
    /**
     *
     */
    protected final Class<T> entityClass;

    /**
     * 查询有限的结果集
     * @param sql
     * @param pageable
     * @return
     */
    protected List<T> findListBySql(String sql,Pageable pageable){
        Assert.hasText(sql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return this.createNativeQueryBySql(sql, pageable, null).getResultList();
    }

    /**
     * 有条件的查询有限的结果集
     * @param sql
     * @param pageable
     * @param args
     * @return
     */
    protected List<T> findListBySql(String sql,Pageable pageable,List<Object> args){
        Assert.hasText(sql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return this.createNativeQueryBySql(sql, pageable, args).getResultList();
    }

    /**
     * 有条件的查询有限的结果集
     * @param sql
     * @param pageable
     * @param args
     * @return
     */
    protected List<T> findListBySql(String sql,Pageable pageable,Map<String,Object> args){
        Assert.hasText(sql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return this.createNamedNativeQueryBySql(sql, pageable, args).getResultList();
    }

    /**
     * 分页无参数查询
     * @param sql
     * @param pageable
     * @return
     */
    protected Page<T> findAllBySql(String sql,Pageable pageable){
        Assert.hasText(sql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return new PageImpl<T>(this.createNativeQueryBySql(sql, pageable, null).getResultList(),pageable,(Long) this.createCountQuery(sql).getSingleResult());
    }

    /**
     * 有条件的分页
     * @param sql
     * @param pageable
     * @param args
     * @return
     */
    protected Page<T> findAllBySql(String sql,Pageable pageable,List<Object> args){
        Assert.hasText(sql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return new PageImpl<T>(this.createNativeQueryBySql(sql, pageable, args).getResultList(),pageable,(Long) this.createCountQuery(sql,args.toArray()).getSingleResult());
    }

    /**
     * 有条件的分页
     * 参数绑定
     * @param sql
     * @param pageable
     * @param args
     * @return
     */
    protected Page<T> findAllBySql(String sql,Pageable pageable,Map<String,Object> args){
        Assert.hasText(sql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return new PageImpl<T>(this.createNamedNativeQueryBySql(sql, pageable, args).getResultList(),pageable,(Long) this.createCountQuery(sql,args).getSingleResult());
    }

    ///////////

    /**
     * 查询有限的结果集
     * @param hql
     * @param pageable
     * @return
     */
    protected List<T> findList(String hql,Pageable pageable){
        Assert.hasText(hql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return this.createTypeQueryByHql(hql, pageable, null).getResultList();
    }

    /**
     * 有条件的查询有限的结果集
     * @param hql
     * @param pageable
     * @param args
     * @return
     */
    protected List<T> findList(String hql,Pageable pageable,List<Object> args){
        Assert.hasText(hql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return this.createTypeQueryByHql(hql, pageable, args).getResultList();
    }

    /**
     * 有条件的查询有限的结果集
     * 参数绑定
     * @param hql
     * @param pageable
     * @param args
     * @return
     */
    protected List<T> findList(String hql,Pageable pageable,Map<String,Object> args){
        Assert.hasText(hql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return this.createNamedTypeQueryByHql(hql, pageable, args).getResultList();
    }

    /**
     * 分页无参数查询
     * @param hql
     * @param pageable
     * @return
     */
    protected Page<T> findAll(String hql,Pageable pageable){
        Assert.hasText(hql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return new PageImpl<T>(this.createTypeQueryByHql(hql, pageable, null).getResultList(),pageable,(Long) this.createCountQuery(hql).getSingleResult());
    }

    /**
     * 有条件的分页
     * @param hql
     * @param pageable
     * @param args
     * @return
     */
    protected Page<T> findAll(String hql,Pageable pageable,List<Object> args){
        Assert.hasText(hql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return new PageImpl<T>(this.createTypeQueryByHql(hql, pageable, args).getResultList(),pageable,(Long) this.createCountQuery(hql,args.toArray()).getSingleResult());
    }

    /**
     * 有条件的分页
     * 参数绑定
     * @param hql
     * @param pageable
     * @param args
     * @return
     */
    protected Page<T> findAll(String hql,Pageable pageable,Map<String,Object> args){
        Assert.hasText(hql, "queryJQL不能为空");
        Assert.notNull(pageable);
        return new PageImpl<T>(this.createNamedTypeQueryByHql(hql, pageable, args).getResultList(),pageable,(Long) this.createCountQuery(hql,args).getSingleResult());
    }

    /**
     *查询列表 无参数
     * @param hql
     * @return
     */
    protected List<T> findListByHql(String hql){
        Assert.hasText(hql, "queryJQL不能为空");
        return em.createQuery(hql, entityClass).getResultList();
    }

    /**
     * 根据sql查询 args里面的参数顺序要与sql中的条件顺序一致
     * @param hql
     * @param args
     * @return
     */
    protected List<T> findListByHql(String hql,List<Object> args){
        Assert.hasText(hql, "queryJQL不能为空");
        return this.createTypeQueryByHql(hql, args).getResultList();
    }

    /**
     * 根据sql查询 map 绑定参数
     * @param hql
     * @param args
     * @return
     */
    protected List<T> findListByHql(String hql,Map<String,Object> args){
        Assert.hasText(hql, "queryJQL不能为空");
        return this.createNamedTypeQueryByHql(hql, args).getResultList();
    }

    /**
     *查询列表 无参数
     * @param sql
     * @return
     */
    protected List<T> findListBySql(String sql){
        Assert.hasText(sql, "queryJQL不能为空");
        return em.createNativeQuery(sql, entityClass).getResultList();
    }

    /**
     * 根据sql查询 args里面的参数顺序要与sql中的条件顺序一致
     * @param sql
     * @param args
     * @return
     */
    protected List<T> findListBySql(String sql,List<Object> args){
        Assert.hasText(sql, "queryJQL不能为空");
        return this.createNativeQueryBySql(sql, args).getResultList();
    }

    /**
     * 根据sql查询 map 绑定参数
     * @param sql
     * @param args
     * @return
     */
    protected List<T> findListBySql(String sql,Map<String,Object> args){
        Assert.hasText(sql, "queryJQL不能为空");
        return this.createNamedTypeQueryByHql(sql, args).getResultList();
    }


    /**
     * 创建TypeQuery
     * @param hql
     * @param args
     * @return
     */
    private TypedQuery<T> createTypeQueryByHql(final String hql, final Pageable pageable, final List<Object> args){
        TypedQuery<T> query = em.createQuery(hql, entityClass);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        this.pargrames(query, args.toArray());
        return query;
    }

    /**
     * 创建TypeQuery
     * @param hql
     * @param parameters
     * @return
     */
    private TypedQuery<T> createNamedTypeQueryByHql(final String hql, final Pageable pageable, final Map<String,Object> parameters){
        TypedQuery<T> query = em.createQuery(hql, entityClass);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        this.pargrames(query, parameters);
        return query;
    }

    /**
     * 创建TypeQuery
     * @param hql
     * @param args
     * @return
     */
    private TypedQuery<T> createTypeQueryByHql(final String hql, final List<Object> args){
        TypedQuery<T> query = em.createQuery(hql, entityClass);
        this.pargrames(query, args.toArray());
        return query;
    }

    /**
     * 创建TypeQuery
     * @param hql
     * @param parameters
     * @return
     */
    private TypedQuery<T> createNamedTypeQueryByHql(final String hql,final Map<String,Object> parameters){
        TypedQuery<T> query = em.createQuery(hql, entityClass);

        this.pargrames(query, parameters);

        return query;
    }


    /**
     * 创建NativeQuery
     * @param sql
     * @param pageable
     * @param args
     * @return
     */
    private Query createNativeQueryBySql(final String sql, final Pageable pageable, final List<Object> args){
        Query query = em.createNativeQuery(sql, entityClass);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        this.pargrames(query, args.toArray());

        return query;
    }

    /**
     * 创建NativeQuery
     * @param sql
     * @param pageable
     * @param parameters
     * @return
     */
    private Query createNamedNativeQueryBySql(final String sql, final Pageable pageable,final Map<String,Object> parameters){
        Query query = em.createNativeQuery(sql, entityClass);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        this.pargrames(query, parameters);

        return query;
    }

    /**
     * 创建NativeQuery
     * @param sql
     * @param args
     * @return
     */
    private Query createNativeQueryBySql(final String sql, final List<Object> args){
        Query query = em.createNativeQuery(sql, entityClass);
        if(args != null){
            this.pargrames(query, args.toArray());
        }
        return query;
    }

    /**
     * 命名绑定的query
     * @param sql
     * @param parameters
     * @return
     */
    private Query createNativeNamedQuery(String sql,final Map<String,Object> parameters){
        Query query = em.createNativeQuery(sql, entityClass);
        this.namedParameters(query,parameters);
        return query;
    }

    /**
     * named 绑定参数
     * @param jql
     * @param parameters
     * @return
     */
    private Query createNamedQuery(String jql,final Map<String,Object> parameters){
        Assert.hasText(jql, "queryJQL不能为空");
        Query query = this.em.createQuery(jql);
        this.namedParameters(query,parameters);
        return query;
    }

    /**
     *
     * @param query
     * @param parameters
     */
    private void namedParameters( Query query,Map<String,Object> parameters){
        if(parameters != null && parameters.size() > 0){
            for(Map.Entry<String,Object> entry : parameters.entrySet()){
                query.setParameter(entry.getKey(),entry.getValue());
            }
        }
    }

    /**
     *创建countquery
     * @param hql
     * @param values
     * @return
     */
    private Query createCountQuery(String hql,final Object... values){
        Assert.hasText(hql, "queryJQL不能为空");
        Query query = this.em.createQuery(SQLUtils.buildCountSQL(hql));
            //query.setParameter(i + 1, values[i]);
        this.pargrames(query, values);
        return query;
    }

    /**
     *创建countquery
     * @param hql
     * @param values
     * @return
     */
    private Query createCountQuery(String hql,final Map<String,Object> values){
        Assert.hasText(hql, "queryJQL不能为空");
        Query query = this.em.createQuery(SQLUtils.buildCountSQL(hql));
        //query.setParameter(i + 1, values[i]);
        this.pargrames(query, values);
        return query;
    }

    /**
     * 设置查询参数
     * @param query
     * @param values
     */
    private void pargrames(Query query, Object... values){
        if(values != null && values.length > 0){
            for(int i = 0 ; i < values.length ; i++){
                query.setParameter(i + 1, values[i]);
            }
        }
    }

    public BaseJpaDao() {
        this.entityClass = getInitEntityClass();
        if (this.entityClass == null) {
            String clsName = super.getClass().getSimpleName();
            throw new RuntimeException(super.getClass().getCanonicalName()
                    + "未定义泛型! 继承于:" + BaseJpaDao.class.getCanonicalName()
                    + "的类都必需声明所操作实体的泛型, 如:\npublic class " + clsName
                    + " extends " + BaseJpaDao.class.getSimpleName() + "<"
                    + clsName.substring(0, clsName.length() - 3)
                    + "> implements I" + clsName + "{\n\t...\n}");
        }
    }

    protected Class<T> getInitEntityClass() {
        Class cls = getEntityTypeFromClass(super.getClass());
        if (cls == null) {
            cls = getFirstGenericType(super.getClass());
        }
        return cls;
    }
    protected Class<T> getEntityTypeFromClass(Class<?> beanClass) {
        if (BaseJpaDao.class.isAssignableFrom(beanClass)) {
            Type type = beanClass.getGenericSuperclass();
            if ((type != null) && (ParameterizedType.class.isInstance(type))) {
                ParameterizedType pType = (ParameterizedType) type;
                if (pType.getRawType().equals(BaseJpaDao.class)) {
                    Type argType = pType.getActualTypeArguments()[0];
                    if (argType instanceof Class) {
                        return ((Class) argType);
                    }
                    return null;
                }
            }

            return getEntityTypeFromClass(beanClass.getSuperclass());
        }
        return null;
    }

    private Class<T> getFirstGenericType(Class<?> beanClass) {
        Type type = beanClass.getGenericSuperclass();
        if ((type != null) && (ParameterizedType.class.isInstance(type))) {
            ParameterizedType pType = (ParameterizedType) type;
            Type argType = pType.getActualTypeArguments()[0];
            if (argType instanceof Class) {
                return ((Class) argType);
            }
            return null;
        }

        return getFirstGenericType(beanClass.getSuperclass());
    }

}
