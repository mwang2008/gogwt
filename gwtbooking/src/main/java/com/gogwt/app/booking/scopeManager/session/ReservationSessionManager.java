package com.gogwt.app.booking.scopeManager.session;

import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;

public class ReservationSessionManager {
	private SearchFormBean searchFormBean;
	private HotelSearchResponseBean hotelSearchResponse;

	public SearchFormBean getSearchFormBean() {
		return searchFormBean;
	}

	public void setSearchFormBean(SearchFormBean searchFormBean) {
		this.searchFormBean = searchFormBean;
	}

	public HotelSearchResponseBean getHotelSearchResponse() {
		return hotelSearchResponse;
	}

	public void setHotelSearchResponse(HotelSearchResponseBean hotelSearchResponse) {
		this.hotelSearchResponse = hotelSearchResponse;
	}

}
