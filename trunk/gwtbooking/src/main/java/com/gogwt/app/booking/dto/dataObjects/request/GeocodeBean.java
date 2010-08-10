package com.gogwt.app.booking.dto.dataObjects.request;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;

public class GeocodeBean extends BaseBean {
	private double lat;
	private double lng;
	private int radius;

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

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

}
