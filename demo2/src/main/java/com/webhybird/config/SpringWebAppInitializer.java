package com.webhybird.config;

import javax.servlet.*;

import com.webhybird.oauth.Oauth2AuthorFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[] { ApplicationConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { SpringMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        servletContext.addListener(new Log4jConfigListener());//
        servletContext.setInitParameter("log4jConfigLocation", "classpath:/log4j.properties");
        servletContext.setInitParameter("webAppRootKey", "spring_springmvc_jpademo.root");

        Oauth2AuthorFilter oauth2AuthorFilter = new Oauth2AuthorFilter();
        oauth2AuthorFilter.setAccessTokenCheckUrl("http://localhost:8080/oauth2/checkAccessToken");
        FilterRegistration.Dynamic oauth2filterRegistration = servletContext.addFilter(
                "oauth2AuthorFilter", oauth2AuthorFilter);
        oauth2filterRegistration.addMappingForUrlPatterns(null, false, "/vl/openapi/*");

        super.onStartup(servletContext);

        /**
         * demo add servlet
         */
        /*ServletRegistration.Dynamic dynamic = servletContext.addServlet("","");
        dynamic.addMapping("");*/


       /**
         * filter 需要指定url 则用此方式添加filter
         */
      /* OpenEntityManagerInViewFilter openSessionInViewFilter = new OpenEntityManagerInViewFilter();

        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(
                "openSessionInViewFilter", openSessionInViewFilter);
        filterRegistration.addMappingForUrlPatterns(null, false, "*//*");*/

/*EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE)*/
       /* CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic filterRegistration2 = servletContext.addFilter(
                "characterEncodingFilter", characterEncodingFilter);
        filterRegistration2.addMappingForUrlPatterns(null, false, "*//*");*/

        /*MultipartFilter multipartFilter = new MultipartFilter();
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("multipartFilter",multipartFilter);
        filterRegistration.addMappingForUrlPatterns(null,false,"/upload*");*/

        /*delegatingFilterProxy.setContextAttribute("");*/
    }


    /**
     *filter for all url patterns
     * 如果filter 要应用到所有的url上，则重写此方法
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

      /*  OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();

        openSessionInViewFilter.setSessionFactoryBeanName("entityManagerFactory");*/

        OpenEntityManagerInViewFilter openSessionInViewFilter = new OpenEntityManagerInViewFilter();

        return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter,openSessionInViewFilter};
    }

    /**
     * 文件上传配置
     * @param dynamic
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic dynamic){
       /* super.customizeRegistration(dynamic);*/
        //临时文件路径
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/",20971520,41943040,0);

        dynamic.setMultipartConfig(multipartConfigElement);
    }
}
