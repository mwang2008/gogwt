package com.gogwt.app.booking.rpc.services;

import javax.servlet.http.HttpServletRequest;

import org.gwtwidgets.server.spring.ServletUtils;

public class BaseService {
	 /**
	   * 
	   * <p>
	   * Return http request
	   * </p>
	   * @return
	   */
	  protected HttpServletRequest getHttpServletRequest() {
	     return ServletUtils.getRequest();		 
	  }
}
