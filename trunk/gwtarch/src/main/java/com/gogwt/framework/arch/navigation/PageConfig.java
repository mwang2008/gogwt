package com.gogwt.framework.arch.navigation;

import java.util.Map;

import com.gogwt.framework.arch.widgets.AbstractPage;

public class PageConfig {
	private String name;	 
	private AbstractPage instance;
	private Map<String, Map<String, String>> properties;
	private Map<String, String> forward;
	
	public PageConfig() {
	}

	
	public PageConfig(String name, AbstractPage instance) {
		super();
		this.instance = instance;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public AbstractPage getInstance() {
		return instance;
	}


	public void setInstance(AbstractPage instance) {
		this.instance = instance;
	}


	public Map<String, Map<String, String>> getProperties() {
		return properties;
	}


	public void setProperties(Map<String, Map<String, String>> properties) {
		this.properties = properties;
	}


	public Map<String, String> getForward() {
		return forward;
	}


	public void setForward(Map<String, String> forward) {
		this.forward = forward;
	}
 
	 
}
