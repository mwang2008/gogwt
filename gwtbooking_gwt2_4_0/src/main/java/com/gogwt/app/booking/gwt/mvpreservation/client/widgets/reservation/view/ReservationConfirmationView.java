package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view;

import com.google.gwt.user.client.ui.Widget;


public interface ReservationConfirmationView <T> {
	public interface Presenter {
		
	}
	
	public void processDisplay(T t);
	
	/**
	 * Connect to presenter
	 * @param presenter
	 */
	void setPresenter(Presenter  presenter);

	/**
	 * 
	 * @return
	 */
	Widget asWidget();
	
}
