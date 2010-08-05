package org.spring.tutorial.config.urlmapping;

import java.io.Serializable;

public class URLMappingElement implements Serializable {
	private String languageId;   //en
	private String countryId;    //us
	private String contextPath;
	private String servletPath;
	
	
	public URLMappingElement() {
		super();
		languageId = "en";
		countryId = "us";
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getServletPath() {
		return servletPath;
	}
	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}  
	
	
	
	
}
