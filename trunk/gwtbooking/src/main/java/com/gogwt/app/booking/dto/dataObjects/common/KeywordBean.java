package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.framework.arch.dto.BaseBean;



public class KeywordBean extends BaseBean {
	private String keyword;
	private double lat;
	private double lng;
    private String searchkey;
    private String type;
    
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
