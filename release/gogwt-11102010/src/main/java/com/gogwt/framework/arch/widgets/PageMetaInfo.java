package com.gogwt.framework.arch.widgets;

import java.util.HashMap;

import com.gogwt.framework.arch.dto.BaseBean;

public class PageMetaInfo extends BaseBean {
	 
	private String title;
	private String description;
	private String keywords;
	private HashMap<String, String> metaMap;
	
	
	public PageMetaInfo() {
		super();
		metaMap = new HashMap<String, String>(); 
	}
	
	 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public HashMap<String, String> getMetaMap() {
		return metaMap;
	}

	private void setMetaMap(HashMap<String, String> metaMap) {
		this.metaMap = metaMap;
	}
	
	public void addMetaMap(String key, String value) {
		metaMap.put(key, value);
	}
}
