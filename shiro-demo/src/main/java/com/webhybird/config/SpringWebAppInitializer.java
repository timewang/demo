package com.webhybird.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.*;
import java.util.EnumSet;

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
        return new String[] { "/s/*" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        /*org.springframework.orm.hibernate4.support.OpenSessionInViewFilter*/

        super.onStartup(servletContext);
        servletContext.addListener(new Log4jConfigListener());//
        servletContext.setInitParameter("log4jConfigLocation", "classpath:/log4j.properties");
        servletContext.setInitParameter("webAppRootKey", "spring_springmvc_jpademo.root");

        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();

        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(
                "shiroFilter", delegatingFilterProxy);
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/s/*");

        /**
         *
         *   //DemoServlet
         *    DemoServlet demoServlet = new DemoServlet();
         *    ServletRegistration.Dynamic dynamic = servletContext.addServlet(
         *    "demoServlet", demoServlet);
         *    dynamic.setLoadOnStartup(2);
         *    dynamic.addMapping("/demo_servlet");
         * */
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
