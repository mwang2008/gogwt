package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.framework.arch.dto.BaseBean;

public class GeoCodeBean extends BaseBean {
 
	private double lat;
	private double lng;
 
	public GeoCodeBean() {
		super();
	}
	
	public GeoCodeBean(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
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
