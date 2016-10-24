package com.webhybird.config;

import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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
        super.onStartup(servletContext);
        servletContext.addListener(new Log4jConfigListener());//
        servletContext.setInitParameter("log4jConfigLocation", "classpath:/log4j.properties");
        servletContext.setInitParameter("webAppRootKey", "spring_springmvc_jpademo.root2");

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

    }

    @Override
    protected Filter[] getServletFilters() {
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter};
    }
}
