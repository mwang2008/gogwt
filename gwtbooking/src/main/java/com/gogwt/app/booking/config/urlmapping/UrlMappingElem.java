package com.gogwt.app.booking.config.urlmapping;

import java.io.Serializable;

import com.gogwt.app.booking.config.runtime.AppRuntimeConfig;

public class UrlMappingElem implements Serializable {
	private AppRuntimeConfig runtimeConfig;
	
	private String languageId; // en
	private String countryId; // US
	private String controllerName;

	private String uri;           // /booking/en-US/home
	private String contextPath;    // //booking
    private String prefix;        // booking/en-US
    
    private String domainName;
	private boolean isSecure;
	private String hotelId;
	private String secureHostPort;
	
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

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public boolean isSecure() {
		return isSecure;
	}

	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getSecureHostPort() {
		return secureHostPort;
	}

	public void setSecureHostPort(String secureHostPort) {
		this.secureHostPort = secureHostPort;
	}

	public AppRuntimeConfig getRuntimeConfig() {
		return runtimeConfig;
	}

	public void setRuntimeConfig(AppRuntimeConfig runtimeConfig) {
		this.runtimeConfig = runtimeConfig;
	}

	
}
