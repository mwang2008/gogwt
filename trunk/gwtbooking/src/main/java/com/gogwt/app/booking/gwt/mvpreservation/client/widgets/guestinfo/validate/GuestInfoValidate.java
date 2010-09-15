package com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.validate;

import java.util.ArrayList;

import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.app.booking.gwt.common.i18n.TagsReservationResources;
import com.gogwt.app.booking.gwt.mvpreservation.client.widgets.guestinfo.view.GuestInfoView;
import com.gogwt.framework.arch.utils.GWTStringUtils;
import com.gogwt.framework.arch.widgets.AbstractValidate;

public class GuestInfoValidate extends AbstractValidate {
	private TagsReservationResources tags = TagsReservationResources.Util.getInstance();
	 
	/**
     * Validate search form
     * Note: use ArrayList is for quick response, per Google document.
     * @param formEntry
     * @return error list
     */
	public ArrayList<String> validate(GuestInfoView<GuestInfoFormBean> view) {
        this.resetError();
         
		if (!GWTStringUtils.isSet(view.getFirstName().getValue())) {
			errorList.add(tags.input_field_required(tags.label_First_Name()));
		}

		if (!GWTStringUtils.isSet(view.getLastName().getValue())) {
			errorList.add(tags.input_field_required(tags.label_Last_Name()));
		}

		if (!GWTStringUtils.isSet(view.getAddress().getValue())) {
			errorList.add(tags.input_field_required(tags.Label_Address()));
		}
		
		if (!GWTStringUtils.isSet(view.getCity().getValue())) {
			errorList.add(tags.input_field_required(tags.Label_city()));
		}
		if (!GWTStringUtils.isSet(view.getZipCode().getValue())) {
			errorList.add(tags.input_field_required(tags.Label_zip()));
		}
		
		if (!GWTStringUtils.isSet(view.getEmail().getValue())) {
			errorList.add(tags.input_field_required(tags.label_email()));
		}
		
		
		return this.getErrorList();
	}
}