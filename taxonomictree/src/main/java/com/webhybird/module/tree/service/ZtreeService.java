package com.webhybird.module.tree.service;

import com.webhybird.module.tree.entity.ZtreeEntity;
import java.util.List;
/**
 * Created by wangzhongfu on 2015/5/27.
 */
public interface ZtreeService {
    /**
     * 根据id 删除树 及其子节点
     * @param id
     */
    void deleteTreeById(String id);
    /**
     * 根据id 获取树
     * @param id
     * @return
     */
    ZtreeEntity findById(String id);
    /**
     * 新增树节点
     * @param ztreeEntity
     */
    void saveZtree(ZtreeEntity ztreeEntity,boolean setParent);

    /**
     * update
     * @param ztreeEntity
     */
    void updateZtree(ZtreeEntity ztreeEntity);

    /**
     * 移动节点，将源节点及其子节点全部移动到另一节点下
     * @param sourceId
     * @param tagetId
     */
    void moveTree(String sourceId,String tagetId);

    /**
     * 设置节点
     * 有子节点
     * @param id
     */
    void setIsPatrentTrue(String id);

    /**
     * 设置节点没有子节点
     *  要将该节点下的叶子节点的父ID 设置为0
     * @param id
     */
    void setIsPatrentFalse(String id);

    /**
     * 根据类型查询根节点列表
     * @param type
     * @return
     */
    List<ZtreeEntity> findRootTree(String type);

    /**
     * 根据父di type 查找树列表
     * @param type
     * @param pid
     * @return
     */
    List<ZtreeEntity> findByTypeAndPid(String type,String pid);

    /**
     * 根据pid查询树列表
     * @param pid
     * @return
     */
    List<ZtreeEntity> findTeeByPid(String pid);

    /**
     * 更新name
     * @param id
     * @param treeName
     * @return
     */
    int updateTreeName(String id,String treeName);
}
