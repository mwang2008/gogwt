package com.gogwt.app.booking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;

public class GuestInfoFormValidator extends BaseValidateAdapter {


	public boolean supports(Class clazz) {
		return GuestInfoFormBean.class.isAssignableFrom(clazz);
	}


	public void validate(Object obj, Errors errors) {
		final GuestInfoFormBean guestInfoFormBean = (GuestInfoFormBean) obj;

	 	validateRequiredField(errors, "firstName", guestInfoFormBean.getFirstName(), "label.First.Name");
	 	validateRequiredField(errors, "lastName", guestInfoFormBean.getFirstName(), "label.Last.Name");
	 	validateRequiredField(errors, "email", guestInfoFormBean.getFirstName(), "label.email");
 	    
		
	}

	 
}
