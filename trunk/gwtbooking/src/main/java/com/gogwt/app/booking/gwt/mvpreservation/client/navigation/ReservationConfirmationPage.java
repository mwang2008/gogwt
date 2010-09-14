package com.gogwt.app.booking.gwt.mvpreservation.client.navigation;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.presenter.ReservationConfirmationPresenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view.ReservationConfirmationView;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view.ReservationConfirmationViewImpl;
import com.gogwt.framework.arch.widgets.AbstractPage;

public class ReservationConfirmationPage extends AbstractPage {

	private ReservationConfirmationView theView;

	public ReservationConfirmationPage() {		// 
 		if (theView == null) {
			theView = new ReservationConfirmationViewImpl();
		}
		new ReservationConfirmationPresenter(theView).go(pagePanel);
 	}

	@Override
	public void process() {
		//this.pagePanel.add(new Label(" confirmation "));
		theView.process();
	}

}
