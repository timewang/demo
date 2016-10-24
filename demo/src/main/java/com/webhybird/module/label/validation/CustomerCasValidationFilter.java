/**
 *
 */
package com.webhybird.module.label.validation;

import static java.lang.System.out;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
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
public class CustomerCasValidationFilter extends Cas20ProxyReceivingTicketValidationFilter {


    @Override
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response,
            final Assertion assertion) {

        AttributePrincipal attributePrincipal = assertion.getPrincipal();
        String username = attributePrincipal.getName();


    }

}
