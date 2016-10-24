package com.webhybird.config;

import com.webhybird.daobase.MyJdbcTemplate;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
@Configuration
@ComponentScan(basePackages={"com.webhybird.module.sso.sqlvalidate"},
        excludeFilters=@ComponentScan.Filter(type= FilterType.ANNOTATION, value = {org.springframework.stereotype.Controller.class}))
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
public class SSOSqlvalidateConfig {

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

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }

    /**
     * 增加SQL返回任意VO的支持
     * @return
     */
    @Bean
    public MyJdbcTemplate myJdbcTemplate(){
        MyJdbcTemplate myJdbcTemplate = new MyJdbcTemplate();
        myJdbcTemplate.setDataSource(this.dataSource());
        return myJdbcTemplate;
    }

    /**
     * 事务管理配置，基于数据源
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(this.dataSource());
        return dataSourceTransactionManager;
    }
}
