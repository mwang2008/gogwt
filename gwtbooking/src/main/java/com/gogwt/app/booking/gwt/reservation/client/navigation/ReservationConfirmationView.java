package com.gogwt.app.booking.gwt.reservation.client.navigation;

import com.gogwt.app.booking.gwt.reservation.client.widgets.reservation.ReservationConfirmationLayoutWidget;
import com.gogwt.framework.arch.widgets.AbstractView;

public class ReservationConfirmationView extends AbstractView {

	 
	public void process() {
		ReservationConfirmationLayoutWidget layout = new ReservationConfirmationLayoutWidget();
		
		this.viewPanel.add(layout);
		
		layout.prepareEntryLayout();
	}

	 
 

}
