package com.webhybird.module.tree.repositories;

import com.webhybird.framework.base.BaseRepositories;
import com.webhybird.module.tree.entity.ZtreeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangzhongfu on 2015/5/27.
 */
public interface ZtreeRepositories extends BaseRepositories<ZtreeEntity,String> {
    /**
     * 根据pid和type 查询
     * 按时间倒排
     * 一般用于查询顶级树
     * @param pid
     * @param type
     * @return
     */
    List<ZtreeEntity> findByPidAndTypeOrderByCreatetimeDesc(String pid,String type);
    /**
     * 根据pid和type 查询
     * 按时间正排
     * 一般用于查询顶级树
     * @param pid
     * @param type
     * @return
     */
    List<ZtreeEntity> findByPidAndTypeOrderByCreatetimeAsc(String pid,String type);
    /**
     * 根据pid查询树
     * 按时间倒排
     * @param pid
     * @return
     */
    List<ZtreeEntity> findByPidOrderByCreatetimeDesc(String pid);
    /**
     * 根据pid查询树
     * 按时间正排
     * @param pid
     * @return
     */
    List<ZtreeEntity> findByPidOrderByCreatetimeAsc(String pid);

    /**
     * 将某一pid下的树移动到另一pid上
     * @param sourcePid
     * @param targetPid
     * @return
     */
    @Modifying
    @Transactional
    @Query("update ZtreeEntity ztreeEntity set ztreeEntity.pid = ?1 where ztreeEntity.pid = ?2 ")
    int setLeafePid(String targetPid,String sourcePid);

    /**
     * 更改pid
     * @param pid
     * @param id
     * @return
     */
    @Modifying
    @Transactional
    @Query("update ZtreeEntity ztreeEntity set ztreeEntity.pid = ?1 where ztreeEntity.id = ?2 ")
    int setPid(String pid,String id);

    /**
     * 更改名称
     * @param name
     * @param id
     * @return
     */
    @Modifying
    @Transactional
    @Query("update ZtreeEntity ztreeEntity set ztreeEntity.name = ?1 where ztreeEntity.id = ?2 ")
    int setName(String name,String id);

    /**
     * 批量移动树，将某个节点及其子节点全部移动到另一节点下
     * @param pid
     * @param sourceId
     * @return
     */
    @Modifying
    @Transactional
    @Query("update ZtreeEntity ztreeEntity set ztreeEntity.pid = ?1 where ztreeEntity.id = ?2 or ztreeEntity.pid = ?2")
    int setPidAndToChildren(String pid, String sourceId);

    /**
     * 删除某个节点 及节点下的子节点
     * @param id
     * @return
     */
    @Modifying
    @Transactional
    @Query("delete from ZtreeEntity ztreeEntity where ztreeEntity.id = ?1 or ztreeEntity.pid = ?1")
    int deleteTreeWithLeaf(String id);

    /**
     * 设置节点是否有子节点
     * @param idPatrent
     * @param id
     * @return
     */
    @Modifying
    @Transactional
    @Query("update ZtreeEntity ztreeEntity set ztreeEntity.isParent = ?1 where ztreeEntity.id = ?2")
    int setPatrent(Boolean idPatrent,String id);

}
