package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.utils.StringUtils;

public class ModifyCustomerValidator extends BaseValidateAdapter {
	@Override
	public boolean supports(Class<?> clazz) {
	 	return EnrollCustomerFormBean.class.isAssignableFrom(clazz);
	}



	@Override
	public void validate(Object obj, Errors errors) {
		final EnrollCustomerFormBean formBean = (EnrollCustomerFormBean) obj;
		//validateRequiredField(errors, "groupId", formBean.getGroupId(), "label.GroupId");
		validateRequiredField(errors, "groupName", formBean.getGroupId(), "label.groupname");
	 	//validateRequiredField(errors, "userName", formBean.getUserName(), "label.userName");
	 	
	 	validateRequiredField(errors, "firstName", formBean.getFirstName(), "label.First.Name");
	 	validateRequiredField(errors, "lastName", formBean.getLastName(), "label.Last.Name");
	 	validateRequiredField(errors, "phoneNumber", formBean.getPhoneNumber(), "label.phoneNumber");
	  	validateRequiredField(errors, "email", formBean.getEmail(), "label.email.address");
	 	//validateRequiredField(errors, "confirmEmail", formBean.getConfirmPassword(), "label.confirmEmail");


	 	if (StringUtils.equalsIgnoreCase("UNKNOWN", formBean.getGroupId())) {
	 		errors.reject("error.wrong.groupId");		 		 
		}
		
	 	 
	 	//valid email and confirmEmail
	 	if (StringUtils.isSet(formBean.getEmail())) {
	 		if (!isValidEmailFormat(formBean.getEmail())) {
					errors.reject("error.invalid.email");
			}	 		
	 	}
  

	}



}
