/**
 *
 */
package com.webhybird.module.label.validation;

import static java.lang.System.out;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas10TicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;

/**
 * ***********************************************************
 *
 * @类名 ：CustomerCasValidationFilter.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/10/17
 * ***********************************************************
 */
public class CustomerCasValidationFilter2 extends Cas10TicketValidationFilter {


    @Override
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response,
            final Assertion assertion) {
        //assertion.getAttributes().get("username");
        Map<String, Object> map = assertion.getAttributes();
        out.println(map.size());
        for (Map.Entry entry : map.entrySet()) {
            out.println("*****************************************************************,key: "+entry.getKey() + "  value: " + entry.getValue());
        }
        out.println("*****************************************************************,CAS LOGIN USERNAME:"+assertion.getAttributes().get("username"));
        out.println("*****************************************************************,CAS LOGIN USERNAME:"+request.getRemoteUser());
        out.println("*****************************************************************,CAS LOGIN USERNAME:"+request.getSession().getAttribute("edu.yale.its.tp.cas.client.filter.user"));

        /*AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String username = principal.getName();
        System.out.println("*****************************************************************,CAS LOGIN USERNAME:"+username);*/
    }

}
