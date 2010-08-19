package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;

public class KeywordBean extends BaseBean {
	private String keyword;
	private double lat;
	private double lng;

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

}
