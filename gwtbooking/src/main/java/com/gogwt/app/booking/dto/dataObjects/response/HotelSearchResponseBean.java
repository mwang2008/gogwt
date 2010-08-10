package com.gogwt.app.booking.dto.dataObjects.response;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GeocodeBean;

public class HotelSearchResponseBean extends BaseBean {
 	private GeocodeBean centerGeocode;
	private List<HotelBean> hotelList;
 
 

	public GeocodeBean getCenterGeocode() {
		return centerGeocode;
	}

	public void setCenterGeocode(GeocodeBean centerGeocode) {
		this.centerGeocode = centerGeocode;
	}

	public List<HotelBean> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<HotelBean> hotelList) {
		this.hotelList = hotelList;
	}

	public boolean hasSearchResult() {
		return (hotelList != null && !hotelList.isEmpty());
	}
}
