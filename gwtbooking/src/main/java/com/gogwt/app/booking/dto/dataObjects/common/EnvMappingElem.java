package com.gogwt.app.booking.dto.dataObjects.common;

/**
 * Env element
 * Basically, it matches the UrlMappingElem in server sidem=, but may have more GWT related env such as Token
 * @author WangM
 * 
 */
public class EnvMappingElem {
	 
	private String languageId; // en
	private String countryId; // US
	private String controllerName;

	private String uri; // /booking/en-US/home
	private String contextPath; // //booking
	private String prefix; // booking/en-US
    private String token;
    private boolean isSecure;
    private String domainName;
    private String queryParamter; 
    
	public String getLagnRegion() {
    	return getLanguageId() + "-" + getCountryId().toUpperCase();
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public boolean isSecure() {
		return isSecure;
	}
	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getQueryParamter() {
		return queryParamter;
	}
	public void setQueryParamter(String queryParamter) {
		this.queryParamter = queryParamter;
	}	
	
	
	
}
