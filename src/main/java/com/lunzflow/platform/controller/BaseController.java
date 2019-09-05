package com.lunzflow.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lunzflow.platform.util.Parametermap;

public class BaseController {
	public Parametermap getParametermap() {
		Parametermap parametermap=new Parametermap(getRequest());
		return parametermap;
		
	}
	
	public HttpSession getSession() {
		HttpSession session = this.getRequest().getSession();
		return session;
	}
	
	/**
	 * 获取HttpServletRequest
	 * @return
	 */
	public HttpServletRequest getRequest() {
		ServletRequestAttributes servletRequestAttributes= (org.springframework.web.context.request.ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		return request;
	}
	
}
