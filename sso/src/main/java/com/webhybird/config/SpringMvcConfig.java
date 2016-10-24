package com.webhybird.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
@Configuration
@ComponentScan(basePackages = {"com.webhybird.module.*.controller"})
@EnableWebMvc
//@EnableSpringDataWebSupport
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private Environment env;

    /**
     * freemarker 视图配置
     * @return
     */
    @Bean
    public ViewResolver freemarkerViewResolver(){
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setSuffix(this.env.getProperty("freemarker.suffix"));
        freeMarkerViewResolver.setContentType(this.env.getProperty("freemarker.contentType"));
        return freeMarkerViewResolver;
    }

    /**
     * freemarker 配置
     * @return
     */
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath(this.env.getProperty("freemarker.templateLoaderPath"));
        Properties properties = new Properties();
        setFreemarkerProperties(properties);
        freeMarkerConfigurer.setFreemarkerSettings(properties);
        return freeMarkerConfigurer;
    }

    /**
     *freemarker 配置项
     * @param properties
     */
    private void setFreemarkerProperties(Properties properties){
        properties.put("template_update_delay", this.env.getProperty("freemarker.template_update_delay"));
        properties.put("default_encoding", this.env.getProperty("freemarker.default_encoding"));
        properties.put("number_format",this.env.getProperty("freemarker.number_format"));
        properties.put("datetime_format", this.env.getProperty("freemarker.datetime_format"));
        properties.put("date_format", this.env.getProperty("freemarker.date_format"));
        properties.put("template_exception_handler", this.env.getProperty("freemarker.template_exception_handler"));
        properties.put("auto_import", this.env.getProperty("freemarker.auto_import"));
    }

    /**
     * RequestMappingHandlerAdapter 配置
     * @return
     */
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setCacheSeconds(0);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        //下载
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        messageConverters.add(byteArrayHttpMessageConverter);
        //json
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        messageConverters.add(mappingJackson2HttpMessageConverter);

        requestMappingHandlerAdapter.setMessageConverters(messageConverters);
        return requestMappingHandlerAdapter;
    }

    /**
     * 描述 : <HandlerMapping需要显示声明，否则不能注册资源访问处理器>. <br>
     *<p>
     </p>
     * @return
     */
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        return super.resourceHandlerMapping();
    }


    /**
     * 静态资源访问配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/script/**").addResourceLocations("/script/");
    }

    /**
     * 描述 : <文件上传处理器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @return
     */
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver(){
        return new CommonsMultipartResolver();
    }

}
