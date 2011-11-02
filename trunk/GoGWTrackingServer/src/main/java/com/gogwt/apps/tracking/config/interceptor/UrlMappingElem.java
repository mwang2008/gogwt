package com.gogwt.apps.tracking.config.interceptor;

import java.io.Serializable;

import com.gogwt.apps.tracking.data.CustomerProfile;

public class UrlMappingElem implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private String languageId; // en
	private String countryId; // US
	private String controllerName;

	private String uri; // /tracking/en-US/home
	private String contextPath; // //tracking
	private String prefix; // tracking/en-US

	private CustomerProfile customerProfile;
	
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

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}

	
}
