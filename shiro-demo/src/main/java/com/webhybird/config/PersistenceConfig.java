package com.webhybird.config;

import com.webhybird.framework.base.MyJdbcTemplate;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 持久层配置
 * Created by wangzhongfu on 2015/5/5.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages= "com.webhybird.module.*.repositories",repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)
/*@EnableJpaAuditing*/
public class PersistenceConfig {

    @Autowired
    private Environment env;

  /*  @Bean
    public AuditorAware<AuditableUser> auditorProvider() {
        return new AuditorAwareImpl();
    }*/

    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();




        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(this.hibernateJpaVendorAdapter());
        factory.setPackagesToScan(this.env.getProperty("entity.package"));
        factory.setJpaDialect(this.hibernateJpaDialect());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.dialect",env.getProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.connection.driver_class", env.getProperty("jdbc.driverClassName"));
        jpaProperties.put("hibernate.show_sql",this.env.getProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql",this.env.getProperty("hibernate.format_sql"));
        jpaProperties.put("hibernate.max_fetch_depth",this.env.getProperty("hibernate.max_fetch_depth"));
        jpaProperties.put("hibernate.jdbc.fetch_size",this.env.getProperty("hibernate.jdbc.fetch_size"));
        jpaProperties.put("hibernate.jdbc.batch_size",this.env.getProperty("hibernate.jdbc.batch_size"));
        jpaProperties.put("hibernate.cache.use_query_cache",this.env.getProperty("hibernate.cache.use_query_cache"));
        jpaProperties.put("hibernate.cache.region.factory_class",this.env.getProperty("hibernate.cache.region.factory_class"));
        //jpaProperties
        factory.setJpaProperties(jpaProperties);

        factory.afterPropertiesSet();
        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return factory;
    }

    /**
     * 用于设置JPA实现厂商的特定属性
     * @return
     */
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        // vendorAdapter.setGenerateDdl(Boolean.TRUE);
        //vendorAdapter.setShowSql(Boolean.TRUE);
        return vendorAdapter;
    }

    /**
     * 用于指定一些高级特性
     * @return
     */
    public HibernateJpaDialect hibernateJpaDialect(){
        HibernateJpaDialect hibernateJpaDialect = new HibernateJpaDialect();
        return hibernateJpaDialect;
    }

    /**
     * Spring jdbc template
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(this.dataSource());
        return jdbcTemplate;
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

}
