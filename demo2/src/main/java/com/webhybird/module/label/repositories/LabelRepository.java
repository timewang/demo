package com.webhybird.module.label.repositories;

import com.webhybird.module.label.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * BaseRepositories : 抽象接口
 * LabelRepositoryCustom ： 客户端行为接口，用于实现自己的查询逻辑
 * Created by wangzhongfu on 2015/5/5.
 */
public interface LabelRepository extends JpaRepository<Label,String>,LabelRepositoryCustom {

    @Query("select new Label(l.id,l.value) from Label l ")
    List<Label> findAllByC();

    /**
     * 按照值搜索标签 传入参数形如
     * %搜索%
     * @param label
     * @return
     */
    @Query("select label from Label label where label.value like ?1")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<Label> searchLabel(String label);

    @Modifying
    @Query("update Label label set label.value = ?1 where label.id = ?2")
    void updateValueById(String value, String id);

    /**
     * 分页查询指定字段会出问题
     * 建议用Spring template 返回VO
     * @param spec
     * @param pageable
     * @return
     */
    @Query("select L.value,L.test from Label L")
    Page<Object[]> findAllConstructor(Specification<Label> spec, Pageable pageable);
    @Query("select L.value,L.test from Label L")
    List<Object[]> findLabels();

    @Query("select L.value,L.test from Label L")
    List<Object[]> findLabels(Pageable pageable);

    @Query(value = "SELECT * FROM LABEL WHERE VALUE_ LIKE ?1" , nativeQuery = true)
    List<Label> nativeQueryDemo(String label);

    @Query(value = "select label from Label label where label.value like :label ")
    List<Label> namedQueryDemo(@Param("label") String label);

    /**
     * 关联查询 属性名称 + 所关联的字段名称
     * @param createdBy
     * @return
     */
    List<Label> findByCreatedBy_id(String createdBy);

    @Query(value = "select label from Label label where label.createdBy.id = ?1")
    List<Label> findByUserId(String userId);

    List<Label> findByValue(String value);

    List<Label> findByTest(String username);

    List<Label> findByTestOrderByCreateDateDesc(String username);

    List<Label> findByTestAndValue(String username, String value);

}
