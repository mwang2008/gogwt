package com.gogwt.app.booking.gwt.mvpreservation.client.widgets;

import com.google.gwt.event.shared.HandlerManager;

public class AppHandlerManager {
	private static HandlerManager eventBus;
	
	public static HandlerManager getEventBus() {
		if (eventBus == null) {
			eventBus = new HandlerManager(null);
		}
		return eventBus;
	}
}
