package com.webhybird.module.label.service.impl;

import com.webhybird.module.user.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by wangzhongfu on 2015/8/26.
 */
@Component
public class DemoUserAuditorAware implements AuditorAware<User> {
   /* @Autowired
    private UserRepository userRepository;*/
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     */
    @Override
    public User getCurrentAuditor()
    {
        User  u = new User();
        u.setUsername("fuck");
        return u;
    }
}
