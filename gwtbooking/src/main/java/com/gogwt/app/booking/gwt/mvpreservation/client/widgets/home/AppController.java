package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home;

import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.HotelSearchEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.HotelSearchEventHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;

public class AppController {
	private final HandlerManager eventBus;
 
	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
 		bind();
	}

	private void bind() {
		eventBus.addHandler(HotelSearchEvent.TYPE, new HotelSearchEventHandler() {
 			public void searchHotel(HotelSearchEvent event) {
				doSearch();				
			}

			public void backHome(HotelSearchEvent event) {
				backHomePage();				
			}
		});
		
	 
		
	}
	
	//go to searchresult page
	private void doSearch() {
	    History.newItem("searchresult");
	}
	
	private void backHomePage() {
		History.newItem("home");
	}

}
