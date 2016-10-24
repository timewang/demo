package com.webhybird.config;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.*;

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



        super.onStartup(servletContext);

        /*SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        FilterRegistration.Dynamic signOutRegistration = servletContext.addFilter("CAS Single Sign Out Filter",singleSignOutFilter);
        signOutRegistration.addMappingForUrlPatterns(null,false,"*//*");*/
//httpServletRequestWrapperFilter

        DelegatingFilterProxy httpServletRequestWrapperFilterFilterProxy = new DelegatingFilterProxy();
        httpServletRequestWrapperFilterFilterProxy.setTargetBeanName("httpServletRequestWrapperFilter");
        FilterRegistration.Dynamic httpServletRequestWrapperFilterrRegistration = servletContext.addFilter("CAS HttpServletRequest Wrapper Filter",httpServletRequestWrapperFilterFilterProxy);
        httpServletRequestWrapperFilterrRegistration.addMappingForUrlPatterns(null,false,"/*");

        DelegatingFilterProxy singleSignOutFilterProxy = new DelegatingFilterProxy();
        singleSignOutFilterProxy.setTargetBeanName("singleSignOutFilter");
        FilterRegistration.Dynamic singleSignOutFilterRegistration = servletContext.addFilter("CAS Single Sign Out Filter",singleSignOutFilterProxy);
        singleSignOutFilterRegistration.addMappingForUrlPatterns(null,false,"/*");


        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetBeanName("authenticationFilter");
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("CAS Authentication Filter",delegatingFilterProxy);
        filterRegistration.addMappingForUrlPatterns(null,false,"/*");

        DelegatingFilterProxy ticketFilter = new DelegatingFilterProxy();
        ticketFilter.setTargetBeanName("ticketValidationFilter");
        FilterRegistration.Dynamic ticketGilterRegistration = servletContext.addFilter("CAS Validation Filter",ticketFilter);
        ticketGilterRegistration.addMappingForUrlPatterns(null,false,"/*");

        servletContext.addListener(new SingleSignOutHttpSessionListener());


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
        //HttpServletRequestWrapperFilter httpServletRequestWrapperFilter = new HttpServletRequestWrapperFilter();

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
