package com.gogwt.app.booking.gwt.mvpreservation.client.widgets;

import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.GUEST_INFO;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.RESERVATION_CONFIRMATION;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_HOME;
import static com.gogwt.app.booking.dto.dataObjects.GWTPageConstant.VIEW_SEARCH_RESULT;

import com.gogwt.app.booking.gwt.common.utils.GWTExtClientUtils;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.event.GuestInfoEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.event.GuestInfoEventHandler;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.HotelSearchEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event.HotelSearchEventHandler;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.event.HotelSelectEvent;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.event.HotelSelectEventHandler;
import com.google.gwt.event.shared.HandlerManager;

public class AppController {
	private final HandlerManager eventBus;
 
	public AppController() {		
		eventBus = AppHandlerManager.getEventBus();
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
		
	    
		eventBus.addHandler(HotelSelectEvent.TYPE, new HotelSelectEventHandler() {
			public void selectHotel(HotelSelectEvent event) {
               goGuestInfo();
			}
			
		});
		
		eventBus.addHandler(GuestInfoEvent.TYPE, new GuestInfoEventHandler() {
			public void toReservationConfirmation(GuestInfoEvent event) {
				GWTExtClientUtils.forward(RESERVATION_CONFIRMATION);				
			}
			
		});
	}
	
	//go to searchresult page
	private void doSearch() {
		GWTExtClientUtils.forward(VIEW_SEARCH_RESULT);
	    //History.newItem("searchresult");
	}
	
	private void backHomePage() {
		GWTExtClientUtils.forward(VIEW_HOME);
		//History.newItem("home");
	}
	
	private void goGuestInfo() {		
		GWTExtClientUtils.forward(GUEST_INFO);
		//History.newItem("guestinfo");
	}

}
