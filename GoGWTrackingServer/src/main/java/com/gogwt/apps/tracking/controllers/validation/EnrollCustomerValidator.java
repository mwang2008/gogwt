package com.gogwt.apps.tracking.controllers.validation;

import org.springframework.validation.Errors;

import com.gogwt.apps.tracking.formbean.EnrollCustomerFormBean;
import com.gogwt.apps.tracking.utils.StringUtils;

public class EnrollCustomerValidator  extends BaseValidateAdapter {
	@Override
	public boolean supports(Class<?> clazz) {
	 	return EnrollCustomerFormBean.class.isAssignableFrom(clazz);
	}



	@Override
	public void validate(Object obj, Errors errors) {
		final EnrollCustomerFormBean formBean = (EnrollCustomerFormBean) obj;
		validateRequiredField(errors, "groupId", formBean.getGroupId(), "label.GroupId");
		validateRequiredField(errors, "groupName", formBean.getGroupId(), "label.groupname");
	 	validateRequiredField(errors, "userName", formBean.getUserName(), "label.userName");
	 	
	 	validateRequiredField(errors, "firstName", formBean.getFirstName(), "label.First.Name");
	 	validateRequiredField(errors, "lastName", formBean.getLastName(), "label.Last.Name");

	  	validateRequiredField(errors, "email", formBean.getEmail(), "label.email.address");
	 	//validateRequiredField(errors, "confirmEmail", formBean.getConfirmPassword(), "label.confirmEmail");


	 	validateRequiredField(errors, "password", formBean.getPassword(), "label.password");
	 	validateRequiredField(errors, "confirmPassword", formBean.getConfirmPassword(), "label.confirmPassword");

	 	if (StringUtils.equalsIgnoreCase("UNKNOWN", formBean.getGroupId())) {
	 		errors.reject("error.wrong.groupId");
		 		 
		}
		
	 	 
	 	//valid email and confirmEmail
	 	if (StringUtils.isSet(formBean.getEmail())) {
	 		if (!isValidEmailFormat(formBean.getEmail())) {
					errors.reject("error.invalid.email");
			}
	 		else {
	 			errors.reject("error.email.notmatch");
	 		}
	 	}
        
	 	
	 	/* not apply any username validation at this moment
	 	//username
	 	if (StringUtils.isSet(formBean.getUserName())) {
	 		if (!isValidUsername(formBean.getUserName())) {
	 			errors.reject("error.username.invalid.format");
	 		}
	 	}
        */
	 	
	 	if (StringUtils.isSet(formBean.getPassword()) && StringUtils.isSet(formBean.getConfirmPassword())) {
	 		if (StringUtils.equals(formBean.getPassword(), formBean.getConfirmPassword())) {
	 			//if correct format
	 			/* not apply it right now
	 			if (!isValidPasswordFormat(formBean.getPassword())) {
	 				errors.reject("error.password.invalid.format");
	 			}
	 			*/
	 		}
	 		else {
	 			errors.reject("error.password.notmatch");
	 		}
	 	}
	 	

	}



}
