package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import java.util.ArrayList;

import com.gogwt.app.booking.gwt.reservation.client.widgets.common.BaseValidate;
import com.gogwt.framework.arch.utils.GWTStringUtils;

public class GuestInfoValidate extends BaseValidate {
	  /**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return
     */
	public ArrayList<String> validate(GuestInfoFormEntry formEntry) {
		
		this.resetError();
		
		if (!GWTStringUtils.isSet(formEntry.firstName.getText())) {
			this.addError("Please type in First name field");
		}
		
		return this.getErrorList();
	}
}
