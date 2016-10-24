package com.webhybird.config;

import com.webhybird.module.security.controller.CurrentUserInterceptor;
import com.webhybird.module.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
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
@Import(SecurityMvcConfig.class)
//@EnableSpringDataWebSupport
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private Environment env;

    /**
     * jsp 视图配置
     * @return
     */
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver(){
        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewClass(JstlView.class);

        urlBasedViewResolver.setPrefix("/pages/jsp/");
        urlBasedViewResolver.setSuffix(".jsp");
        //urlBasedViewResolver.setOrder(2);
        return urlBasedViewResolver;
    }

  /*  @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setViewClass(JstlView.class);
        internalResourceViewResolver.setPrefix("/pages/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }*/

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

        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        //Spring jpa pageable handle
        PageableHandlerMethodArgumentResolver pageableHandlerArgumentResolver = new PageableHandlerMethodArgumentResolver();
        argumentResolvers.add(pageableHandlerArgumentResolver);

        requestMappingHandlerAdapter.setCustomArgumentResolvers(argumentResolvers);

        return requestMappingHandlerAdapter;
    }

    /**
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        return requestMappingHandlerMapping;
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
        registry.addResourceHandler("/script/**", "/styles/**", "/css/**", "/images/**").addResourceLocations("/script/","/styles/","/css/","/images/");
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



    /**
     * 描述 : <添加拦截器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
       //registry.

    }
}
