package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event;

import com.google.gwt.event.shared.GwtEvent;

public class HotelSearchEvent extends GwtEvent<HotelSearchEventHandler> {
	
	public static Type<HotelSearchEventHandler> TYPE = new Type<HotelSearchEventHandler>();
	 
	@Override
	protected void dispatch(HotelSearchEventHandler handler) {
		 handler.searchHotel(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HotelSearchEventHandler> getAssociatedType() {
		return TYPE;
	}

}
