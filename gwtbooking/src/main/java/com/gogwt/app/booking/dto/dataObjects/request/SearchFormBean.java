package com.gogwt.app.booking.dto.dataObjects.request;


import com.gogwt.app.booking.dto.dataObjects.common.GeoCodeBean;
import com.gogwt.framework.arch.dto.BaseBean;

public class SearchFormBean extends BaseBean {
	private String location;
    private int radius = 10;     //default set to 30 miles
    private GeoCodeBean geoCode;
    
    public SearchFormBean() {
    	geoCode = new GeoCodeBean();
    }
    
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public GeoCodeBean getGeoCode() {
		return geoCode;
	}

	public void setGeoCode(GeoCodeBean geoCode) {
		this.geoCode = geoCode;
	}

	

	
}
