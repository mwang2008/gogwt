package com.gogwt.app.booking.dto.dataObjects.common;

import com.gogwt.app.booking.dto.dataObjects.BaseBean;
import com.gogwt.app.booking.dto.dataObjects.request.SearchFormBean;
import com.gogwt.app.booking.dto.dataObjects.response.HotelSearchResponseBean;
import com.gogwt.app.booking.dto.dataObjects.response.ReserveResponseBean;

public class ReservationContainerBean extends BaseBean {
	/* request in hotel search form page */
	private SearchFormBean searchFormBean;

	/* response after submit "Find A Hotel" button */
	private HotelSearchResponseBean hotelSearchResponseBean;

	/* the selected item in hotel search result page*/
	private int selectHotelItem;
	
	/* the result of reservation */
	private ReserveResponseBean ReserveResponseBean;
	
	public SearchFormBean getSearchFormBean() {
		return searchFormBean;
	}

	public void setSearchFormBean(SearchFormBean searchFormBean) {
		this.searchFormBean = searchFormBean;
	}

	public HotelSearchResponseBean getHotelSearchResponseBean() {
		return hotelSearchResponseBean;
	}

	public void setHotelSearchResponseBean(
			HotelSearchResponseBean hotelSearchResponseBean) {
		this.hotelSearchResponseBean = hotelSearchResponseBean;
	}

	public int getSelectHotelItem() {
		return selectHotelItem;
	}

	public void setSelectHotelItem(int selectHotelItem) {
		this.selectHotelItem = selectHotelItem;
	}

	public ReserveResponseBean getReserveResponseBean() {
		return ReserveResponseBean;
	}

	public void setReserveResponseBean(ReserveResponseBean reserveResponseBean) {
		ReserveResponseBean = reserveResponseBean;
	}
	
}
