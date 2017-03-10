package com.webhybird.module.label.repositories;

import com.webhybird.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangzhongfu on 2015/8/26.
 */
public interface UserRepository extends JpaRepository<User,String> {
}
