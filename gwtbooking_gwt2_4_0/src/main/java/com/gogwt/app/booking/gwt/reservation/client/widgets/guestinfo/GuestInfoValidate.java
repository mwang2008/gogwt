package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import java.util.ArrayList;

import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractValidate;

public class GuestInfoValidate extends AbstractValidate { 
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();	
	
    /**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return
     */
	public ArrayList<String> validate(GuestInfoFormEntry formEntry) {
		
		this.resetError();
		
		if (!StringUtils.isSet(formEntry.getFirstName().getText())) {
			errorList.add(tags.input_field_required(tags.label_First_Name()));
		}

		if (!StringUtils.isSet(formEntry.getLastName().getText())) {
			errorList.add(tags.input_field_required(tags.label_Last_Name()));
		}

		if (!StringUtils.isSet(formEntry.getAddress().getText())) {
			errorList.add(tags.input_field_required(tags.Label_Address()));
		}
		
		if (!StringUtils.isSet(formEntry.getCity().getText())) {
			errorList.add(tags.input_field_required(tags.Label_city()));
		}
		if (!StringUtils.isSet(formEntry.getZipCode().getText())) {
			errorList.add(tags.input_field_required(tags.Label_zip()));
		}
		
		String email = formEntry.getEmail().getText();
		if (!StringUtils.isSet(email)) {
			errorList.add(tags.input_field_required(tags.label_email()));
		} else {
			if (!isValidEmailFormat(email)) {
				errorList.add(tags.error_invalid_email());
			}
		}
		
		return this.getErrorList();
	}
}
