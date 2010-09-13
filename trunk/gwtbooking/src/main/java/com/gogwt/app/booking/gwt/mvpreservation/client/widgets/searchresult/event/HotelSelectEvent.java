package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.searchresult.event;

import com.google.gwt.event.shared.GwtEvent;

public class HotelSelectEvent extends GwtEvent<HotelSelectEventHandler> {
	
	public static Type<HotelSelectEventHandler> TYPE = new Type<HotelSelectEventHandler>();
	 
	@Override
	protected void dispatch(HotelSelectEventHandler handler) {
		 handler.selectHotel(this);
    }

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HotelSelectEventHandler> getAssociatedType() {
		return TYPE;
	}

}
