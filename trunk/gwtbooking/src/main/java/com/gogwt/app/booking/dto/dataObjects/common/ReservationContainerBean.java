package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;

/**
 * ReservationContainerBean
 *  
 */

public class ReservationContainerBean extends BaseBean {
	/* status of reservation: SEARCH_FORM,SEARCH_RESULT, ROOM_RATE,GUEST_INFO */
	private ProcessStatusEnum status = ProcessStatusEnum.SEARCH_FORM;
	  
	/* request in hotel search form page */
	private SearchFormBean hotelSearchRequest;

	/* response after submit "Find A Hotel" button */
	private HotelSearchResponseBean hotelSearchResponse;

	/* the selected item in hotel search result page*/
	private int selectHotelItem;
	
	/* the result of reservation */
	private ReserveResponseBean reserveResponse;

	public ProcessStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ProcessStatusEnum status) {
		this.status = status;
	}

	public SearchFormBean getHotelSearchRequest() {
		return hotelSearchRequest;
	}

	public void setHotelSearchRequest(SearchFormBean hotelSearchRequest) {
		this.hotelSearchRequest = hotelSearchRequest;
	}

	public HotelSearchResponseBean getHotelSearchResponse() {
		return hotelSearchResponse;
	}

	public void setHotelSearchResponse(HotelSearchResponseBean hotelSearchResponse) {
		this.hotelSearchResponse = hotelSearchResponse;
	}

	public int getSelectHotelItem() {
		return selectHotelItem;
	}

	public void setSelectHotelItem(int selectHotelItem) {
		this.selectHotelItem = selectHotelItem;
	}

	public ReserveResponseBean getReserveResponse() {
		return reserveResponse;
	}

	public void setReserveResponse(ReserveResponseBean reserveResponse) {
		this.reserveResponse = reserveResponse;
	}
	

	
	
	
}
