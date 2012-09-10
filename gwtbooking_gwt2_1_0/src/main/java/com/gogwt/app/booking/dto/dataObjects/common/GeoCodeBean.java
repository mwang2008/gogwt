package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.framework.arch.dto.BaseBean;
import com.gogwt.framework.arch.utils.StringUtils;

public class GeoCodeBean extends BaseBean {
 
	//private double lat = -9999;
	//private double lng = -9999;
 
	private String lat = "-9999";
	private String lng = "-9999"; 

	public GeoCodeBean() {
		super();
	}
	

	public GeoCodeBean(String lat, String lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public boolean validGeocode() {
		if (!StringUtils.isSet(lat) || !StringUtils.isSet(lng)) {
			return false;
		}
		
		if (("-9999".equalsIgnoreCase(lat)) || ("-9999".equalsIgnoreCase(lng))) {
			return false;
		}
		return true;
	}
}
