package com.gogwt.app.booking.gwt.hoteldetail.client.widgets.info.view;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.google.gwt.user.client.ui.Widget;

public interface HotelDetailInfoView <T>{
	
	public interface Presenter<T> {
		void retrieveHotelDetail();
	}
	
	/**
	 * Connect to presenter
	 * @param presenter
	 */
	void setPresenter(Presenter<T> presenter);

	/**
	 * 
	 * @return
	 */
	Widget asWidget();
	
	
	void retrieveHotelDetail();
	
	/**
	 * Fill error message
	 * @param msg
	 */
	void dispErrorMsg(ArrayList<String> msg);	
	
	void displayHotelDetail(final HotelBean hotelBean);
}
