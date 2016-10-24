package com.webhybird.module.security.service.impl;

import com.webhybird.module.security.entity.User;
import com.webhybird.module.security.repositories.UserRepository;
import com.webhybird.module.security.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by wangzhongfu on 2015/8/7.
 */
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        final String currentUserId = (String) SecurityUtils.getSubject().getPrincipal();
        if(!StringUtils.isEmpty(currentUserId ) ) {
            return getUser(currentUserId);
        } else {
            return null;
        }
    }
    @Override
    public void createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword( new Sha256Hash(password).toHex() );
        this.userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    @Override
    public User getUser(String userId) {
        return this.userRepository.getOne(userId);
    }
    @Override
    public void deleteUser(String userId) {
        this.userRepository.delete(userId);
    }
    @Override
    public void updateUser(User user) {
        this.userRepository.update(user);
    }
}
