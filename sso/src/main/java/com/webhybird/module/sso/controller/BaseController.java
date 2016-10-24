package com.webhybird.module.sso.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;


public abstract class BaseController {

	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected HttpSession session;
	/**
	 * 获取HttpServletRequest对象
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {

		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取HttpServletResponse对象
	 * @author wangwg
	 * @return
	 */
	@ModelAttribute          
	public void getRequestAndResponse(HttpServletRequest request, HttpServletResponse response, Model model){
		this.response = response;   
		this.request = request;
		this.session = request.getSession();

		String base = request.getContextPath();
		String fullPath = request.getScheme() + "://" + request.getServerName()
				+ base;
		model.addAttribute("base", base);
		model.addAttribute("fullPath", fullPath);
	}
	
	/**
	 * 工程当前真实的物理地址，weblogic war发布模式时不能获取路径
	 */
	public String getRealPath() {
		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession().getServletContext().getRealPath("/");
		return path;
	}

	/**
	 * 获得ContextPath
	 */
	public String getContextPath() {
		String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession().getServletContext().getContextPath();
		return path;
	}
	
}
