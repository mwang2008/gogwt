package com.gogwt.app.booking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.app.booking.dto.dataObjects.request.GuestInfoFormBean;
import com.gogwt.framework.arch.utils.StringUtils;

public class GuestInfoFormValidator extends BaseValidateAdapter {
	

	public boolean supports(Class clazz) {
		return GuestInfoFormBean.class.isAssignableFrom(clazz);
	}


	public void validate(Object obj, Errors errors) {
		final GuestInfoFormBean guestInfoFormBean = (GuestInfoFormBean) obj;

		validateRequiredField(errors, "title", guestInfoFormBean.getTitle(), "label.Title");
	 	validateRequiredField(errors, "firstName", guestInfoFormBean.getFirstName(), "label.First.Name");
	 	validateRequiredField(errors, "lastName", guestInfoFormBean.getLastName(), "label.Last.Name");
	 	validateRequiredField(errors, "address", guestInfoFormBean.getAddress(), "Label.address");
	 	validateRequiredField(errors, "city", guestInfoFormBean.getCity(), "Label.city");
	 	validateRequiredField(errors, "stateId", guestInfoFormBean.getFirstName(), "Label.state");
	 	
	 	String email = guestInfoFormBean.getEmail();
		if (!StringUtils.isSet(email)) {
			validateRequiredField(errors, "email", email, "label.email");
		} else {
			if (!isValidEmailFormat(email)) {
				errors.reject("error.invalid.email");
			}
		}
 	}

	
}
