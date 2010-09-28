package com.gogwt.app.booking.gwt.reservation.client.widgets.home;

import java.util.ArrayList;

import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractValidate;

public class SearchValidate extends AbstractValidate {
	
	
    /**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return
     */
	public ArrayList<String> validate(HomeFormEntry formEntry) {
		
		this.resetError();
		
		if (!StringUtils.isSet(formEntry.getDestination().getText())) {
			this.addError("Please type in Destination field");
		}
		
		return this.getErrorList();
	}
}
