package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.home.event;

import com.google.gwt.event.shared.GwtEvent;

public class BackHomeEvent extends GwtEvent<BackHomeEventHandler> {
	
	public static Type<BackHomeEventHandler> TYPE = new Type<BackHomeEventHandler>();
	 
	@Override
	protected void dispatch(BackHomeEventHandler handler) {
		 handler.backToHome(this);
    }
 
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BackHomeEventHandler> getAssociatedType() {
		return TYPE;
	}

}
