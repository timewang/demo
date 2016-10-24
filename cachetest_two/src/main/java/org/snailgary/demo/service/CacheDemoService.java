/**
 *
 */
package org.snailgary.demo.service;

import org.snailgary.demo.model.DemoCacheObj;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * ***********************************************************
 *
 * @类名 ：CacheDemoService.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/9/28
 * ***********************************************************
 */
@Component
public class CacheDemoService {


    @Cacheable(value = "cachedemo",key = "#id")
    public DemoCacheObj createDemoCacheObj(String id){
        DemoCacheObj demoCacheObj = new DemoCacheObj();
        demoCacheObj.setId(id);
        demoCacheObj.setValue("缓存的对象");
        return demoCacheObj;
    }

    @CacheEvict(value = "cachedemo",key = "#id")
    public void deleteCache(String id){
        DemoCacheObj demoCacheObj = new DemoCacheObj();
        demoCacheObj.setId(id);
        demoCacheObj.setValue("缓存的对象");
    }

    @CachePut(value = "cachedemo",key = "#id")
    public DemoCacheObj updateCache(String id){
        DemoCacheObj demoCacheObj = new DemoCacheObj();
        demoCacheObj.setId(id);
        demoCacheObj.setValue("缓存的对象");
        return demoCacheObj;
    }

}
