package com.webhybird.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
@Configuration
@ComponentScan(basePackages={"com.webhybird"},
        excludeFilters=@ComponentScan.Filter(type= FilterType.ANNOTATION, value = {org.springframework.stereotype.Controller.class}))
@PropertySource(value = { "classpath:application.properties" })
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
@Import(ShiroConfig.class)
public class ApplicationConfig {

    /**
     * 环境变量存储类
     */
    @Autowired
    private Environment env;

    /**
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }



}
