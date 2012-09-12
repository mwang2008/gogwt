package com.gogwt.app.booking.dto.dataObjects.response;

import java.util.ArrayList;

import com.gogwt.framework.arch.dto.BaseBean;



public class GeocodeResponseBean extends BaseBean {
	private int code;
	private String inputAddress;
	
	private ArrayList<GeocodeResultBean> geocodeDataList;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInputAddress() {
		return inputAddress;
	}

	public void setInputAddress(String inputAddress) {
		this.inputAddress = inputAddress;
	}

	public ArrayList<GeocodeResultBean> getGeocodeDataList() {
		return geocodeDataList;
	}

	public void setGeocodeDataList(ArrayList<GeocodeResultBean> geocodeDataList) {
		this.geocodeDataList = geocodeDataList;
	}
	
	public boolean hasValidGeocode() {
		return (geocodeDataList != null && !geocodeDataList.isEmpty());
	}

}
