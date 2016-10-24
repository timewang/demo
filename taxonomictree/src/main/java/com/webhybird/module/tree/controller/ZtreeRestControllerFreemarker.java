package com.webhybird.module.tree.controller;

import com.webhybird.framework.base.FreemarkerBaseController;
import com.webhybird.module.tree.entity.ZtreeEntity;
import com.webhybird.module.tree.service.ZtreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created by wangzhongfu on 2015/5/27.
 */
@RestController
public class ZtreeRestControllerFreemarker extends FreemarkerBaseController {

    @Autowired
    private ZtreeService ztreeServiceImpl;

    /**
     * 新增数节点
     * @param ztreeEntity
     * @return
     */
    @RequestMapping(value = "tree",method = RequestMethod.POST)
    public ZtreeEntity add(ZtreeEntity ztreeEntity,@RequestParam(defaultValue = "false")boolean setParent){
        this.ztreeServiceImpl.saveZtree(ztreeEntity,setParent);
        /*//原父节点不包含子节点，需将其设置为父节点
        if(setParent){
            this.ztreeServiceImpl.setIsPatrentTrue(ztreeEntity.getPid());
        }*/
        return ztreeEntity;
    }

    /**
     * 更新节点名称
     * @param id
     * @param treeName
     * @return
     */
    @RequestMapping(value = "treename",method = RequestMethod.PUT)
    public String treeName(String id,String treeName){
        this.ztreeServiceImpl.updateTreeName(id,treeName);
        return this.SUCCESS;
    }

    /**
     * 修改书节点
     * @param ztreeEntity
     * @return
     */
    @RequestMapping(value = "tree",method = RequestMethod.PUT)
    public ZtreeEntity edit(ZtreeEntity ztreeEntity){
        this.ztreeServiceImpl.updateZtree(ztreeEntity);
        return ztreeEntity;
    }

    /**
     * 根据id 查找节点
     * @param id
     * @return
     */
    @RequestMapping(value = "tree",method = RequestMethod.GET)
    public ZtreeEntity get(String id){
        return this.ztreeServiceImpl.findById(id);
    }

    /**
     * 移动树
     * @return
     */
    @RequestMapping(value = "tree/{sourceId}/{targetId}",method = RequestMethod.PUT)
    public String moveTree(@PathVariable("sourceId")String sourceId,@PathVariable("targetId")String targetId){
        this.ztreeServiceImpl.moveTree(sourceId,targetId);
        return this.SUCCESS;
    }

    /**
     *
     * @param type
     * @param pid
     * @return
     */
    @RequestMapping(value = "trees")
    public  List<ZtreeEntity> findTree(String type,@RequestParam(defaultValue = "0") String pid){
        return this.ztreeServiceImpl.findByTypeAndPid(type,pid);
    }

    /**
     * 查找树根节点列表
     * @return
     */
    @RequestMapping(value = "treesroot",method = RequestMethod.GET)
    public List<ZtreeEntity> findRootOrgTree(String type){
        return this.ztreeServiceImpl.findRootTree(type);
    }

    /**
     * 根据父id查询树列表
     * @param pid
     * @return
     */
    @RequestMapping(value = "treeschildren",method = RequestMethod.GET)
    public List<ZtreeEntity> findChildren(String pid){
        return this.ztreeServiceImpl.findTeeByPid(pid);
    }


    /**
     * 根据id 删除节点及其子节点
     * @param id
     * @return
     */
    @RequestMapping(value = "tree",method = RequestMethod.DELETE)
    public String deleteTreeById(String id){
        this.ztreeServiceImpl.deleteTreeById(id);
        return this.SUCCESS;
    }

    /**
     * 将该节点设置为父节点
     * @param id
     * @return
     */
    @RequestMapping(value = "parenttree",method = RequestMethod.PUT)
    public String parentTrue(String id){
        this.ztreeServiceImpl.setIsPatrentTrue(id);
        return this.SUCCESS;
    }

    /**
     * 将当前节点设置为叶子节点
     * @param id
     * @return
     */
    @RequestMapping(value = "leaftree",method = RequestMethod.PUT)
    public String leafTree(String id){
        this.ztreeServiceImpl.setIsPatrentFalse(id);
        return this.SUCCESS;
    }
}
