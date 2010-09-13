package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.presenter;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.common.presenter.Presenter;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.reservation.view.ReservationConfirmationView;
import com.google.gwt.user.client.ui.HasWidgets;

public class ReservationConfirmationPresenter implements Presenter, ReservationConfirmationView.Presenter {
	private final ReservationConfirmationView view; 
	
	public ReservationConfirmationPresenter(ReservationConfirmationView view) {
		
		//this.eventBus = 
		this.view = view;
		this.view.setPresenter(this);		
		 
		
	}

	public void go(HasWidgets container) {
	    container.clear();
	    container.add(view.asWidget());
	    
	    view.displayConfirmation();
 	}
}
