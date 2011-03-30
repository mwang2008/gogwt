package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.framework.arch.dto.BaseBean;

public class GeoCodeBean extends BaseBean {
 
	private double lat = -9999;
	private double lng = -9999;
 
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

	public boolean validGeocode() {
		if ((lat == -9999 ) || (lng == -9999)) {
			return false;
		}
		return true;
	}
}
