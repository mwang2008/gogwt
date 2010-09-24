package com.gogwt.app.booking.gwt.reservation.client.widgets.guestinfo;

import java.util.ArrayList;

import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.gogwt.framework.arch.widgets.AbstractValidate;

public class GuestInfoValidate extends AbstractValidate {//BaseValidate {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();	
	
    /**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return
     */
	public ArrayList<String> validate(GuestInfoFormEntry formEntry) {
		
		this.resetError();
		
		if (!GWTStringUtils.isSet(formEntry.getFirstName().getText())) {
			errorList.add(tags.input_field_required(tags.label_First_Name()));
		}

		if (!GWTStringUtils.isSet(formEntry.getLastName().getText())) {
			errorList.add(tags.input_field_required(tags.label_Last_Name()));
		}

		if (!GWTStringUtils.isSet(formEntry.getAddress().getText())) {
			errorList.add(tags.input_field_required(tags.Label_Address()));
		}
		
		if (!GWTStringUtils.isSet(formEntry.getCity().getText())) {
			errorList.add(tags.input_field_required(tags.Label_city()));
		}
		if (!GWTStringUtils.isSet(formEntry.getZipCode().getText())) {
			errorList.add(tags.input_field_required(tags.Label_zip()));
		}
		
		String email = formEntry.getEmail().getText();
		if (!GWTStringUtils.isSet(email)) {
			errorList.add(tags.input_field_required(tags.label_email()));
		} else {
			if (!isValidEmailFormat(email)) {
				errorList.add(tags.error_invalid_email());
			}
		}
		
		return this.getErrorList();
	}
}
