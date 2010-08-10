package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import java.util.ArrayList;

import com.gogwt.app.booking.gwt.reservation.client.widgets.common.BaseValidate;
import com.gogwt.framework.arch.utils.GWTStringUtils;

public class SearchValidate extends BaseValidate {
	
	
    /**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return
     */
	public ArrayList<String> validate(HomeFormEntry formEntry) {
		
		this.resetError();
		
		if (!GWTStringUtils.isSet(formEntry.getDestination().getText())) {
			this.addError("Please type in Destination field");
		}
		
		return this.getErrorList();
	}
}
