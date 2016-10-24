package com.webhybird.module.security.service;

import com.webhybird.module.security.entity.User;

import java.util.List;

/**
 * Created by wangzhongfu on 2015/8/7.
 */
public interface UserService {

    User getCurrentUser();

    void createUser(String username, String email, String password);

    List<User> getAllUsers();

    User getUser(String userId);

    void deleteUser(String userId);

    void updateUser(User user);

}
