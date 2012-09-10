package com.gogwt.app.booking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.app.booking.dto.dataObjects.request.EnrollCustomerFormBean;
import com.gogwt.framework.arch.utils.StringUtils;

public class EnrollCustomerValidator extends BaseValidateAdapter {

	public boolean supports(Class clazz) {
	 	return EnrollCustomerFormBean.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		final EnrollCustomerFormBean formBean = (EnrollCustomerFormBean) obj;
		validateRequiredField(errors, "title", formBean.getTitle(), "label.Title");
	 	validateRequiredField(errors, "firstName", formBean.getFirstName(), "label.First.Name");
	 	validateRequiredField(errors, "lastName", formBean.getLastName(), "label.Last.Name");
	 	//validateRequiredField(errors, "month", formBean.getLastName(), "label.Last.Name");
	 	
	 	validateRequiredField(errors, "gender", formBean.getGender(), "label.Gender");
	 	validateRequiredField(errors, "email", formBean.getEmail(), "label.email.address");
	 	validateRequiredField(errors, "confirmEmail", formBean.getConfirmEmail(), "label.confirmEmail");
	 	
	 	validateRequiredField(errors, "userName", formBean.getUserName(), "label.userName");
	 	
	 	validateRequiredField(errors, "password", formBean.getPassword(), "label.password");
	 	validateRequiredField(errors, "confirmPassword", formBean.getConfirmPassword(), "label.confirmPassword");
	 	
	 	//month, day, year
	 	if (formBean.getMonth() == -1 || formBean.getDay() == -1 || formBean.getYear() == -1) {
	 		errors.reject("error.invalid.birthday");
	 	}
	 	
	 	//valid email and confirmEmail
	 	if (StringUtils.isSet(formBean.getEmail()) && StringUtils.isSet(formBean.getConfirmEmail())) {
	 		if (StringUtils.equals(formBean.getEmail(), formBean.getConfirmEmail())) {
	 			if (!isValidEmailFormat(formBean.getEmail())) {
					errors.reject("error.invalid.email");
				}
	 		}
	 		else {
	 			errors.reject("error.email.notmatch");
	 		}
	 	}
	 	
	 	//username
	 	if (StringUtils.isSet(formBean.getUserName())) {
	 		if (!isValidUsername(formBean.getUserName())) {
	 			errors.reject("error.username.invalid.format");
	 		}
	 	}
	 	
	 	if (StringUtils.isSet(formBean.getPassword()) && StringUtils.isSet(formBean.getConfirmPassword())) {
	 		if (StringUtils.equals(formBean.getPassword(), formBean.getConfirmPassword())) {
	 			//if correct format
	 			if (!isValidPasswordFormat(formBean.getPassword())) {
	 				errors.reject("error.password.invalid.format");
	 			}   
	 		}
	 		else {
	 			errors.reject("error.password.notmatch");
	 		}
	 	}	 	 
	}

}
