package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.validate;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view.GuestInfoView;
import com.gogwt.framework.arch.utils.StringUtils;
import com.gogwt.framework.arch.widgets.AbstractValidate;

public class GuestInfoValidate extends AbstractValidate {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	//private String EMAIL_REGEX = "^[_A-Za-z0-9-']+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+[A-Za-z0-9-]*(\\.[A-Za-z0-9-]+)*[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)";
	
	/**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return error list
     */
	public ArrayList<String> validate(GuestInfoView<GuestInfoFormBean> view) {
        this.resetError();
         
		if (!StringUtils.isSet(view.getFirstName().getValue())) {
			errorList.add(tags.input_field_required(tags.label_First_Name()));
		}

		if (!StringUtils.isSet(view.getLastName().getValue())) {
			errorList.add(tags.input_field_required(tags.label_Last_Name()));
		}

		if (!StringUtils.isSet(view.getAddress().getValue())) {
			errorList.add(tags.input_field_required(tags.Label_Address()));
		}
		
		if (!StringUtils.isSet(view.getCity().getValue())) {
			errorList.add(tags.input_field_required(tags.Label_city()));
		}
		if (!StringUtils.isSet(view.getZipCode().getValue())) {
			errorList.add(tags.input_field_required(tags.Label_zip()));
		}
		
		String email = view.getEmail().getValue();
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
