package com.webhybird.module.security.repositories;

import com.webhybird.framework.base.BaseRepositories;
import com.webhybird.module.security.entity.User;

/**
 * Created by wangzhongfu on 2015/8/7.
 */
public interface UserRepository extends BaseRepositories<User,String> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

}
