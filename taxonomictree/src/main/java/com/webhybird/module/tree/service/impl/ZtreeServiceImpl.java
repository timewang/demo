package com.webhybird.module.tree.service.impl;

import com.webhybird.framework.base.BaseService;
import com.webhybird.module.tree.constants.ZtreeEnum;
import com.webhybird.module.tree.entity.ZtreeEntity;
import com.webhybird.module.tree.repositories.ZtreeRepositories;
import com.webhybird.module.tree.service.ZtreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wangzhongfu on 2015/5/27.
 */
@Service
public class ZtreeServiceImpl extends BaseService implements ZtreeService {
    @Autowired
    private ZtreeRepositories ztreeRepositories;

    /**
     * 根据id 删除树 及其子节点
     *
     * @param id
     */
    @Override
    public void deleteTreeById(String id) {
        this.ztreeRepositories.deleteTreeWithLeaf(id);
    }

    /**
     * 根据id 获取树
     *
     * @param id
     * @return
     */
    @Override
    public ZtreeEntity findById(String id) {
        return this.ztreeRepositories.findOne(id);
    }

    /**
     * 新增树节点
     *
     * @param ztreeEntity
     */
    @Override
    public void saveZtree(ZtreeEntity ztreeEntity,boolean setParent) {
        ztreeEntity.setCreatetime(new Date());
        this.ztreeRepositories.save(ztreeEntity);
        if(setParent){
            this.ztreeRepositories.setPatrent(true,ztreeEntity.getPid());
        }
    }

    /**
     * update
     *
     * @param ztreeEntity
     */
    @Override
    public void updateZtree(ZtreeEntity ztreeEntity) {
        this.ztreeRepositories.update(ztreeEntity);
    }

    /**
     * 移动节点，将源节点及其子节点全部移动到另一节点下
     *
     * @param sourceId
     * @param tagetId
     */
    @Override
    public void moveTree(String sourceId, String tagetId) {
        this.ztreeRepositories.setPid(tagetId, sourceId);
    }

    /**
     * 设置节点
     * 有子节点
     * @param id
     */
    @Override
    public void setIsPatrentTrue(String id) {
        this.ztreeRepositories.setPatrent(true,id);
    }

    /**
     * 设置节点没有子节点
     *  要将该节点下的叶子节点的父ID 设置为0
     * @param id
     */
    @Override
    public void setIsPatrentFalse(String id) {
        this.ztreeRepositories.setPatrent(false,id);
        this.ztreeRepositories.setLeafePid(id,ZtreeEnum.rootId.getEnumName());
    }

    /**
     * 根据类型查询根节点列表
     *
     * @param type
     * @return
     */
    @Override
    public List<ZtreeEntity> findRootTree(String type) {
        return this.ztreeRepositories.findByPidAndTypeOrderByCreatetimeAsc(ZtreeEnum.rootId.getEnumName(),type);
    }

    /**
     * 根据父di type 查找树列表
     *
     * @param type
     * @param pid
     * @return
     */
    @Override
    public List<ZtreeEntity> findByTypeAndPid(String type, String pid) {
        return this.ztreeRepositories.findByPidAndTypeOrderByCreatetimeAsc(pid,type);
    }

    /**
     * 根据pid查询树列表
     *
     * @param pid
     * @return
     */
    @Override
    public List<ZtreeEntity> findTeeByPid(String pid) {
        return this.ztreeRepositories.findByPidOrderByCreatetimeDesc(pid);
    }

    /**
     * 更新name
     *
     * @param id
     * @param treeName
     * @return
     */
    @Override
    public int updateTreeName(String id, String treeName) {
        return this.ztreeRepositories.setName(treeName,id);
    }
}
