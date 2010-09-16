package com.gogwt.app.booking.dto.dataObjects.response;

import java.util.List;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GeoSearchBean;

public class HotelSearchResponseBean extends BaseBean {
 	private GeoSearchBean centerGeocode;
	private List<HotelBean> hotelList;
 
 

	public GeoSearchBean getCenterGeocode() {
		return centerGeocode;
	}

	public void setCenterGeocode(GeoSearchBean centerGeocode) {
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
