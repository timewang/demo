package com.webhybird.config;

import com.webhybird.module.security.security.SampleRealm;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangzhongfu on 2015/8/7.
 */
@Configuration
public class ShiroConfig {

    /**
     *    Shiro's main business-tier object for web-enabled applications
     (use org.apache.shiro.web.mgt.DefaultWebSecurityManager instead when there is no web environment)
     * @param sampleRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(SampleRealm sampleRealm){
        /**
         *  Single realm app (realm configured next, below).  If you have multiple realms, use the 'realms'
         property instead.
         Uncomment this next property if you want heterogenous session access or clusterable/distributable
         sessions.  The default value is 'http' which uses the Servlet container's HttpSession as the underlying
         Session implementation.
         */
        return  new DefaultWebSecurityManager(sampleRealm);
    }

    /**
     * Post processor that automatically invokes init() and destroy() methods
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     *
     * @return
     */
    @Bean
    public PassThruAuthenticationFilter passThruAuthenticationFilter(){
        return new PassThruAuthenticationFilter();
    }

    /**
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        shiroFilterFactoryBean.setLoginUrl("/s/login");
        shiroFilterFactoryBean.setSuccessUrl("/s/home");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        /**
         * The 'filters' property is usually not necessary unless performing an override, which we
         want to do here (make authc point to a PassthruAuthenticationFilter instead of the
         default FormAuthenticationFilter:
         */
        shiroFilterFactoryBean.getFilters().put("authc",passThruAuthenticationFilter());
        String definitions = "/s/signup = anon\n" +
                "                /s/manageUsers = perms[user:manage]\n" +
                "                /s/** = authc ";
        shiroFilterFactoryBean.setFilterChainDefinitions(definitions);
        return shiroFilterFactoryBean;
    }
}
