package com.gogwt.app.booking.dto.dataObjects.request;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;

public class SearchFormBean extends BaseBean {
	private String location;
    private int radius = 20;     //default set to 30 miles
    
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

	
}
