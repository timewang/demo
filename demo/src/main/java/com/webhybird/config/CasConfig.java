package com.webhybird.config;

import com.webhybird.module.label.validation.CustomerCasValidationFilter;
import com.webhybird.module.label.validation.CustomerCasValidationFilter2;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas10TicketValidationFilter;
import org.jasig.cas.client.validation.Cas10TicketValidator;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by snail on 2016/9/1.
 */
@Configuration
public class CasConfig {

    private final String hostName = "https://s";

    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setCasServerLoginUrl("https://demo.snailgary.org:8443/cas/login");
        authenticationFilter.setServerName("https://demo.snailgary.org:8443");
        //authenticationFilter.setService("");
        return authenticationFilter;
    }

 /*   @Bean
    public Cas10TicketValidationFilter cas10TicketValidationFilter(){
        Cas10TicketValidationFilter cas10TicketValidationFilter = new Cas10TicketValidationFilter();
        cas10TicketValidationFilter.setService("https://demo.snailgary.org:8445/demo");
        cas10TicketValidationFilter.setTicketValidator(this.cas10TicketValidator());
        return cas10TicketValidationFilter;
    }

    @Bean
    public Cas10TicketValidator cas10TicketValidator(){
        Cas10TicketValidator cas10TicketValidator = new Cas10TicketValidator("https://demo.snailgary.org:8443/cas");

        return cas10TicketValidator;
    }*/
    @Bean
    public CustomerCasValidationFilter ticketValidationFilter() {
        CustomerCasValidationFilter  cas20ProxyReceivingTicketValidationFilter = new CustomerCasValidationFilter();
        //cas20ProxyReceivingTicketValidationFilter.setService("https://demo.snailgary.org:8443/demo/");
        cas20ProxyReceivingTicketValidationFilter.setServerName("https://demo.snailgary.org:8443");
        cas20ProxyReceivingTicketValidationFilter.setTicketValidator(this.cas20ServiceTicketValidator());
        return cas20ProxyReceivingTicketValidationFilter;
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator(){
        Cas20ServiceTicketValidator cas20ServiceTicketValidator = new Cas20ServiceTicketValidator("https://demo.snailgary.org:8443/cas");
        return cas20ServiceTicketValidator;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter(){
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        //singleSignOutFilter.setCasServerUrlPrefix("");
        return singleSignOutFilter;
    }
    @Bean
    public HttpServletRequestWrapperFilter httpServletRequestWrapperFilter(){
        return new HttpServletRequestWrapperFilter();
    }

}
