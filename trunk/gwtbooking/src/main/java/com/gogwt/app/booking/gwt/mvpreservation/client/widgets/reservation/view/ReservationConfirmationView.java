package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view;

import com.google.gwt.user.client.ui.Widget;


public interface ReservationConfirmationView  {
	public interface Presenter {
		
	}
	
	public void process();
	
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
