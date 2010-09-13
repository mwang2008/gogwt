package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.event;

import com.google.gwt.event.shared.GwtEvent;

public class GuestInfoEvent extends GwtEvent<GuestInfoEventHandler> {
	public static Type<GuestInfoEventHandler> TYPE = new Type<GuestInfoEventHandler>();
	
	@Override
	protected void dispatch(GuestInfoEventHandler handler) {
		handler.toReservationConfirmation(this);
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GuestInfoEventHandler> getAssociatedType() {
		return TYPE;
	}

}
